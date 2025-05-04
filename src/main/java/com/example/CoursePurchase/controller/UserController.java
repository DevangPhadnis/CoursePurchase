package com.example.CoursePurchase.controller;

import com.example.CoursePurchase.models.*;
import com.example.CoursePurchase.service.UserService;
import com.razorpay.Order;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/save-details")
    public ResponseEntity<?> saveUserDetails(@RequestBody UserDetails userDetails) {
        Integer result = userService.updateUserDetails(userDetails);
        if(result == 1) {
            Response response = new Response();
            response.setData(null);
            response.setStatus("1");
            response.setMessage("Details Updated Successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            Response response = new Response();
            response.setData(null);
            response.setStatus("0");
            response.setMessage("Error in Updating User Details");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/course-details")
    public ResponseEntity<?> fetchCourseList(@RequestParam Integer pageNumber, @RequestParam Integer rowCount, Principal principal) {
        String username = principal.getName();
        List<CourseDTO> courseDTOList = userService.fetchUnpiadCourseList(username, pageNumber, rowCount);

        if(courseDTOList != null && !courseDTOList.isEmpty()) {
            Response response = new Response();
            response.setData(courseDTOList);
            response.setStatus("1");
            response.setMessage("Course List Fetched Successfully");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            Response response = new Response();
            response.setData(null);
            response.setStatus("0");
            response.setMessage("Error in Fetching Course Details");

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/purchased-course-details")
    public ResponseEntity<?> fetchPurchasedCourseList(@RequestParam Integer pageNumber, @RequestParam Integer rowCount, Principal principal) {
        String username = principal.getName();
        List<CourseDTO> courseDTOList = userService.fetchPurchasedCourseList(username, pageNumber, rowCount);

        if(courseDTOList != null) {
            Response response = new Response();
            response.setData(courseDTOList);
            response.setStatus("1");
            if(courseDTOList.isEmpty()) {
                response.setMessage("No Course Purchased Yet");
            }
            else {
                response.setMessage("Course List Fetched Successfully");
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            Response response = new Response();
            response.setData(null);
            response.setStatus("0");
            response.setMessage("Error in Fetching Course Details");

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create-payment-order")
    public ResponseEntity<?> createPaymentOrder(@RequestBody Map<String, Integer> inputMap) {
        String strAmount = inputMap.get("amount").toString();
        Double amount = Double.parseDouble(strAmount);

        Order order = userService.createPaymentOrder(amount);
        Map<String, Object> orderData = new JSONObject(order.toString()).toMap();
        if(orderData != null) {
            Response response = new Response();
            response.setData(orderData);
            response.setStatus("1");
            response.setMessage("Order created Successfully");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            Response response = new Response();
            response.setData(null);
            response.setStatus("0");
            response.setMessage("Error in razorpay order creation");

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/verify-payment")
    public ResponseEntity<?> verifyPayment(@RequestBody Map<String, String> paymentData, Principal principal) {
        String username = principal.getName();

        String signature = paymentData.get("signature");
        Long courseId = Long.parseLong(paymentData.get("courseId"));
        String orderId = paymentData.get("orderId");
        String paymentId = paymentData.get("paymentId");
        String strAmount =  paymentData.get("amount");
        Double amount = Double.parseDouble(strAmount);

        Integer result = userService.verifyPayment(orderId, paymentId, signature, username, courseId, amount);
        if(result == 1) {
            Response response = new Response();
            response.setData(null);
            response.setStatus("1");
            response.setMessage("Payment Verified Successfully");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            Response response = new Response();
            response.setData(null);
            response.setStatus("0");
            response.setMessage("Error in payment verification");

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/fetch-payment-details")
    public ResponseEntity<?> fetchPaymentDetails(Principal principal) {
        String username = principal.getName();
        List<PaymentDTO> paymentDTOList = userService.fetchPaymentDetails(username);

        if(paymentDTOList != null) {
            Response response = new Response();
            response.setData(paymentDTOList);
            response.setStatus("1");
            response.setMessage("Payment Details Fetched Successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            Response response = new Response();
            response.setData(null);
            response.setStatus("0");
            response.setMessage("Error in fetching Payment Details");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/download-attachment")
    public ResponseEntity<?> downloadAttachment(@RequestParam Long attachmentId) {
        AttachmentDTO attachmentDTO = userService.fetchAttachmentDetails(attachmentId);

        if(attachmentDTO != null) {
            Response response = new Response();
            response.setData(attachmentDTO);
            response.setStatus("1");
            response.setMessage("Attachment Details Fetched Successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            Response response = new Response();
            response.setData(null);
            response.setStatus("0");
            response.setMessage("Error in fetching Attachment Details");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
