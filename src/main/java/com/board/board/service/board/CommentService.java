package com.board.board.service.board;


import com.board.board.domain.Board;
import com.board.board.domain.Comment;
import com.board.board.domain.User;
import com.board.board.dto.CommentDto;
import com.board.board.repository.BoardRepository;
import com.board.board.repository.CommentRepository;
import com.board.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /* CREATE */
    @Transactional
    public Long commentSave(Long userId, Long boardId, CommentDto.Request commentDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("유저를 찾을수 없습니다."));
        Board board = boardRepository.findById(boardId).orElseThrow(() ->
            new IllegalArgumentException("댓글 작성 실패 : 해당 게시글이 존재하지 않습니다." + boardId));

        commentDto.setUser(user);
        commentDto.setBoard(board);
        commentRepository.save(commentDto.toEntity());

        return commentDto.getId();
    }

    /* CREATE */
    @Transactional
    public Long recommentSave(Long userId, Long boardId, Long parentId ,CommentDto.Request commentDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("유저를 찾을수 없습니다."));
        Board board = boardRepository.findById(boardId).orElseThrow(() ->
                new IllegalArgumentException("댓글 작성 실패 : 해당 게시글이 존재하지 않습니다." + boardId));
        commentRepository.findById(parentId).ifPresentOrElse(parent -> commentDto.setParent(parent), ()-> commentDto.setParent(null));

        commentDto.setUser(user);
        commentDto.setBoard(board);

        commentRepository.save(commentDto.toEntity());
        return commentDto.getId();

    }

    /* UPDATE */
    @Transactional
    public void commentUpdate(Long id, CommentDto.Request commentDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("해당 댓글이 존재하지 않습니다." + id));
        comment.update(commentDto.getComment());
    }

    /* DELETE */
    @Transactional
    public void commentDelete(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 존재하지 않습니다." + id));

        /* (부모댓글)답글이 존재하는 상태면 */
        if(!comment.getChildList().isEmpty()) {
            comment.remove();
        }else {
            /* 대댓글 삭제 */
            commentRepository.delete(comment);
            /* 마지막 댓글 이면서 부모 댓글이 삭제되었는지 체크 -> 부모 댓글 까지 삭제 */
            if(comment.getParent() != null && comment.getParent().getChildList().size() == 1 && comment.getParent().isRemoved()) {
                commentRepository.delete(comment.getParent());
            }
        }
    }

    /* 댓글 계층 정렬 */
    public List<CommentDto.Response> convertNestedStructure(List<CommentDto.Response> comments) {
        List<CommentDto.Response> result = new ArrayList<>();
        Map<Long, CommentDto.Response> map = new HashMap<>();

        comments.stream().forEach(comment -> {
            map.put(comment.getId(), comment);

            /* 부모 댓글 존재 */
            if(comment.getParent() != null) {
                map.get(comment.getParent().getId()).getChildList().add(comment);
            }

            else result.add(comment);
        });
        return result;
    }
}
