package com.bit.myboardapp.service.impl;

import com.bit.myboardapp.dto.NotificationDto;
import com.bit.myboardapp.entity.Notification;
import com.bit.myboardapp.repository.NotificationRepository;
import com.bit.myboardapp.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public List<NotificationDto> getNotifications(String email) {
        return notificationRepository.findByUser_Email(email).stream()
                .map(Notification::toDto).collect(Collectors.toList());
    }

    @Override
    public NotificationDto getNotification(Long noticeId) {

        Notification notice = notificationRepository.findById(noticeId).orElseThrow(
                () -> new IllegalArgumentException("noticeId not exist")
        );

        return notice.toDto();
    }


}
