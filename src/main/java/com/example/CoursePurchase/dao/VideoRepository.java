package com.example.CoursePurchase.dao;

import com.example.CoursePurchase.models.Courses;
import com.example.CoursePurchase.models.UserAuth;
import com.example.CoursePurchase.models.VideoProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<VideoProgress, Long> {
    Optional<VideoProgress> findByUserAuthAndCourses(UserAuth userAuth, Courses courses);
}
