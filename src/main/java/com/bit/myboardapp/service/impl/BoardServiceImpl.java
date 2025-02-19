package com.bit.myboardapp.service.impl;

import com.bit.myboardapp.dto.BoardDto;
import com.bit.myboardapp.entity.Board;
import com.bit.myboardapp.entity.User;
import com.bit.myboardapp.repository.BoardRepository;
import com.bit.myboardapp.repository.UserRepository;
import com.bit.myboardapp.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Override
    public BoardDto post(BoardDto boardDto, String email) {

        User user = userRepository.findByEmail(email) // email로 유저 조회
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Board board = boardDto.toEntity(user); // User 정보 매핑
        return boardRepository.save(board).toDto();
    }
}
