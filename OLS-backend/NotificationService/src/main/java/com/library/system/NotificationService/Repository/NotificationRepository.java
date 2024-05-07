package com.library.system.NotificationService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.system.NotificationService.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
     
}
