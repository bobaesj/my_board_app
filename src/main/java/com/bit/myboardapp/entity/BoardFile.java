package com.bit.myboardapp.entity;

import com.bit.myboardapp.dto.BoardFileDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@SequenceGenerator(
        name = "boardFileSeqGenerator",
        sequenceName = "BOARDFILE_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardFile {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "boardFileSeqGenerator"
    )
    private Long fileId;

    @ManyToOne
    @JoinColumn(name = "boardId", referencedColumnName = "boardId")
    private Board board;

    private String fileName;  // 파일 이름
    private String filePath;  // 파일 경로
    private String fileType;  // 파일 타입 (예: 이미지, PDF 등)
    private Long fileSize;    // 파일 크기

    public static BoardFile fromDto(Board board, BoardFileDto fileDto) {
        return BoardFile.builder()
                .board(board)
                .fileName(fileDto.getFileName())
                .filePath(fileDto.getFilePath())
                .fileType(fileDto.getFileType())
                .fileSize(fileDto.getFileSize())
                .build();
    }
}

