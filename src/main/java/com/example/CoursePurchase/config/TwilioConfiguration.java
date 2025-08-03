package com.example.CoursePurchase.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioConfiguration {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;


    public String getAccountSid() {
        return accountSid;
    }

    public String getAuthToken() {
        return authToken;
    }
}
