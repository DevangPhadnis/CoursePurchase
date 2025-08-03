package com.example.CoursePurchase.serviceImpl;

import com.example.CoursePurchase.dao.CourseRepository;
import com.example.CoursePurchase.dao.SessionRepository;
import com.example.CoursePurchase.dao.UserRepository;
import com.example.CoursePurchase.models.*;
import com.example.CoursePurchase.service.AdminService;
import com.example.CoursePurchase.service.AttachmentService;
import com.example.CoursePurchase.utils.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.*;
import java.util.zip.GZIPInputStream;

@Service
public class AdminServiceImpl implements AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AttachmentService attachmentService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public Object fetchUserDetails(String username) {
        try {
            UserAuth userAuth = userRepository.findByUserName(username);

            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(userAuth.getUserId());
            userDTO.setUserName(username);
            userDTO.setEmail(userAuth.getEmail());
            userDTO.setUserCreatedDate(userAuth.getCreatedDate());
            userDTO.setRole(userAuth.getRole());
            if(userAuth.getUserDetails() != null) {
                userDTO.setUserDetailsId(userAuth.getUserDetails().getUsrDetailsId());
                userDTO.setGender(userAuth.getUserDetails().getGender());
                userDTO.setFullName(userAuth.getUserDetails().getFullName());
                userDTO.setMobileNumber(userAuth.getUserDetails().getMobileNumber());
                userDTO.setAddressOne(userAuth.getUserDetails().getAddressOne());
                userDTO.setAddressTwo(userAuth.getUserDetails().getAddressTwo());
                userDTO.setAddressThree(userAuth.getUserDetails().getAddressThree());
                userDTO.setDateOfBirth(userAuth.getUserDetails().getDateOfBirth());
                userDTO.setUserDetailsCreatedDate(userAuth.getUserDetails().getCreatedDate());
            }
            return userDTO;
        } catch (Exception e) {
            logger.error("Error Found", e);
            return null;
        }
    }

    @Override
    public List<CourseDTO> fetchCourseDetails() {
        try {
            List<Courses> courses = courseRepository.findAll();
            List<CourseDTO> courseDTOS = new ArrayList<>();
            for(Courses courses1: courses) {
                CourseDTO courseDTO = new CourseDTO();
                courseDTO.setCourseId(courses1.getCourseId());
                courseDTO.setTitle(courses1.getTitle());
                courseDTO.setDescription(courses1.getDescription());
                courseDTO.setCourseUrl(courses1.getCourseUrl());
                courseDTO.setThumbnailUrl(courses1.getThumbnailUrl());
                courseDTO.setCreatedDate(courses1.getCreatedDate());
                courseDTO.setGenre(courses1.getGenre());
                courseDTO.setPrice(courses1.getPrice());

                courseDTOS.add(courseDTO);
            }

            return courseDTOS;
        } catch (Exception e) {
            logger.error("Error Found", e);
            return null;
        }
    }

    @Override
    public Integer addCourse(Courses course) {
        try {

            Attachment attachment = new Attachment();
            String fileName = course.getFileBytes().getOriginalFilename();
            assert fileName != null;
            int index = fileName.lastIndexOf(".");
            attachment.setOriginalFileName(fileName);
            attachment.setContentType(course.getFileBytes().getContentType());
            if(index != -1) {
                attachment.setFileExtension(fileName.substring(index));
            }
            attachment.setCreatedDate(LocalDateTime.now());
            byte[] fileByteArray = course.getFileBytes().getBytes();
            attachment.setContentSize(String.valueOf(course.getFileBytes().getSize()));
            attachment = attachmentService.uploadAttachment(attachment, fileByteArray);
            course.setCreatedDate(LocalDateTime.now());
            course.setCourseAttachId(Long.parseLong(attachment.getAttachmentId().toString()));

            courseRepository.save(course);
            return 1;
        } catch (Exception e) {
            logger.error("Error Found", e);
            return 0;
        }
    }

    @Override
    public Integer deleteCourse(Long courseId) {
        try {
            courseRepository.deleteById(courseId);
            return 1;
        } catch (Exception e) {
            logger.error("Error Found", e);
            return 0;
        }
    }

    @Override
    public Integer addNewUser(UserAuth user) {
        logger.info("Inside Add New User ServiceImpl");
        try {
            if(user.getPassword() == null || user.getPassword().equalsIgnoreCase("")) {
                user.setPassword(encoder.encode("CMSAdmin@1234"));
            }
            user.setLoginType("MANUAL");
            user.setCreatedDate(LocalDateTime.now());
            user.setUserName(generateUserName(user.getEmail()));
            userRepository.save(user);
        } catch (Exception e) {
            logger.error("Error Found" + e);
            return 0;
        }
        logger.info("Outside Add New User ServiceImpl");
        return 1;
    }

    @Override
    public String verifyUser(UserAuth user, HttpServletRequest request) {
        logger.info("Inside Verify User Serviceimpl");
        try {
            Authentication authentication = authenticationManager.authenticate(new
                    UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
            if(authentication.isAuthenticated()) {
                UserAuth user1 = userRepository.findByUserName(user.getUserName());
                if(user1 != null) {
                    String fingerPrintRequestDetails = generateFingerPrintHash(request);
                    Optional<Session> sessionDetails =  sessionRepository.findByRequestDetails(fingerPrintRequestDetails);
                    String sessionId;
                    if(sessionDetails.isPresent()) {
                        if(!sessionDetails.get().getUserName().equals(user.getUserName())) {
                            sessionRepository.deleteById(sessionDetails.get().getId());
                            sessionId = UUID.randomUUID().toString();
                            Session session = new Session();

                            session.setSessionId(sessionId);
                            session.setCreatedDate(LocalDateTime.now());
                            session.setStatus(1);
                            session.setUserName(user.getUserName());
                            session.setRequestDetails(fingerPrintRequestDetails);
                            sessionRepository.save(session);
                        }
                        else {
                            sessionId = sessionDetails.get().getSessionId();
                        }
                    }
                    else {
                        sessionId = UUID.randomUUID().toString();
                        Session session = new Session();

                        session.setSessionId(sessionId);
                        session.setCreatedDate(LocalDateTime.now());
                        session.setStatus(1);
                        session.setUserName(user.getUserName());
                        session.setRequestDetails(fingerPrintRequestDetails);
                        sessionRepository.save(session);
                    }
                    return jwtUtils.generateToken(user.getUserName(), user1.getRole(), sessionId);
                }
                else {
                    return null;
                }
            }
            else {
                return null;
            }
        } catch (Exception e) {
            logger.error("Exception during authentication: " , e);
            return null;
        }
    }

    public String generateUserName(String email) {
        String prefixEmail = email.contains("@") ? email.substring(0, email.indexOf("@")) : email;

        String prefix = null;
        if(prefixEmail.length() >= 4) {
            prefix = prefixEmail.substring(0, 4);
        }
        else {
            prefix = prefixEmail;
        }

        String randomUUID = UUID.randomUUID().toString().substring(0,4);
        int year = Year.now().getValue();
        return prefix.toUpperCase() + randomUUID.toUpperCase() + year;
    }

    private String generateFingerPrintHash(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if(ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }

        String userAgent = request.getHeader("User-Agent");
        if(userAgent == null) {
            userAgent = "unknown";
        }

        String normalizedUserAgent = userAgent.toLowerCase().replaceAll("\\s+", " ").trim();
        String combined = ipAddress + "_" + normalizedUserAgent;

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(combined.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
