package com.bit.myboardapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@SequenceGenerator(
        name = "boardSeqGenerator",
        sequenceName = "BOARD_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "boardSeqGenerator"
    )
    private Long boardId;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

}