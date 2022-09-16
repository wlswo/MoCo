package com.board.board.repository;

import com.board.board.domain.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashSet;
import java.util.List;

public interface HashTagRepository extends JpaRepository<HashTag,Long> {
    /* 특정 게시글의 해시태그 가져오기 */
    HashSet<HashTag> findAllByBoardId(Long board_id);

    /* 해쉬태그 삭제 */
    void deleteByBoardIdAndTagcontentIn(Long board_id, List<String> tagContent);
}
