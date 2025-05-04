package com.example.CoursePurchase.service;

import java.util.List;

public interface PaymentService {

    public List<Object[]> fetchSalesByGenre();

    public List<Object[]> fetchTopCourses();

    public List<Object[]> fetchRevenueByGenre();
}
