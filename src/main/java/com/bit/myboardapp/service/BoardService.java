package com.bit.myboardapp.service;

import com.bit.myboardapp.dto.BoardDto;
import com.bit.myboardapp.dto.CommentDto;
import com.bit.myboardapp.entity.Board;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface BoardService {

    List<Board> findBoards(String title, String nickname);

    BoardDto post(BoardDto boardDto, String email);

    BoardDto updateBoard(Long boardId, BoardDto boardDto, String email);

    BoardDto getBoardWithComments(Long boardId);

    CommentDto writeComment(Long boardId, CommentDto commentDto, String email);

    CommentDto updateComment(Long boardId, CommentDto commentDto, String email);

    void deleteComment(Long boardId, Long commentId, String email);

    void deleteBoard(Long boardId, String email);
}
