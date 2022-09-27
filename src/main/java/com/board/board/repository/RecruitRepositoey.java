package com.board.board.repository;

import com.board.board.domain.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecruitRepositoey extends JpaRepository<Recruit,Long> {
    @Query("SELECT count(r)>0 from Recruit r WHERE r.board.id = :boardId AND  r.user.id = :userId")
    boolean existsByBoardIdAndUserId(@Param("boardId") Long boardId, @Param("userId") Long userId);

    @Query("select  count(r) from Recruit  r where r.board.id = :boardId")
    Long countByBoardId(@Param("boardId") Long boardId);

    @Modifying
    @Query("delete from Recruit r where r.board.id = :boardId AND r.user.id = :userId")
    int joinUserCancel(@Param("boardId") Long boardId, @Param("userId") Long userId);

}
