package com.library.system.NotificationService.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.system.NotificationService.Service.NotificationService;
import com.library.system.NotificationService.entity.Notification;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("api/notifications")
public class NotificationController {
    private NotificationService notificationService;

    @PostMapping("/sendNotification")
    public ResponseEntity<String> sendNotification(@RequestBody Notification notification) {
        // Call the service method to send the notification
        notificationService.sendNotification(notification);
        
        // Return a success response
        return ResponseEntity.status(HttpStatus.OK).body("Notification sent successfully");
    }
}
