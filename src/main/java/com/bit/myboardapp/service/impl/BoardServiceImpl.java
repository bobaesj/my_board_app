package com.bit.myboardapp.service.impl;

import com.bit.myboardapp.dto.BoardDto;
import com.bit.myboardapp.dto.CommentDto;
import com.bit.myboardapp.entity.*;
import com.bit.myboardapp.repository.*;
import com.bit.myboardapp.service.BoardService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;
    private final CommentRepository commentRepository;
    private final NotificationRepository notificationRepository;

    @Override
    public List<BoardDto> findBoards(String title, String nickname) {

        return boardRepository.findByTitleAndNickname(title, nickname).stream().map(Board::toDto).toList();
    }

    @Override
    public BoardDto post(BoardDto boardDto, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user email."));

        Board board = boardDto.toEntity(user);
        board.setCreatedDate(java.time.LocalDateTime.now());
        board.setViewCount(0L);

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
    public BoardDto updateBoard(Long boardId, BoardDto boardDto, String email) {

        Board existingBoard = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("Board with id " + boardId + " not found")
        );

        if(!existingBoard.getUser().getEmail().equals(email)){
            throw new SecurityException("You do not have permission to update this board.");
        }

        existingBoard.setTitle(boardDto.getTitle() != null ? boardDto.getTitle() : existingBoard.getTitle());
        existingBoard.setContent(boardDto.getContent() != null ? boardDto.getContent() : existingBoard.getContent());

        List<BoardFile> existingFiles = existingBoard.getBoardFiles();

        // 삭제할 파일 처리 (요청 데이터에 제거할 ID를 받음)
        if (boardDto.getDeleteFileIds() != null && !boardDto.getDeleteFileIds().isEmpty()) {
            List<Long> deleteFileIds = boardDto.getDeleteFileIds();

            // 삭제하려는 파일들이 현재 게시글에 속해 있는지 확인
            List<BoardFile> filesToDelete = boardFileRepository.findAllById(deleteFileIds)
                    .stream()
                    .filter(file -> file.getBoard().getBoardId().equals(boardId))  // 종속된 파일만 필터링
                    .toList();

            if (filesToDelete.size() != deleteFileIds.size()) {
                throw new IllegalStateException("Some files do not belong to the current board!");
            }

            // 파일 삭제
            existingBoard.getBoardFiles().removeAll(filesToDelete);
        }

        // 추가할 파일 처리
        if(boardDto.getBoardFiles() != null && !boardDto.getBoardFiles().isEmpty()){

            List<BoardFile> newFiles = boardDto.getBoardFiles().stream()
                    .map(fileDto -> BoardFile.fromDto(existingBoard, fileDto)) // DTO -> 엔티티 변환
                    .toList();

            existingFiles.addAll(newFiles);
        }

        existingBoard.setBoardFiles(existingFiles);
        existingBoard.setModifiedDate(java.time.LocalDateTime.now());

        return boardRepository.save(existingBoard).toDto();
    }

    @Override
    public void deleteBoard(Long boardId, String email) {

        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("Board with id " + boardId + " not found")
        );

        if(board.getUser().getEmail().equals(email)){
            throw new SecurityException("You do not have permission to delete this board.");
        }

        boardRepository.delete(board);
    }

    @Override
    public BoardDto getBoardWithComments(Long boardId) {

        Board board = boardRepository.findByIdWithComments(boardId).orElseThrow(
                () -> new IllegalArgumentException("Board with id " + boardId + " not found")
        );

        increaseViewCount(board);
        BoardDto boardDto = board.toDto();

        List<CommentDto> commentDtoList = board.getComments().stream()
                .map(comment -> {
                    CommentDto commentDto = new CommentDto(comment);
                    commentDto.setNickname(comment.getUser().getNickname());
                    return commentDto;
                }).toList();

        boardDto.setComments(commentDtoList);

        return boardDto;
    }

    @Override
    public CommentDto writeComment(Long boardId, CommentDto commentDto, String email) {

        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("Board with id " + boardId + " not found")
        );

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("User with email " + email + " not found")
        );

        Comment comment = Comment.builder()
                .content(commentDto.getContent())
                .createdDate(LocalDateTime.now())
                .modifiedDate(commentDto.getModifiedDate())
                .board(board)
                .user(user).build();

        commentRepository.save(comment);

        if(!board.getUser().getEmail().equals(email)){
            Notification notification = Notification.builder()
                    .user(board.getUser())
                    .sender(user)
                    .content("댓글 알람!!")
                    .relatedEntityId(comment.getCommentId())
                    .createdDate(LocalDateTime.now())
                    .build();

            notificationRepository.save(notification);
        }

        CommentDto resultCommentDto = new CommentDto(comment);
        resultCommentDto.setNickname(user.getNickname());
        return resultCommentDto;
    }

    @Override
    public CommentDto updateComment(Long boardId, CommentDto commentDto, String email) {

        if(boardRepository.findById(boardId).isEmpty()){
            throw new IllegalArgumentException("Board with id " + boardId + " not found");
        }

        Comment comment = commentRepository.findById(commentDto.getCommentId()).orElseThrow(
                () -> new IllegalArgumentException("Comment with id " + commentDto.getCommentId() + " not found")
        );

        if(!comment.getUser().getEmail().equals(email)){
            throw new SecurityException("You do not have permission to update this comment.");
        }

        String existingContent = comment.getContent();
        String newContent = commentDto.getContent();

        if (newContent == null || newContent.trim().isEmpty()) {
            throw new IllegalArgumentException("Comment content cannot be null or empty.");
        }

        if (existingContent.equals(newContent)) {
            throw new IllegalArgumentException("New content is the same as the existing content. Update skipped.");
        }

        comment.setContent(newContent);
        comment.setModifiedDate(LocalDateTime.now());
        Comment savedComment = commentRepository.save(comment);

        return new CommentDto(savedComment);
    }
    
    @Override
    public void deleteComment(Long boardId, Long commentId, String email) {

        if(boardRepository.findById(boardId).isEmpty()){
            throw new IllegalArgumentException("Board with id " + boardId + " not found");
        }

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("Comment with id " + commentId + " not found")
        );

        if(!comment.getUser().getEmail().equals(email)){
            throw new SecurityException("You do not have permission to delete this comment.");
        }

        commentRepository.delete(comment);
    }

    @Transactional
    public void increaseViewCount(Board board) {
        board.setViewCount(board.getViewCount() + 1);
        boardRepository.save(board);
    }
}
