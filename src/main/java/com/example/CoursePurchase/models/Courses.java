package com.example.CoursePurchase.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "course_details")
public class Courses {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long courseId;

    private String title;
    private String description;
    private String genre;
    private String thumbnailUrl;
    private String courseUrl;
    @Transient
    private MultipartFile fileBytes;

    private Long courseAttachId;

    private LocalDateTime createdDate;

    private Double price;

    @OneToMany(mappedBy = "courses", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Payment> paymentList;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
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

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getCourseUrl() {
        return courseUrl;
    }

    public void setCourseUrl(String courseUrl) {
        this.courseUrl = courseUrl;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }

    public MultipartFile getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(MultipartFile fileBytes) {
        this.fileBytes = fileBytes;
    }

    public Long getCourseAttachId() {
        return courseAttachId;
    }

    public void setCourseAttachId(Long courseAttachId) {
        this.courseAttachId = courseAttachId;
    }
}
