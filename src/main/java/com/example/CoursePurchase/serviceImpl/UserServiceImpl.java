package com.example.CoursePurchase.serviceImpl;

import com.example.CoursePurchase.dao.CourseRepository;
import com.example.CoursePurchase.dao.PaymentRepository;
import com.example.CoursePurchase.dao.UserDetailsRepository;
import com.example.CoursePurchase.dao.UserRepository;
import com.example.CoursePurchase.models.*;
import com.example.CoursePurchase.service.AttachmentService;
import com.example.CoursePurchase.service.EmailService;
import com.example.CoursePurchase.service.PDFGeneratorService;
import com.example.CoursePurchase.service.UserService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.apache.commons.codec.binary.Hex;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PDFGeneratorService pdfGeneratorService;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private EmailService emailService;

    @Override
    public Integer updateUserDetails(UserDetails userDetails) {
        try {
            Optional<UserDetails> optionalUserDetails =  userDetailsRepository.findById(userDetails.getUsrDetailsId());
            if(optionalUserDetails.isPresent()) {
                UserDetails userDetailsByGet = optionalUserDetails.get();
                userDetailsByGet.setFullName(userDetails.getFullName());
                userDetailsByGet.setDateOfBirth(userDetails.getDateOfBirth());
                userDetailsByGet.setGender(userDetails.getGender());
                userDetailsByGet.setMobileNumber(userDetails.getMobileNumber());
                userDetailsByGet.setAddressOne(userDetails.getAddressOne());
                userDetailsByGet.setAddressTwo(userDetails.getAddressTwo());
                userDetailsByGet.setAddressThree(userDetails.getAddressThree());

                userDetailsRepository.save(userDetailsByGet);
                return 1;
            }
            else {
                logger.info("No User Details Found");
                return 0;
            }
        } catch (Exception e) {
            logger.error("Error Found", e);
            return 0;
        }
    }

    @Override
    public List<CourseDTO> fetchUnpiadCourseList(String username, Integer pageNumber, Integer rowCount) {
        try {
            UserAuth userAuth = userRepository.findByUserName(username);
            if(userAuth != null) {
                Pageable pageable = PageRequest.of(pageNumber -1 , rowCount);
                Page<Courses> coursesList = courseRepository.findUnpurchasedCoursesByUserId(userAuth.getUserId(), pageable);
                if(coursesList != null && !coursesList.isEmpty()) {
                    List<CourseDTO> courseDTOS = new ArrayList<>();
                    for(Courses courses: coursesList.getContent()) {
                        CourseDTO courseDTO = new CourseDTO();
                        courseDTO.setCourseId(courses.getCourseId());
                        courseDTO.setTitle(courses.getTitle());
                        courseDTO.setDescription(courses.getDescription());
                        courseDTO.setThumbnailUrl(courses.getThumbnailUrl());
                        courseDTO.setCourseUrl(courses.getCourseUrl());
                        courseDTO.setPrice(courses.getPrice());
                        courseDTO.setGenre(courses.getGenre());
                        courseDTO.setTotalElements(coursesList.getTotalElements());
                        courseDTOS.add(courseDTO);
                    }

                    return courseDTOS;
                }
                else {
                    return null;
                }
            }
            else {
                return null;
            }
        } catch (Exception e) {
            logger.error("Error Found", e);
            return null;
        }
    }

    @Override
    public List<CourseDTO> fetchPurchasedCourseList(String username, Integer pageNumber, Integer rowCount) {
        try {
            UserAuth userAuth = userRepository.findByUserName(username);
            if(userAuth != null) {
                Pageable pageable = PageRequest.of(pageNumber -1 , rowCount);
                Page<Courses> coursesList = courseRepository.findPurchasedCourse(userAuth.getUserId(), pageable);
                if(coursesList != null && !coursesList.isEmpty()) {
                    List<CourseDTO> courseDTOS = new ArrayList<>();
                    for(Courses courses: coursesList.getContent()) {
                        CourseDTO courseDTO = new CourseDTO();
                        courseDTO.setCourseId(courses.getCourseId());
                        courseDTO.setTitle(courses.getTitle());
                        courseDTO.setDescription(courses.getDescription());
                        courseDTO.setThumbnailUrl(courses.getThumbnailUrl());
                        courseDTO.setCourseUrl(courses.getCourseUrl());
                        courseDTO.setPrice(courses.getPrice());
                        courseDTO.setGenre(courses.getGenre());
                        courseDTO.setTotalElements(coursesList.getTotalElements());
                        courseDTOS.add(courseDTO);
                    }
                    return courseDTOS;
                }
                else {
                    return new ArrayList<>();
                }
            }
            else {
                return null;
            }
        } catch (Exception e) {
            logger.error("Error Found", e);
            return null;
        }
    }

    @Override
    public Order createPaymentOrder(Double amount) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("amount", amount * 100);
            jsonObject.put("currency", "INR");
            jsonObject.put("receipt", "txn_" + System.currentTimeMillis());

            RazorpayClient razorpayClient = new RazorpayClient("abc", "abc");
            Order order = razorpayClient.orders.create(jsonObject);
            return order;
        } catch (Exception e) {
            logger.error("Error Found", e);
            return null;
        }
    }

    @Override
    public Integer verifyPayment(String orderId, String paymentId, String signature, String username, Long courseId, Double amount) {
        try {
            String payload = orderId + "|" + paymentId;
            String expectedSignature = hmacSha256(payload, "abc");

            if(expectedSignature.equals(signature)) {
                UserAuth userAuth = userRepository.findByUserName(username);
                Optional<Courses> courses = courseRepository.findById(courseId);

                Map<String, String> paymentDetailsMap = new HashMap<>();
                paymentDetailsMap.put("name", userAuth.getUserDetails().getFullName());
                paymentDetailsMap.put("mobile_number", userAuth.getUserDetails().getMobileNumber());
                if (courses.isPresent()) {
                    paymentDetailsMap.put("courseName", courses.get().getTitle());
                    paymentDetailsMap.put("courseGenre", courses.get().getGenre());
                    paymentDetailsMap.put("amount", String.valueOf(courses.get().getPrice()));
                } else {
                    paymentDetailsMap.put("courseName", "N/A");
                    paymentDetailsMap.put("courseGenre", "N/A");
                    paymentDetailsMap.put("amount", "N/A");
                }

                LocalDateTime dateTime = LocalDateTime.parse(LocalDateTime.now().toString());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                String paymentDate = dateTime.format(formatter);

                paymentDetailsMap.put("paymentDate", paymentDate);
                paymentDetailsMap.put("transactionId", paymentId);
                paymentDetailsMap.put("paymentMode", "ONLINE/CARD");

                byte[] generatedPdf = pdfGeneratorService.generatePDF(paymentDetailsMap);

                String fileName = "Payment_" + paymentId + ".pdf";
                String contentType = "application/pdf";
                String fileSize = String.valueOf(generatedPdf.length);

                Attachment attachment = new Attachment();
                attachment.setOriginalFileName(fileName);
                attachment.setContentSize(fileSize);
                attachment.setContentType(contentType);
                attachment.setFileExtension(".pdf");
                attachment.setCreatedDate(LocalDateTime.now());

                Long attachmentId = attachmentService.uploadAttachment(attachment, generatedPdf);

                Payment payment = new Payment();
                payment.setTransactionId(paymentId);
                payment.setTransactionAmount(amount);
                payment.setStatus("SUCCESS");
                payment.setPaymentMode("ONLINE/CARD");
                payment.setPaymentDate(LocalDateTime.now());
                payment.setVerified(true);
                payment.setReceiptAttachmentId(attachmentId);
                payment.setRazorpayOrderId(orderId);
                payment.setRazorpaySignature(signature);
                payment.setUserAuth(userAuth);
                payment.setCourses(courses.orElse(null));

                paymentRepository.save(payment);
                String to = userAuth.getEmail();
                String subject = "Payment Successful for course " + (courses.isPresent() ? courses.get().getTitle() : "N/A");
                String body = "Dear " + userAuth.getUserDetails().getFullName() + ",\n\n" +
                        "Your payment for the course \"" + (courses.isPresent() ? courses.get().getTitle() : "N/A") + "\" is successful.\n" +
                        "Kindly view the courses in the Purchased Courses section.\n\n" +
                        "Please check the attached Payment Receipt.\n\n" +
                        "Regards,\nTeam CMS";

                emailService.sendEmail(to, subject, body, fileName, generatedPdf);
                return 1;
            }
            else {
                return 0;
            }
        } catch (Exception e) {
            logger.error("Error Found", e);
            return 0;
        }
    }

    @Override
    public List<PaymentDTO> fetchPaymentDetails(String username) {
        try {
            UserAuth userAuth = userRepository.findByUserName(username);
            if(userAuth != null) {
                List<PaymentDTO> paymentDTOList = paymentRepository.fetchCoursePaymentDetails(userAuth.getUserId());
                return paymentDTOList;
            }
            else {
                return null;
            }
        } catch (Exception e) {
            logger.error("Error Found", e);
            return null;
        }
    }

    @Override
    public AttachmentDTO fetchAttachmentDetails(Long attachmentId) {
        try {
            return attachmentService.fetchAttachmentDetails(attachmentId);
        } catch (Exception e) {
            logger.error("Error Found", e);
            return null;
        }
    }

    private String hmacSha256(String data, String secretKey) throws Exception {
        Mac sha256HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
        sha256HMAC.init(secretKeySpec);
        byte[] hash = sha256HMAC.doFinal(data.getBytes());
        return Hex.encodeHexString(hash);
    }
}
