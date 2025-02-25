package com.bit.myboardapp.dto;

import com.bit.myboardapp.entity.Comment;
import com.bit.myboardapp.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {

    private Long commentId;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String nickname;

    public CommentDto(Comment comment){
        this.commentId = comment.getCommentId();
        this.content = comment.getContent();
        this.createdDate = comment.getCreatedDate();
        this.modifiedDate = comment.getModifiedDate();
    }
}
