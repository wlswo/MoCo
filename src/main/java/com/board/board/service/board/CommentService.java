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
    public Long commentSave(String name, Long id, CommentDto.Request commentDto) {
        User user = userRepository.findByName(name);
        Board board = boardRepository.findById(id).orElseThrow(() ->
            new IllegalArgumentException("댓글 작성 실패 : 해당 게시글이 존재하지 않습니다." + id));

        commentDto.setUser(user);
        commentDto.setBoard(board);
        commentRepository.save(commentDto.toEntity());

        return commentDto.getId();
    }

    /* CREATE */
    @Transactional
    public Long recommentSave(String name, Long id, Long parentId ,CommentDto.Request commentDto) {
        User user = userRepository.findByName(name);
        Board board = boardRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("댓글 작성 실패 : 해당 게시글이 존재하지 않습니다." + id));
        Comment parent = commentRepository.findById(parentId).orElse(null);

        commentDto.setUser(user);
        commentDto.setBoard(board);
        commentDto.setParent(parent);

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
    public void commentDelete(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 존재하지 않습니다." + id));
        commentRepository.delete(comment);
    }

    public void remove(Long id) throws Exception {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new Exception("댓글이 없습니다."));
        comment.remove();
        List<Comment> removableCommentList = comment.findRemovableList();
        removableCommentList.forEach(removableComment -> commentRepository.delete(removableComment));
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
