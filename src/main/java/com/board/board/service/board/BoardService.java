package com.board.board.service.board;

import com.board.board.domain.Board;
import com.board.board.domain.User;
import com.board.board.dto.BoardDto;
import com.board.board.dto.BoardListVo;
import com.board.board.repository.BoardRepository;
import com.board.board.repository.LikeRepository;
import com.board.board.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@AllArgsConstructor
@Service
public class BoardService {

    private UserRepository  userRepository;
    private BoardRepository boardRepository;

    //private static final int BLOCK_PAGE_NUM_COUNT = 5; // 블럭에 존재하는 페이지 번호 수
    private static final int PAGE_POST_COUNT = 9; // 한 페이지에 존재하는 게시글 수

    /* PAGEABLE */
    @Transactional
    public List<BoardListVo> getBoardlist(Integer pageNum) {
        PageRequest pageRequest = PageRequest.of(pageNum - 1, PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC, "created_date"));
        List<BoardListVo> boardList = boardRepository.findBoardList(pageRequest);

        return boardList;
    }

    /* READ */
    @Transactional
    public List<BoardListVo> searchPosts(Integer pageNum,String keyword) {
        PageRequest pageRequest = PageRequest.of(pageNum - 1, PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC, "created_date"));
        List<BoardListVo> boardList = boardRepository.findByTitleContaining(pageRequest,keyword);

        return boardList;
    }

    /* READ */
    @Transactional(readOnly = true)
    public BoardDto.Response getPost(Long id) {
        Optional<Board> boardWrapper = boardRepository.findById(id);
        Board board = boardWrapper.get();
        return new BoardDto.Response(board);
    }
    /* READ */
    @Transactional(readOnly = true)
    public BoardDto.Response findById(Long id) {
        Board board = boardRepository.findByIdWithFetchJoin(id);
        return new BoardDto.Response(board);
    }

    /* CREATE */
    @Transactional
    public Long savePost(String name, BoardDto.Request boardDto) {
        User user = userRepository.findByName(name);
        boardDto.setUser(user);
        return boardRepository.save(boardDto.toEntity()).getId();
    }

    /* UPDATE */
    @Transactional
    public Long updatePost(Long board_id, BoardDto.Request boardDto) {
        Board board = boardRepository.findById(board_id).orElseThrow( () -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        board.update(boardDto.getTitle(), boardDto.getContent(), boardDto.getSubcontent(), boardDto.getThumbnail());
        return board.getId();
    }

    @Transactional
    public boolean updateFull(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(()-> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        return board.close();
    }

    /* DELETE */
    @Transactional
    public void deletePost(Long id) {
        boardRepository.deleteById(id);
    }



    // 페이징
    @Transactional
    public Long getBoardCount() {
        return boardRepository.count();
    }

    public Integer getPageList(Integer curPageNum) {
        //Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];

        // 총 게시글 갯수
        Double postsTotalCount = Double.valueOf(this.getBoardCount());

        // 총 게시글 기준으로 계산한 마지막 페이지 번호 계산 (올림으로 계산)
        Integer totalLastPageNum = (int)(Math.ceil((postsTotalCount/PAGE_POST_COUNT)));

        /*
        // 현재 페이지를 기준으로 블럭의 마지막 페이지 번호 계산
        Integer blockLastPageNum = (totalLastPageNum > curPageNum + BLOCK_PAGE_NUM_COUNT)
                ? curPageNum + BLOCK_PAGE_NUM_COUNT
                : totalLastPageNum;

        // 페이지 시작 번호 조정
        curPageNum = (curPageNum <= 3) ? 1 : curPageNum - 2;

        // 페이지 번호 할당
        for (int val = curPageNum, idx = 0; val <= blockLastPageNum; val++, idx++) {
            pageList[idx] = val;
        }
        */
        //return pageList;
        return totalLastPageNum;
    }

    /* 조회수 */
    @Transactional
    public int updateView(Long id) {
        return boardRepository.updateView(id);
    }

}