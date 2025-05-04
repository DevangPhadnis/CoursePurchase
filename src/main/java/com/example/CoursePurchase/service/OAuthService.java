package com.example.CoursePurchase.service;

import jakarta.servlet.http.HttpServletRequest;

public interface OAuthService {

    public String oAuthLogin(String tokenId, HttpServletRequest request);
}
