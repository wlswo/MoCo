package com.board.board.repository;

import com.board.board.domain.Board;
import com.board.board.dto.BoardListVo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/* JpaRepository<Entity 클래스, PK 타입> */
public interface BoardRepository extends JpaRepository<Board, Long> {

    /* 전체 게시글 리스트 가져오기 */
    @Query(value = "select * from board a " +
            "LEFT JOIN " +
            "(select board_id , count(*) as like_count from likes group by board_id) b" +
            " on (a.id = b.board_id)" +
            " LEFT JOIN " +
            " (select board_id as comment_b_id , count(*) as comment_count from comments group by board_id) c" +
            " on (a.id = c.comment_b_id) LEFT JOIN " +
            " (select id as u_id, u.picture  from user u ) d" +
            " on (a.user_id = d.u_id) " +
            " LEFT JOIN" +
            " (SELECT board_id as tag_board_id, GROUP_CONCAT(tagcontent SEPARATOR ' #') AS hashTag" +
            " FROM hashtags group by board_id ) e" +
            " on (a.id = e.tag_board_id)", nativeQuery = true)
    List<BoardListVo> findBoardList(Pageable pageable);

    /* 모집중인 게시글 리스트 가져오기 */
    @Query(value = "select * from board a " +
            "LEFT JOIN " +
            "(select board_id , count(*) as like_count from likes group by board_id) b" +
            " on (a.id = b.board_id)" +
            " LEFT JOIN " +
            " (select board_id as comment_b_id , count(*) as comment_count from comments group by board_id) c" +
            " on (a.id = c.comment_b_id) LEFT JOIN " +
            " (select id as u_id, u.picture  from user u ) d" +
            " on (a.user_id = d.u_id) " +
            " LEFT JOIN" +
            " (SELECT board_id as tag_board_id, GROUP_CONCAT(tagcontent SEPARATOR ' #') AS hashTag" +
            " FROM hashtags group by board_id ) e" +
            " on (a.id = e.tag_board_id) where a.isfull = 0", nativeQuery = true)
    List<BoardListVo> findBoardListOnRecruit(Pageable pageable);


    /* 게시글 Search (모집중인)  */
    @Query(value = "select * from board a " +
            "LEFT JOIN " +
            "(select board_id , count(*) as like_count from likes group by board_id) b" +
            " on (a.id = b.board_id)" +
            " LEFT JOIN " +
            " (select board_id as comment_b_id , count(*) as comment_count from comments group by board_id) c" +
            " on (a.id = c.comment_b_id) LEFT JOIN " +
            " (select id as u_id, u.picture  from user u ) d" +
            " on (a.user_id = d.u_id) " +
            " LEFT JOIN" +
            " (SELECT board_id as tag_board_id, GROUP_CONCAT(tagcontent SEPARATOR ' #') AS hashTag" +
            " FROM hashtags group by board_id ) e" +
            " on (a.id = e.tag_board_id) WHERE a.title LIKE %:keyword% and a.isfull =0", nativeQuery = true)
    List<BoardListVo> findByTitleContaining(Pageable pageable, @Param("keyword") String keyword);

    /* 전체 게시글 리스트 가져오기 */
    @Query(value = "select * from board a " +
            "LEFT JOIN " +
            "(select board_id , count(*) as like_count from likes group by board_id) b" +
            " on (a.id = b.board_id)" +
            " LEFT JOIN " +
            " (select board_id as comment_b_id , count(*) as comment_count from comments group by board_id) c" +
            " on (a.id = c.comment_b_id) LEFT JOIN " +
            " (select id as u_id, u.picture  from user u ) d" +
            " on (a.user_id = d.u_id) " +
            " LEFT JOIN" +
            " (SELECT board_id as tag_board_id, GROUP_CONCAT(tagcontent SEPARATOR ' #') AS hashTag" +
            " FROM hashtags group by board_id ) e" +
            " on (a.id = e.tag_board_id) WHERE a.user_id = :userId", nativeQuery = true)
    List<BoardListVo> findMyBoardList(Pageable pageable, @Param("userId") Long userId);

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
