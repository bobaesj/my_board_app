package com.bit.myboardapp.controller;

import com.bit.myboardapp.dto.BoardDto;
import com.bit.myboardapp.dto.ResponseDto;
import com.bit.myboardapp.entity.Board;
import com.bit.myboardapp.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    // 게시글 조회
    @GetMapping
    public ResponseEntity<?> search(@RequestParam(required = false) String title,
                                    @RequestParam(required = false) String nickname) {
        ResponseDto<List<Board>> responseDto = new ResponseDto<>();
        log.info("search title: {}, nickname: {}", title, nickname);

        List<Board> searchResult = boardService.findBoards(title, nickname);

        responseDto.setStatusCode(HttpStatus.OK.value());
        responseDto.setStatusMessage("ok");
        responseDto.setItem(searchResult);

        return ResponseEntity.ok(responseDto);
    }

    // 게시글 등록
    @PostMapping("/post")
    public ResponseEntity<?> post(
            @RequestBody BoardDto boardDto, HttpServletRequest request){
        ResponseDto<BoardDto> responseDto = new ResponseDto<>();
        String email = (String) request.getAttribute("userEmail");

        log.info("post boardDto with files: {}", boardDto);
        BoardDto postBoardDto = boardService.post(boardDto, email);

        responseDto.setStatusCode(HttpStatus.CREATED.value());
        responseDto.setStatusMessage("created");
        responseDto.setItem(postBoardDto);

        return ResponseEntity.ok(responseDto);
    }

    // 게시글 수정
    @PutMapping("/{boardId}")
    public ResponseEntity<?> update(@PathVariable Long boardId, @RequestBody BoardDto boardDto){
        ResponseDto<BoardDto> responseDto = new ResponseDto<>();

        log.info("Update request for board id {}: {}", boardId, boardDto);

        BoardDto updateBoard = boardService.updateBoard(boardId, boardDto);
        responseDto.setStatusCode(HttpStatus.OK.value());
        responseDto.setStatusMessage("ok");
        responseDto.setItem(updateBoard);

        return ResponseEntity.ok(responseDto);
    }

    // 게시글 상세 정보 API
    @GetMapping("/{boardId}")
    public ResponseEntity<?> getBoardById(@PathVariable Long boardId){
        ResponseDto<BoardDto> responseDto = new ResponseDto<>();

        log.info("getBoardById boardId: {}", boardId);

        BoardDto boardDto = boardService.getBoardById(boardId);
        responseDto.setStatusCode(HttpStatus.OK.value());
        responseDto.setStatusMessage("ok");
        responseDto.setItem(boardDto);

        return ResponseEntity.ok(responseDto);
    }
}
