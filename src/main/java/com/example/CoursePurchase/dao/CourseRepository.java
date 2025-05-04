package com.example.CoursePurchase.dao;

import com.example.CoursePurchase.models.Courses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Courses, Long> {

    @Query("SELECT c FROM Courses c WHERE c.courseId NOT IN" +
            " (SELECT p.courses.courseId FROM Payment p WHERE p.userAuth.userId = :userId AND p.status = 'SUCCESS')")
    Page<Courses> findUnpurchasedCoursesByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT c FROM Courses c WHERE c.courseId IN" +
            " (SELECT p.courses.courseId FROM Payment p WHERE p.userAuth.userId = :userId AND p.status = 'SUCCESS')")
    Page<Courses> findPurchasedCourse(@Param("userId") Long userId, Pageable pageable);
}
