package com.board.board.repository;

import com.board.board.domain.Board;
import com.board.board.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// JpaRepository<Entity 클래스, PK 타입>
public interface BoardRepository extends JpaRepository<Board, Long> {
    /* 게시글 Search */
    List<Board> findByTitleContaining(String keyword);

    @Modifying
    @Query("update Board b set b.view = b.view + 1 where b.id = :id")
    int updateView(@Param("id") Long id);

    Board findByUser(User user);
}
