package com.bit.myboardapp.repository;

import com.bit.myboardapp.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query( "SELECT b From Board b " +
            "JOIN b.user u " +
            "WHERE ((:title IS NOT NULL AND :title != '' AND b.title LIKE CONCAT('%', :title, '%')) " +
            "OR :title IS NULL) " +
            "AND (:nickname IS NULL OR u.nickname = :nickname)")
    List<Board> findByTitleAndNickname(@Param("title") String title, @Param("nickname") String nickname);

    @Query( "SELECT b FROM Board b " +
            "LEFT JOIN FETCH b.comments c " +
            "LEFT JOIN FETCH c.user " +
            "WHERE b.boardId = :boardId")
    Optional<Board> findByIdWithComments(@Param("boardId") Long boardId);
}
