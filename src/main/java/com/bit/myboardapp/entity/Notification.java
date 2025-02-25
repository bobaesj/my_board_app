package com.bit.myboardapp.entity;

import com.bit.myboardapp.dto.NotificationDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@SequenceGenerator(
        name = "NotificationSeqGenerator",
        sequenceName = "NOTIFICATION_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "NotificationSeqGenerator"
    )
    private Long noticeId;

    @ManyToOne
    @JoinColumn(name = "receiverId", referencedColumnName = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "senderId", referencedColumnName = "userId")
    private User sender;

    private String content;
    private Long relatedEntityId;
    private LocalDateTime createdDate;

    public NotificationDto toDto() {
        return NotificationDto.builder()
                .noticeId(noticeId)
                .receiverId(user.getUserId())
                .senderId(sender.getUserId())
                .content(getContent())
                .createdDate(getCreatedDate())
                .build();
    }
}
