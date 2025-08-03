package com.example.CoursePurchase.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = { "user_id", "course_id" })
})
public class VideoProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long progressId;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "userId", foreignKey = @ForeignKey(name = "fk_progress_user"))
    private UserAuth userAuth;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", referencedColumnName = "courseId", foreignKey = @ForeignKey(name = "fk_progress_course"))
    private Courses courses;

    @Column(columnDefinition = "DOUBLE")
    private Double progressTime;

    @Column(columnDefinition = "DATETIME")
    private LocalDateTime updatedTime;

    public Long getProgressId() {
        return progressId;
    }

    public void setProgressId(Long progressId) {
        this.progressId = progressId;
    }

    public UserAuth getUserAuth() {
        return userAuth;
    }

    public void setUserAuth(UserAuth userAuth) {
        this.userAuth = userAuth;
    }

    public Courses getCourses() {
        return courses;
    }

    public void setCourses(Courses courses) {
        this.courses = courses;
    }

    public Double getProgressTime() {
        return progressTime;
    }

    public void setProgressTime(Double progressTime) {
        this.progressTime = progressTime;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }
}
