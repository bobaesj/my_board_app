package com.bit.myboardapp.dto;

import com.bit.myboardapp.entity.Board;
import com.bit.myboardapp.entity.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class BoardDto {

    private Long boardId;
    private Long userId;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private List<CommentDto> comments;

    public Board toEntity(User user) {
        return Board.builder()
                .boardId(boardId)
                .title(title)
                .content(content)
                .createdDate(createdDate)
                .modifiedDate(modifiedDate)
                .user(user)
                .build();
    }
}
