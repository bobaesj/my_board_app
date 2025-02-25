package com.bit.myboardapp.service.impl;

import com.bit.myboardapp.dto.BoardDto;
import com.bit.myboardapp.entity.Board;
import com.bit.myboardapp.entity.BoardFile;
import com.bit.myboardapp.entity.User;
import com.bit.myboardapp.repository.BoardFileRepository;
import com.bit.myboardapp.repository.BoardRepository;
import com.bit.myboardapp.repository.UserRepository;
import com.bit.myboardapp.service.BoardService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;

    @Override
    public List<Board> findBoards(String title, String nickname) {

        return boardRepository.findByTitleAndNickname(title, nickname);
    }

    @Override
    public BoardDto post(BoardDto boardDto, String email) {

        // email로 유저 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user email."));

        // User 정보 매핑
        Board board = boardDto.toEntity(user);
        board.setCreatedDate(java.time.LocalDateTime.now());

        // BoardFile 엔티티 생성 및 값 세팅
        if (boardDto.getBoardFiles() != null && !boardDto.getBoardFiles().isEmpty()) {
            List<BoardFile> boardFiles = boardDto.getBoardFiles().stream()
                    .map(fileDto -> BoardFile.builder()
                            .board(board)
                            .fileName(fileDto.getFileName())
                            .filePath(fileDto.getFilePath())
                            .fileType(fileDto.getFileType())
                            .fileSize(fileDto.getFileSize())
                            .build())
                    .toList();

            board.setBoardFiles(boardFiles);
        }

        return boardRepository.save(board).toDto();
    }

    @Override
    public BoardDto updateBoard(Long boardId, BoardDto boardDto) {

        // 기존 게시글 가져오기
        Board existingBoard = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("Board with id " + boardId + " not found")
        );

        // 제목, 내용 업데이트
        existingBoard.setTitle(boardDto.getTitle() != null ? boardDto.getTitle() : existingBoard.getTitle());
        existingBoard.setContent(boardDto.getContent() != null ? boardDto.getContent() : existingBoard.getContent());

        // 기존 파일 리스트 가져오기
        List<BoardFile> existingFiles = existingBoard.getBoardFiles();

        // 삭제할 파일 처리 (요청 데이터에 제거할 ID를 받음)
        if (boardDto.getDeleteFileIds() != null && !boardDto.getDeleteFileIds().isEmpty()) {
            List<Long> deleteFileIds = boardDto.getDeleteFileIds();

            // 삭제하려는 파일들이 현재 게시글에 속해 있는지 확인
            List<BoardFile> filesToDelete = boardFileRepository.findAllById(deleteFileIds)
                    .stream()
                    .filter(file -> file.getBoard().getBoardId().equals(boardId))  // 종속된 파일만 필터링
                    .toList();

            // 요청한 파일 중 게시글에 속하지 않는 파일이 있다면 예외 발생
            if (filesToDelete.size() != deleteFileIds.size()) {
                throw new IllegalStateException("Some files do not belong to the current board!");
            }

            // 파일 삭제
            existingBoard.getBoardFiles().removeAll(filesToDelete);
        }

        // 추가할 파일 처리
        if(boardDto.getBoardFiles() != null && !boardDto.getBoardFiles().isEmpty()){

            // Dto에서 BoardFile 엔티티로 새 파일 리스트 변환
            List<BoardFile> newFiles = boardDto.getBoardFiles().stream()
                    .map(fileDto -> BoardFile.fromDto(existingBoard, fileDto)) // DTO -> 엔티티 변환
                    .toList();

            existingFiles.addAll(newFiles);
        }

        // 변경된 게시글 저장
        existingBoard.setBoardFiles(existingFiles);
        existingBoard.setModifiedDate(java.time.LocalDateTime.now());

        // 저장된 게시글 Dto 반환
        return boardRepository.save(existingBoard).toDto();
    }

    @Override
    public BoardDto getBoardById(Long boardId) {

        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("Board with id " + boardId + " not found")
        );

        // 조휘수 증가
        increaseViewCount(board);

        return board.toDto();
    }

    @Transactional
    public void increaseViewCount(Board board) {
        board.setViewCount(board.getViewCount() + 1);
        boardRepository.save(board);
    }
}
