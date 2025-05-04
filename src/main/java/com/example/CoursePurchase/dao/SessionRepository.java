package com.example.CoursePurchase.dao;

import com.example.CoursePurchase.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    public Optional<Session> findByRequestDetails(String requestDetails);
}
