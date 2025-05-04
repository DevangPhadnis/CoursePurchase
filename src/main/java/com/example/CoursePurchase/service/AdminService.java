package com.example.CoursePurchase.service;

import com.example.CoursePurchase.models.CourseDTO;
import com.example.CoursePurchase.models.Courses;
import com.example.CoursePurchase.models.UserAuth;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface AdminService {

    public Object fetchUserDetails(String username);

    public List<CourseDTO> fetchCourseDetails();

    public Integer addCourse(Courses course);

    public Integer deleteCourse(Long courseId);

    public Integer addNewUser(UserAuth user);

    public String verifyUser(UserAuth user, HttpServletRequest request);
}
