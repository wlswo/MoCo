package com.board.board.service.board;

import com.board.board.domain.Board;
import com.board.board.domain.Like;
import com.board.board.domain.User;
import com.board.board.dto.LikeDto;
import com.board.board.repository.BoardRepository;
import com.board.board.repository.LikeRepository;
import com.board.board.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class LikeService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final LikeRepository likeRepository;

    /* CREATE */
    @Transactional
    public Long likeSave(String name,Long boardId) {
        User user = userRepository.findByName(name);
        Board board = boardRepository.findById(boardId).orElseThrow(() ->
                new IllegalArgumentException("좋아요 실패 : 해당 게시글이 존재하지 않습니다." + boardId));

        LikeDto.Request likeDto = new LikeDto.Request();
        likeDto.setUser(user);
        likeDto.setBoard(board);
        likeRepository.save(likeDto.toEntity());

        return likeDto.getId();
    }

    /* READ */
    @Transactional(readOnly = true)
    public boolean findLike(Long userId, Long boardId) {
        return likeRepository.existsByUser_IdAndBoard_Id(userId, boardId);
    }

    /* READ COUNT */
    @Transactional(readOnly = true)
    public Long findLikeCount(Long boardId) {
        return likeRepository.countByBoard_Id(boardId);
    }

    /* DELETE */
    @Transactional
    public Long deleteLike(String name, Long boardId) {
        User user = userRepository.findByName(name);
        Like like = likeRepository.findByUser_IdAndBoard_Id(user.getId(), boardId);
        likeRepository.delete(like);
        return like.getId();
    }


}
