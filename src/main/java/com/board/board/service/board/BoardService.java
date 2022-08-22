package com.board.board.service.board;

import com.board.board.domain.Board;
import com.board.board.domain.User;
import com.board.board.dto.BoardDto;
import com.board.board.repository.BoardRepository;
import com.board.board.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BoardService {

    private UserRepository  userRepository;
    private BoardRepository boardRepository;

    private static final int BLOCK_PAGE_NUM_COUNT = 5; // 블럭에 존재하는 페이지 번호 수
    private static final int PAGE_POST_COUNT = 9; // 한 페이지에 존재하는 게시글 수

    @Transactional
    public List<BoardDto.Response> getBoardlist(Integer pageNum) {
        Page<Board> page = boardRepository.findAll(PageRequest.of(
                pageNum - 1, PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC, "createdDate")));

        List<Board> boardEntities = page.getContent();
        List<BoardDto.Response> boardDtoList = new ArrayList<>();

        for (Board board : boardEntities) {
            boardDtoList.add(new BoardDto.Response(board));
        }

        return boardDtoList;
    }

    @Transactional
    public BoardDto.Response getPost(Long id) {
        Optional<Board> boardWrapper = boardRepository.findById(id);
        Board board = boardWrapper.get();
        return new BoardDto.Response(board);
    }
    @Transactional
    public BoardDto.Response findById(Long id) {
        Optional<Board> boardWrapper = boardRepository.findById(id);
        Board board = boardWrapper.get();
        return new BoardDto.Response(board);
    }

    @Transactional
    public Long savePost(String name, BoardDto.Request boardDto) {
        User user = userRepository.findByName(name);
        boardDto.setUser(user);
        return boardRepository.save(boardDto.toEntity()).getId();
    }

    @Transactional
    public void deletePost(Long id) {
        boardRepository.deleteById(id);
    }

    // 검색 API
    @Transactional
    public List<BoardDto.Response> searchPosts(String keyword) {
        List<Board> boardEntities = boardRepository.findByTitleContaining(keyword);
        List<BoardDto.Response> boardDtoList = new ArrayList<>();

        if (boardEntities.isEmpty()) return boardDtoList;

        for (Board board : boardEntities) {
            boardDtoList.add(new BoardDto.Response(board));
        }

        return boardDtoList;
    }


    // 페이징
    @Transactional
    public Long getBoardCount() {
        return boardRepository.count();
    }

    public Integer getPageList(Integer curPageNum) {
        Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];

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