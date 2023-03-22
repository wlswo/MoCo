package com.board.board.repository;

import com.board.board.domain.Board;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/* JpaRepository<Entity 클래스, PK 타입> */
public interface BoardRepository extends JpaRepository<Board, Long> {

    /* 전체 게시글 리스트 가져오기 - 내가 쓴글 */
    @Query(value = "select * from board a WHERE a.user_id = :userId", nativeQuery = true)
    List<Board> findMyBoardList(Pageable pageable, @Param("userId") Long userId);

    /* 게시글 상세보기 [댓글로 인한 FetchJoin] */
    @Query("select b from Board b left join fetch b.comments c where b.id = :boardId")
    Board findByIdWithFetchJoin(@Param("boardId") Long boardId);


    /* 게시글 작성자 변경 */
    @Modifying
    @Query("UPDATE Board set writer = :writer  WHERE user.id = :userId")
    void updateWriter(@Param("writer") String name, @Param("userId") Long userId);


    /* 조회수 */
    @Modifying
    @Query("update Board b set b.view = b.view + 1 where b.id = :id")
    int updateView(@Param("id") Long id);

}
