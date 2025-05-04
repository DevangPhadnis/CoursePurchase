package com.example.CoursePurchase.models;

import java.time.LocalDateTime;

public class PaymentDTO {

    private String transactionId;
    private Double transactionAmount;
    private String status;
    private String paymentMode;
    private LocalDateTime paymentDate;
    private Long receiptAttachmentId;

    private String title;
    private String description;
    private String genre;

    public PaymentDTO(String transactionId, Double transactionAmount, String status, String paymentMode, LocalDateTime paymentDate, Long receiptAttachmentId, String title, String description, String genre) {
        this.transactionId = transactionId;
        this.transactionAmount = transactionAmount;
        this.status = status;
        this.paymentMode = paymentMode;
        this.paymentDate = paymentDate;
        this.receiptAttachmentId = receiptAttachmentId;
        this.title = title;
        this.description = description;
        this.genre = genre;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Long getReceiptAttachmentId() {
        return receiptAttachmentId;
    }

    public void setReceiptAttachmentId(Long receiptAttachmentId) {
        this.receiptAttachmentId = receiptAttachmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
