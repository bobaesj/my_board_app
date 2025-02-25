package com.bit.myboardapp.dto;

import com.bit.myboardapp.entity.Board;
import com.bit.myboardapp.entity.User;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "제목은 필수입니다.")
    private String title;
    @NotBlank(message = "내용은 필수입니다.")
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Long viewCount;
    private List<CommentDto> comments;
    private List<BoardFileDto> boardFiles;
    private List<Long> deleteFileIds;

    public Board toEntity(User user) {
        return Board.builder()
                .boardId(boardId)
                .title(title)
                .content(content)
                .createdDate(createdDate)
                .modifiedDate(modifiedDate)
                .viewCount(viewCount)
                .user(user)
                .build();
    }
}
