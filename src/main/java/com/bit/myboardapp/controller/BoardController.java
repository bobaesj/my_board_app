package com.bit.myboardapp.controller;

import com.bit.myboardapp.dto.BoardDto;
import com.bit.myboardapp.dto.CommentDto;
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
        ResponseDto<List<BoardDto>> responseDto = new ResponseDto<>();
        log.info("search title: {}, nickname: {}", title, nickname);

        List<BoardDto> searchResult = boardService.findBoards(title, nickname);

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
    public ResponseEntity<?> update(@PathVariable Long boardId,
                                    @RequestBody BoardDto boardDto,
                                    HttpServletRequest request){
        ResponseDto<BoardDto> responseDto = new ResponseDto<>();
        String email = (String) request.getAttribute("userEmail");

        log.info("update boardId: {}, boardDto: {}, email: {}", boardId, boardDto, email);

        BoardDto updateBoard = boardService.updateBoard(boardId, boardDto, email);
        responseDto.setStatusCode(HttpStatus.OK.value());
        responseDto.setStatusMessage("ok");
        responseDto.setItem(updateBoard);

        return ResponseEntity.ok(responseDto);
    }

    // 게시글 삭제
    @DeleteMapping("/{boardId}")
    public ResponseEntity<?> delete(@PathVariable Long boardId, HttpServletRequest request){
        ResponseDto<Void> responseDto = new ResponseDto<>();
        String email = (String) request.getAttribute("userEmail");

        log.info("delete boardId: {}, email: {}", boardId, email);
        boardService.deleteBoard(boardId, email);
        responseDto.setStatusCode(HttpStatus.OK.value());
        responseDto.setStatusMessage("deleted");

        return ResponseEntity.ok(responseDto);
    }

    // 댓글 작성
    @PostMapping("/{boardId}/comments")
    public ResponseEntity<?> writeComment(@PathVariable Long boardId,
                                          @RequestBody CommentDto commentDto,
                                          HttpServletRequest request){
        ResponseDto<CommentDto> responseDto = new ResponseDto<>();
        String email = (String) request.getAttribute("userEmail");

        log.info("writeComment boardId: {}, commentDto: {}", boardId, commentDto);
        CommentDto writeCommentDto = boardService.writeComment(boardId, commentDto, email);
        responseDto.setStatusCode(HttpStatus.CREATED.value());
        responseDto.setStatusMessage("created");
        responseDto.setItem(writeCommentDto);

        return ResponseEntity.ok(responseDto);
    }

    // 댓글 수정
    @PutMapping("/{boardId}/comments/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long boardId,
                                           @RequestBody CommentDto commentDto,
                                           HttpServletRequest request) {
        ResponseDto<CommentDto> responseDto = new ResponseDto<>();
        String email = (String) request.getAttribute("userEmail");

        log.info("UpdateComment boardId: {}, commentId: {}, email: {}", boardId, commentDto, email);
        CommentDto UpdateComment = boardService.updateComment(boardId, commentDto, email);
        responseDto.setStatusCode(HttpStatus.OK.value());
        responseDto.setStatusMessage("ok");
        responseDto.setItem(UpdateComment);

        return ResponseEntity.ok(responseDto);
    }

    // 댓글 삭제
    @DeleteMapping("/{boardId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long boardId,
                                           @PathVariable Long commentId,
                                           HttpServletRequest request){
        ResponseDto<Void> responseDto = new ResponseDto<>();
        String email = (String) request.getAttribute("userEmail");

        log.info("deleteComment boardId: {}, commentId: {}, email: {}", boardId, commentId, email);
        boardService.deleteComment(boardId, commentId, email);
        responseDto.setStatusCode(HttpStatus.OK.value());
        responseDto.setStatusMessage("deleted");

        return ResponseEntity.ok(responseDto);
    }

    // 게시글 상세 정보와 댓글 리스트 포함 반환
    @GetMapping("/{boardId}")
    public ResponseEntity<?> getBoardById(@PathVariable Long boardId){
        ResponseDto<BoardDto> responseDto = new ResponseDto<>();

        log.info("getBoardById boardId: {}", boardId);

        BoardDto boardDto = boardService.getBoardWithComments(boardId);
        responseDto.setStatusCode(HttpStatus.OK.value());
        responseDto.setStatusMessage("ok");
        responseDto.setItem(boardDto);

        return ResponseEntity.ok(responseDto);
    }

}
