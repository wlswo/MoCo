package com.board.board.QuerydslTest;

import com.board.board.domain.Board;
import com.board.board.domain.QBoard;
import com.board.board.dto.BoardListVo;
import com.board.board.dto.QBoardListVo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import static com.board.board.domain.QBoard.board;
import static com.board.board.domain.QUser.user;


@DisplayName("queryDsl 테스트 코드")
@SpringBootTest
public class QuerydslTest {

    @Autowired private EntityManager em;
    @Autowired private JPAQueryFactory queryFactory;

    private final int PAGE_POST_COUNT = 9; // 한 페이지에 존재하는 게시글 수
    private final int PageNum = 1; // 페이지
    private final Pageable pageable = PageRequest.of(PageNum - 1, PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC, "created_date"));

    @BeforeEach
    public void 엔티티매니저_주입() {
        queryFactory = new JPAQueryFactory(em);
    }

    @DisplayName("모든 게시글 가져오기")
    @Test
    public void 모든_게시글_가져오기(){
        List<Board> boards = queryFactory.selectFrom(board)
                        .fetch();

        boards.stream().forEach(board -> System.out.println(board.getCommentcnt()));
        System.out.println(boards.size());
    }

    @DisplayName("페이징 구현")
    @Test
    public void 페이징_구현하기_테스트() {
        List<BoardListVo> results  = queryFactory
                .select(new QBoardListVo(board.id, board.createdDate, board.title, board.writer, board.content, user.id, board.view, board.thumbnail, board.subcontent, board.likecnt, board.commentcnt, user.picture, board.hashTag, board.isfull))
                .from(board)
                .leftJoin(user)
                .on(board.user.id.eq(user.id))
                .orderBy(board.createdDate.desc())
                .offset(pageable.getOffset()) //N번 부터 시작
                .limit(pageable.getPageSize())
                .fetch();

        System.out.println(results.size());
    }

    @DisplayName("검색 + 페이징")
    @Test
    public void 검색한_게시글_페이징_테스트() {
        List<BoardListVo> results = queryFactory
                .select(new QBoardListVo(board.id, board.createdDate, board.title, board.writer, board.content, user.id, board.view, board.thumbnail, board.subcontent, board.likecnt, board.commentcnt, user.picture, board.hashTag, board.isfull))
                .from(board)
                .leftJoin(user)
                .on(board.user.id.eq(user.id))
                .orderBy(board.createdDate.desc())
                .where(board.isfull.eq(false), board.title.contains("a"))
                .offset(pageable.getOffset()) //N번 부터 시작
                .limit(pageable.getPageSize())
                .fetch();

        System.out.println(results.size());
    }

    @DisplayName("댓글 개수, 좋아요 개수 테스트")
    @Test @Transactional
    public void 업데이트_테스트() {
        JPAUpdateClause updateClause = new JPAUpdateClause(em, board);
        updateClause.where(board.id.eq(1L)).set(board.commentcnt, board.commentcnt.add(1)).execute();
        int count = queryFactory.selectFrom(board).where(board.id.eq(1L)).fetchOne().getCommentcnt();
        Assert.assertEquals(1,count);
    }
}
