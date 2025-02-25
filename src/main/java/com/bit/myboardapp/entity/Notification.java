package com.bit.myboardapp.entity;

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
    @JoinColumn(name = "receiverId", referencedColumnName = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "senderId", referencedColumnName = "userId", nullable = false)
    private User sender;

    @Column(nullable = false)
    private boolean isRead = false;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Long relatedEntityId;

    @Column(nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();
}
