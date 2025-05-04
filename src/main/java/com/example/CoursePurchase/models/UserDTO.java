package com.example.CoursePurchase.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserDTO {

    private String fullName;
    private Long userId;
    private String loginType;
    private String gender;
    private String role;
    private String email;
    private LocalDateTime userCreatedDate;
    private LocalDateTime userDetailsCreatedDate;
    private LocalDate dateOfBirth;
    private Long userDetailsId;
    private String userName;
    private String mobileNumber;
    private String addressOne;
    private String addressTwo;
    private String addressThree;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getUserCreatedDate() {
        return userCreatedDate;
    }

    public void setUserCreatedDate(LocalDateTime userCreatedDate) {
        this.userCreatedDate = userCreatedDate;
    }

    public LocalDateTime getUserDetailsCreatedDate() {
        return userDetailsCreatedDate;
    }

    public void setUserDetailsCreatedDate(LocalDateTime userDetailsCreatedDate) {
        this.userDetailsCreatedDate = userDetailsCreatedDate;
    }

    public Long getUserDetailsId() {
        return userDetailsId;
    }

    public void setUserDetailsId(Long userDetailsId) {
        this.userDetailsId = userDetailsId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAddressOne() {
        return addressOne;
    }

    public void setAddressOne(String addressOne) {
        this.addressOne = addressOne;
    }

    public String getAddressTwo() {
        return addressTwo;
    }

    public void setAddressTwo(String addressTwo) {
        this.addressTwo = addressTwo;
    }

    public String getAddressThree() {
        return addressThree;
    }

    public void setAddressThree(String addressThree) {
        this.addressThree = addressThree;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
