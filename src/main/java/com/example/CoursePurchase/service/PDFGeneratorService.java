package com.example.CoursePurchase.service;

import java.util.Map;

public interface PDFGeneratorService {

    public byte[] generatePDF(Map<String, String> paymentDetailsMap);
}
