package com.board.board.service.board;


import com.board.board.domain.Board;
import com.board.board.domain.User;
import com.board.board.dto.RecruitDto;
import com.board.board.repository.BoardRepository;
import com.board.board.repository.RecruitRepositoey;
import com.board.board.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class RecruitService {
    private RecruitRepositoey recruitRepositoey;
    private BoardRepository boardRepository;
    private UserRepository userRepository;

    /* 모집참가 */
    @Transactional
    public Long Join(Long boardid, Long userId, RecruitDto.Request recruitDto) {
        Board board = boardRepository.findById(boardid).orElseThrow(() -> new IllegalArgumentException("게시글을 찾을수 없습니다."));
        User user  = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("유저를 찾을수 없습니다."));;

        recruitDto.setBoard(board);
        recruitDto.setUser(user);
        recruitRepositoey.save(recruitDto.toEntity());
        return recruitDto.getId();
    }
    /* 참가여부 조회 */
    @Transactional(readOnly = true)
    public Boolean isDuplicate(Long boardId, Long userId) {
        return recruitRepositoey.existsByBoardIdAndUserId(boardId,userId);
    }

    /* 해당게시글 참가 인원수 가져오기 */
    @Transactional(readOnly = true)
    public Long countToJoinUsers(Long boardId) {
        return recruitRepositoey.countByBoardId(boardId);
    }

    /* 참가 취소 */
    @Transactional
    public int joinCancel(Long boardId, Long userId) {
        return recruitRepositoey.joinUserCancel(boardId,userId);
    }

}
