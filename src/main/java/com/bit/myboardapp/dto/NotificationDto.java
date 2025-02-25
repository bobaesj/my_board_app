package com.bit.myboardapp.dto;

import com.bit.myboardapp.entity.Notification;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDto {

    private Long noticeId;
    private Long receiverId;
    private Long senderId;
    private String content;
    private LocalDateTime createdDate;

    // Notification -> NotificationDto 변환
    public static NotificationDto fromEntity(Notification notification) {
        return NotificationDto.builder()
                .noticeId(notification.getNoticeId())
                .receiverId(notification.getUser().getUserId())
                .senderId(notification.getSender().getUserId())
                .content(notification.getContent())
                .createdDate(notification.getCreatedDate())
                .build();
    }
}
