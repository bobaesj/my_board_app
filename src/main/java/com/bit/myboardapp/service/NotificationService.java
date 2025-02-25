package com.bit.myboardapp.service;

import com.bit.myboardapp.dto.NotificationDto;

import java.util.List;

public interface NotificationService {
    List<NotificationDto> getNotifications(String email);

    NotificationDto getNotification(Long noticeId);
}
