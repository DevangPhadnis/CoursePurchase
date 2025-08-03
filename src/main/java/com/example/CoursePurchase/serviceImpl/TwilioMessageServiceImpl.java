package com.example.CoursePurchase.serviceImpl;

import com.example.CoursePurchase.service.TwilioMessageService;
import org.springframework.stereotype.Service;

@Service
public class TwilioMessageServiceImpl implements TwilioMessageService {
    @Override
    public void sendWhatsappMessage(String phoneNumber, String message, String preSignedUrl) {

    }
}
