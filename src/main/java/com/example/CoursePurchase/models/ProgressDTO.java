package com.example.CoursePurchase.models;

public class ProgressDTO {

    Double progressTime;
    Long courseId;

    public ProgressDTO(Double progressTime, Long courseId) {
        this.progressTime = progressTime;
        this.courseId = courseId;
    }

    public Double getProgressTime() {
        return progressTime;
    }

    public void setProgressTime(Double progressTime) {
        this.progressTime = progressTime;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
