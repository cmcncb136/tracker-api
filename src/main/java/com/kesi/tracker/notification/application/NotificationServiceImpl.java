package com.kesi.tracker.notification.application;

import com.kesi.tracker.notification.application.repository.NotificationRepository;
import com.kesi.tracker.notification.domain.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;

    @Override
    public void send(Notification notification) {
        notificationRepository.save(notification);
    }

    @Override
    public void read(Long notificationId) {
        Optional<Notification> notificationOptional = notificationRepository.findById(notificationId);

        if(notificationOptional.isEmpty()) return;
        Notification notification = notificationOptional.get();

        if(notification.getIsRead()) return; //이미 읽은 경우
        notification.read();

        notificationRepository.save(notification);
    }

    @Override
    public List<Notification> findByReceiverId(Long receiverId) {
        return notificationRepository.findByReceiverId(receiverId);
    }

    @Override
    public Page<Notification> findByReceiverId(Long receiverId, Pageable pageable) {
        return notificationRepository.findByReceiverId(receiverId, pageable);
    }
}
