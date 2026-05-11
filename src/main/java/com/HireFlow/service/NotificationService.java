package com.HireFlow.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.HireFlow.entity.Notification;
import com.HireFlow.entity.User;
import com.HireFlow.repository.NotificationRepository;
import com.HireFlow.repository.UserRepo;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notifRepo;

    @Autowired
    private UserRepo userRepo;

    public void createNotification(String email, String message) {
        User user = userRepo.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Notification notif = new Notification();
        notif.setUser(user);
        notif.setMessage(message);
        notifRepo.save(notif);
    }

    public List<Notification> getNotifications(String email) {
        return notifRepo.findByUserEmailOrderByCreatedAtDesc(email);
    }

    public long getUnreadCount(String email) {
        return notifRepo.countByUserEmailAndIsRead(email, false);
    }

    public void markAllAsRead(String email) {
        List<Notification> notifs = notifRepo
            .findByUserEmailOrderByCreatedAtDesc(email);
        notifs.forEach(n -> n.setRead(true));
        notifRepo.saveAll(notifs);
    }
}