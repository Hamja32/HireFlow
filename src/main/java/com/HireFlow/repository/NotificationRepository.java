package com.HireFlow.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.HireFlow.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserEmailOrderByCreatedAtDesc(String email);
    long countByUserEmailAndIsRead(String email, boolean isRead);
}