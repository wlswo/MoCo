package com.board.board.repository;

import com.board.board.dto.BoardListVo;
import com.board.board.dto.QBoardListVo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static com.board.board.domain.QBoard.board;
import static com.board.board.domain.QUser.user;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryCustom {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    /* 모집중인 게시글 리스트 가져오기 */
    public List<BoardListVo> getBoardList(Pageable pageable) {
        return  queryFactory
                .select(new QBoardListVo(board.id, board.createdDate, board.title, board.writer, board.content, user.id, board.view, board.thumbnail, board.subcontent, board.likecnt, board.commentcnt, user.picture, board.hashTag, board.isfull))
                .from(board)
                .leftJoin(user)
                .on(board.user.id.eq(user.id))
                .where(board.isfull.eq(false))
                .orderBy(board.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    /* 모든 게시글 리스트 가져오기 */
    public List<BoardListVo> getAllBoardList(Pageable pageable) {
        return  queryFactory
                .select(new QBoardListVo(board.id, board.createdDate, board.title, board.writer, board.content, user.id, board.view, board.thumbnail, board.subcontent, board.likecnt, board.commentcnt, user.picture, board.hashTag, board.isfull))
                .from(board)
                .leftJoin(user)
                .on(board.user.id.eq(user.id))
                .orderBy(board.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    /* 모집중인 게시글 검색 */
    public List<BoardListVo> getSearchBoard(Pageable pageable, String keyword) {
        return  queryFactory
                .select(new QBoardListVo(board.id, board.createdDate, board.title, board.writer, board.content, user.id, board.view, board.thumbnail, board.subcontent, board.likecnt, board.commentcnt, user.picture, board.hashTag, board.isfull))
                .from(board)
                .leftJoin(user)
                .on(board.user.id.eq(user.id))
                .orderBy(board.createdDate.desc())
                .where(board.isfull.eq(false), board.title.contains(keyword))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    /* 내가 쓴글 가져오기 */
    public List<BoardListVo> getMyBoardList(Pageable pageable, Long userId) {
        return  queryFactory
                .select(new QBoardListVo(board.id, board.createdDate, board.title, board.writer, board.content, user.id, board.view, board.thumbnail, board.subcontent, board.likecnt, board.commentcnt, user.picture, board.hashTag, board.isfull))
                .from(board)
                .leftJoin(user)
                .on(board.user.id.eq(user.id))
                .orderBy(board.createdDate.desc())
                .where(board.user.id.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    /* 댓글 개수 +1 */
    @Transactional
    public void updateCommentCountPlus(Long boardId) {
        JPAUpdateClause updateClause = new JPAUpdateClause(em, board);
        updateClause.where(board.id.eq(boardId)).set(board.commentcnt, board.commentcnt.add(1)).execute();
    }

    /* 댓글 개수 -1 */
    @Transactional
    public void updateCommentCountMinus(Long boardId) {
        JPAUpdateClause updateClause = new JPAUpdateClause(em, board);
        updateClause.where(board.id.eq(boardId)).set(board.commentcnt, board.commentcnt.subtract(1)).execute();
    }

    /* 좋아요 개수 +1 */
    @Transactional
    public void updateLikeCountPlus(Long boardId) {
        JPAUpdateClause updateClause = new JPAUpdateClause(em, board);
        updateClause.where(board.id.eq(boardId)).set(board.likecnt, board.likecnt.add(1)).execute();
    }

    /* 댓글 개수 -1 */
    @Transactional
    public void updateLikeCountMinus(Long boardId) {
        JPAUpdateClause updateClause = new JPAUpdateClause(em, board);
        updateClause.where(board.id.eq(boardId)).set(board.likecnt, board.likecnt.subtract(1)).execute();
    }
}
