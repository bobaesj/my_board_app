package com.bit.myboardapp.entity;

import jakarta.persistence.*;
import lombok.*;

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
    private int senderId;
    private String content;
}
