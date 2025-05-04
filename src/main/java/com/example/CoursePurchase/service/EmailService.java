package com.example.CoursePurchase.service;

public interface EmailService {

    public void sendEmail(String to, String subject, String body, String fileName, byte[] fileDetails) throws Exception;
}
