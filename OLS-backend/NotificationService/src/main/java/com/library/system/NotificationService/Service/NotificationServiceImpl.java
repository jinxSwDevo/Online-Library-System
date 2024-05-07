package com.library.system.NotificationService.Service;

import org.springframework.stereotype.Service;

import com.library.system.NotificationService.Repository.NotificationRepository;
import com.library.system.NotificationService.entity.Notification;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }
    

    @Override
    public void sendNotification(Notification notification) {
    
        notificationRepository.save(notification);
    }
}
