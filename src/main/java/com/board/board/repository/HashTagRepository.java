package com.board.board.repository;

import com.board.board.domain.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface HashTagRepository extends JpaRepository<HashTag,Long> {
    /* 특정 게시글의 해시태그 가져오기 */
    Set<HashTag> findAllByBoardId(Long board_id);
}
