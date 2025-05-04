package com.example.CoursePurchase.serviceImpl;

import com.example.CoursePurchase.dao.PaymentRepository;
import com.example.CoursePurchase.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public List<Object[]> fetchSalesByGenre() {
        try {
            List<Object[]> result = paymentRepository.countCoursesSoldByGenre();
            return result;
        } catch (Exception e) {
            logger.error("Error Found", e);
            return null;
        }
    }

    @Override
    public List<Object[]> fetchTopCourses() {
        try {
            List<Object[]> result = paymentRepository.findTopCourses();
            return result;
        } catch (Exception e) {
            logger.error("Error Found", e);
            return null;
        }
    }

    @Override
    public List<Object[]> fetchRevenueByGenre() {
        try {
            List<Object[]> result = paymentRepository.findRevenueByGenre();
            return result;
        } catch (Exception e) {
            logger.error("Error Found", e);
            return null;
        }
    }
}
