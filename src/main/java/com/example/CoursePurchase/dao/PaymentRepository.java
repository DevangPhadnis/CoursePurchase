package com.example.CoursePurchase.dao;

import com.example.CoursePurchase.models.Payment;
import com.example.CoursePurchase.models.PaymentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("SELECT c.genre, count(p.paymentId) FROM Payment p JOIN p.courses c GROUP BY c.genre")
    List<Object[]> countCoursesSoldByGenre();

    @Query("SELECT c.title, COUNT(p.paymentId) FROM Payment p JOIN p.courses c GROUP BY c.title ORDER BY COUNT(p.paymentId) DESC")
    List<Object[]> findTopCourses();

    @Query("SELECT c.genre, SUM(p.transactionAmount) FROM Payment p JOIN p.courses c GROUP BY c.genre")
    List<Object[]> findRevenueByGenre();

    @Query("SELECT new com.example.CoursePurchase.models.PaymentDTO(p.transactionId, p.transactionAmount, p.status, p.paymentMode, p.paymentDate, p.receiptAttachmentId," +
            "c.title, c.description, c.genre) FROM Payment p JOIN p.courses c WHERE p.userAuth.userId = :userId")
    List<PaymentDTO> fetchCoursePaymentDetails(@Param("userId") Long userId);
}
