package com.bit.myboardapp.service;

import com.bit.myboardapp.dto.BoardDto;

public interface BoardService {
    BoardDto post(BoardDto boardDto, String email);
}
