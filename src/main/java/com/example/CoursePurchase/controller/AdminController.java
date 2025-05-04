package com.example.CoursePurchase.controller;

import com.example.CoursePurchase.models.*;
import com.example.CoursePurchase.service.AdminService;
import com.example.CoursePurchase.service.PaymentService;
import com.example.CoursePurchase.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/register")
    public ResponseEntity<?> addUser(@RequestBody UserAuth user) {
        logger.info("Inside Add New User Controller");

        adminService.addNewUser(user);

        logger.info("Outside Add New User Controller");
        Response response = new Response();
        response.setStatus("1");
        response.setMessage("User Added Successfully");
        response.setData(null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserAuth user, HttpServletRequest request) {
        logger.info("Inside Verify User Controller");
        String token = adminService.verifyUser(user, request);
        if(token != null) {
            Response response = new Response();
            response.setStatus("1");
            response.setMessage("Login Success");
            response.setData(token);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            Response response = new Response();
            response.setStatus("0");
            response.setMessage("Login Credentials are in correct");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/user-profile")
    public ResponseEntity<?> userProfile(Principal principal) {
        String username = principal.getName();
        UserDTO userDTO = (UserDTO) adminService.fetchUserDetails(username);
        if(userDTO != null) {
            Response response = new Response();
            response.setData(userDTO);
            response.setStatus("1");
            response.setMessage("");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            Response response = new Response();
            response.setData(userDTO);
            response.setStatus("0");
            response.setMessage("Error in fetching User Profile");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/fetch-course")
    public ResponseEntity<?> fetchCourses() {
        List<CourseDTO> courseDTOS = adminService.fetchCourseDetails();

        if(courseDTOS != null) {
            Response response = new Response();
            response.setData(courseDTOS);
            response.setStatus("1");
            response.setMessage("Courses fetched successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        else {
            Response response = new Response();
            response.setData(null);
            response.setStatus("0");
            response.setMessage("Error in fetching Courses");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @PostMapping("/add-courses")
    public ResponseEntity<?> addCourses(@RequestBody Courses courses) {
        Integer result = adminService.addCourse(courses);
        if(result == 1) {
            Response response = new Response();
            response.setData(null);
            response.setMessage("Course Added Successfully");
            response.setStatus("1");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            Response response = new Response();
            response.setData(null);
            response.setMessage("Error in Adding new course");
            response.setStatus("0");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete-course")
    public ResponseEntity<?> deleteCourse(@RequestParam Long courseId) {
        Integer result = adminService.deleteCourse(courseId);
        if(result == 1) {
            Response response = new Response();
            response.setData(null);
            response.setMessage("Course Deleted Successfully");
            response.setStatus("1");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            Response response = new Response();
            response.setData(null);
            response.setMessage("Error in Deleted course");
            response.setStatus("0");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sales-by-genre")
    public ResponseEntity<?> fetchSalesByGenre() {
        List<Object[]> salesByGenre = paymentService.fetchSalesByGenre();
        if(salesByGenre != null) {
            Map<String, Long> result = salesByGenre.stream().collect(Collectors.toMap(
                    row -> (String) row[0],
                    row -> (Long) row[1]
            ));

            Response response = new Response();
            response.setData(result);
            response.setStatus("1");
            response.setMessage("Sales by Genre Fetched Successfully");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            Response response = new Response();
            response.setMessage("Error in Fetching Sales by Genre");
            response.setData(null);
            response.setStatus("0");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/top-courses")
    public ResponseEntity<?> fetchTopSellingCourses() {
        List<Object[]> topCourses = paymentService.fetchTopCourses();
        if(topCourses != null) {
            List<Map<String, Object>> result = topCourses.stream().map(row -> Map.of(
                    "title", row[0],
                    "count", row[1]
            )).collect(Collectors.toList());

            Response response = new Response();
            response.setData(result);
            response.setStatus("1");
            response.setMessage("Top Courses Fetched Successfully");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            Response response = new Response();
            response.setMessage("Error in Fetching Top Selling Courses");
            response.setData(null);
            response.setStatus("0");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/revenue-by-genre")
    public ResponseEntity<?> fetchRevenueByGenre() {
        List<Object[]> revenueByGenre = paymentService.fetchRevenueByGenre();
        if(revenueByGenre != null) {
            Map<String, Double> result = revenueByGenre.stream().collect(Collectors.toMap(
                    row -> (String) row[0],
                    row -> (Double) row[1]
            ));

            Response response = new Response();
            response.setData(result);
            response.setStatus("1");
            response.setMessage("Revenue by Genre Fetched Successfully");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            Response response = new Response();
            response.setMessage("Error in Fetching Revenue by Genre");
            response.setData(null);
            response.setStatus("0");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
