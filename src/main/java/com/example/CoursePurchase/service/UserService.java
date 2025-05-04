package com.example.CoursePurchase.service;

import com.example.CoursePurchase.models.*;
import com.razorpay.Order;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface UserService {

    public Integer updateUserDetails(UserDetails userDetails);

    public List<CourseDTO> fetchUnpiadCourseList(String username, Integer pageNumber, Integer rowCount);

    public List<CourseDTO> fetchPurchasedCourseList(String username, Integer pageNumber, Integer rowCount);

    public Order createPaymentOrder(Double amount);

    public Integer verifyPayment(String orderId, String paymentId, String signature, String username, Long courseId, Double amount);

    public List<PaymentDTO> fetchPaymentDetails(String username);

    public AttachmentDTO fetchAttachmentDetails(Long attachmentId);
}
