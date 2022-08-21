package com.board.board.repository;

import com.board.board.domain.Like;
import com.board.board.dto.LikeDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like,Long> {
    /* likeGet - exist */
    boolean existsByUser_IdAndBoard_Id(Long user_id, Long board_id);

    /* likeGet - find */
    Like findByUser_IdAndBoard_Id(Long user_id,Long board_id);

    /* likeSize - count */
    Long countByBoard_Id(Long board_id);
}
