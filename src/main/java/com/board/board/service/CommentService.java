package com.board.board.service;


import com.board.board.domain.Board;
import com.board.board.domain.Comment;
import com.board.board.domain.User;
import com.board.board.dto.CommentDto;
import com.board.board.repository.BoardRepository;
import com.board.board.repository.CommentRepository;
import com.board.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

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

}
