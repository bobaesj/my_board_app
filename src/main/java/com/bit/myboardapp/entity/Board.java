package com.bit.myboardapp.entity;

import com.bit.myboardapp.dto.BoardDto;
import com.bit.myboardapp.dto.BoardFileDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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

    @Column(nullable = false)
    @Builder.Default
    private Long viewCount = 0L;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> comments;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardFile> boardFiles;

    public BoardDto toDto() {
        return BoardDto.builder()
                .boardId(boardId)
                .userId(user.getUserId())
                .title(title)
                .content(content)
                .createdDate(createdDate)
                .modifiedDate(modifiedDate)
                .viewCount(viewCount)
                .boardFiles(
                        boardFiles != null
                                ? boardFiles.stream()
                                .map(file -> BoardFileDto.builder()
                                        .fileId(file.getFileId())
                                        .fileName(file.getFileName())
                                        .filePath(file.getFilePath())
                                        .fileType(file.getFileType())
                                        .fileSize(file.getFileSize())
                                        .build())
                                .toList()
                                : null
                )
                .build();
    }
}