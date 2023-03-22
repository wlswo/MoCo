package com.board.board.MethodTest;

import com.board.board.domain.Board;
import com.board.board.dto.BoardDto;
import com.board.board.repository.BoardRepository;
import com.board.board.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@DisplayName("JPA 테스트")
@SpringBootTest
public class BoardTest {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;

    private static final int pageNum = 1;
    private static final int PAGE_POST_COUNT = 9;

    @Test
    public void 게시글_가져오기() {
        PageRequest pageRequest = PageRequest.of(pageNum - 1, PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC, "createdDate"));
        //List<BoardDto.Response> boardList = boardRepository.findAll(pageRequest).stream().map(board -> new BoardDto.Response(board)).collect(Collectors.toList());
        Page<Board> boards = boardRepository.findAll(pageRequest);


    }
}
