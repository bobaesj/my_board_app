package com.bit.myboardapp.controller;

import com.bit.myboardapp.dto.BoardDto;
import com.bit.myboardapp.dto.ResponseDto;
import com.bit.myboardapp.jwt.JwtProvider;
import com.bit.myboardapp.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;
    private final JwtProvider jwtProvider;

    // 게시글 등록
    @PostMapping("/post")
    public ResponseEntity<?> post(@RequestBody BoardDto boardDto,
                                  @RequestHeader("Authorization") String authHeader) {
        ResponseDto<BoardDto> responseDto = new ResponseDto<>();
        String token = authHeader.replace("Bearer ", ""); // Bearer 제거
        String email = jwtProvider.validatedAndGetSubject(token); // JWT에서 email(subject) 추출

        try {
            log.info("post boardDto: {}", boardDto);
            BoardDto postBoardDto = boardService.post(boardDto, email);

            responseDto.setStatusCode(HttpStatus.CREATED.value());
            responseDto.setStatusMessage("created");
            responseDto.setItem(postBoardDto);

            return ResponseEntity.ok(responseDto);
        } catch (Exception e){
            log.error("post error: {}", e.getMessage());
            responseDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDto.setStatusMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(responseDto);
        }
    }
    // 게시글 조회
}
