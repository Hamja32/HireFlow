package com.HireFlow.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.HireFlow.entity.Notification;
import com.HireFlow.service.NotificationService;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notifService;

    @PreAuthorize("hasAuthority('ROLE_SEEKER')")
    @GetMapping
    public ResponseEntity<List<Notification>> getNotifications(
            Authentication auth) {
        return ResponseEntity.ok(
            notifService.getNotifications(auth.getName()));
    }

    @PreAuthorize("hasAuthority('ROLE_SEEKER')")
    @GetMapping("/unread-count")
    public ResponseEntity<Long> getUnreadCount(Authentication auth) {
        return ResponseEntity.ok(
            notifService.getUnreadCount(auth.getName()));
    }

    @PreAuthorize("hasAuthority('ROLE_SEEKER')")
    @PutMapping("/mark-read")
    public ResponseEntity<String> markAllAsRead(Authentication auth) {
        notifService.markAllAsRead(auth.getName());
        return ResponseEntity.ok("Marked as read");
    }
}