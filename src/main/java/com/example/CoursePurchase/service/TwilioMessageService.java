package com.example.CoursePurchase.service;

public interface TwilioMessageService {

    public void sendWhatsappMessage(String phoneNumber, String message, String preSignedUrl);
}
