package com.board.board.Controller;

import com.board.board.domain.Board;
import com.board.board.domain.HashTag;
import com.board.board.repository.BoardRepository;
import com.board.board.repository.HashTagRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@DisplayName("SAVE 와 SAVEALL 비교테스트")
public class BoardControllerTest {
    @Autowired
    HashTagRepository hashTagRepository;
    @Autowired
    BoardRepository boardRepository;
    Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    /* 해시태그 만개 테스트 */

    @Transactional
    @Test @Rollback(value = true)
    void SAVE_TEST() {
        Board board = boardRepository.findById(Long.valueOf(108)).orElseThrow();
        long start = System.currentTimeMillis();
        int count = 10000;
        while (count-- > 0) {
            HashTag hashTag = HashTag.builder().tagcontent("test").board(board).build();
            hashTagRepository.save(hashTag);
        }
        log.info("elapsed time : "  + (System.currentTimeMillis() - start) + "ms.");
    }
    @Transactional
    @Test @Rollback(value = true)
    void SAVEALL_TEST() {
        Board board = boardRepository.findById(Long.valueOf(108)).orElseThrow();
        long start = System.currentTimeMillis();
        int count = 10000;
        List<HashTag> hashTagList = new ArrayList<>();
        while (count-- > 0) {
            HashTag hashTag = HashTag.builder().tagcontent("test").board(board).build();
            hashTagList.add(hashTag);
        }
        hashTagRepository.saveAll(hashTagList);
        log.info("elapsed time : "  + (System.currentTimeMillis() - start) + "ms.");

    }
}
