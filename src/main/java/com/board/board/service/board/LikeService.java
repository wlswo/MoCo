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

import javax.transaction.Transactional;

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
    @Transactional
    public boolean findLike(String name, Long boardId) {
        User user = userRepository.findByName(name);
        return likeRepository.existsByUser_IdAndBoard_Id(user.getId(), boardId);
    }

    /* DELETE */
    public Long deleteLike(String name, Long boardId) {
        User user = userRepository.findByName(name);
        Like like = likeRepository.findByUser_IdAndBoard_Id(user.getId(), boardId);
        likeRepository.delete(like);
        return like.getId();
    }
}
