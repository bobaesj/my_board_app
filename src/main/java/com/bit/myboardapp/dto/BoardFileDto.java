package com.bit.myboardapp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class BoardFileDto {

    private Long fileId;
    private String fileName;
    private String filePath;
    private String fileType;
    private Long fileSize;

}
