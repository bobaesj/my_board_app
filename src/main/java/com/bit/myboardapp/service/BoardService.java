package com.bit.myboardapp.service;

import com.bit.myboardapp.dto.BoardDto;
import com.bit.myboardapp.entity.Board;

import java.util.List;

public interface BoardService {

    List<Board> findBoards(String title, String nickname);

    BoardDto post(BoardDto boardDto, String email);

    BoardDto updateBoard(Long boardId, BoardDto boardDto);

    BoardDto getBoardById(Long boardId);
}
