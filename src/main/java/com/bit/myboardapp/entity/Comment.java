package com.bit.myboardapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@SequenceGenerator(
        name = "commentSeqGenerator",
        sequenceName = "COMMENT_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "commentSeqGenerator"
    )
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "boardId", referencedColumnName = "boardId")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
