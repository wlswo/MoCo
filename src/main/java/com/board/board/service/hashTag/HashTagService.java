package com.board.board.service.hashTag;

import com.board.board.domain.Board;
import com.board.board.domain.HashTag;
import com.board.board.dto.HashTagDto;
import com.board.board.repository.BoardRepository;
import com.board.board.repository.HashTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class HashTagService {
    private final HashTagRepository hashTagRepository;
    private final BoardRepository boardRepository;

    /* CREATE */
    @Transactional
    public void SaveAll(Long board_id, List<HashTagDto.Request> hashTagDto) {
        Board board = boardRepository.findById(board_id).orElseThrow(() ->
                new IllegalArgumentException("해시태그 저장 실패 : 해당 게시글이 존재하지 않습니다."));
        List<HashTag> hashTags = new ArrayList<>();
        for(int i=0; i<hashTagDto.size(); i++) {
            hashTagDto.get(i).setBoard(board);
            hashTags.add(hashTagDto.get(i).toEntity());
        }
        hashTagRepository.saveAll(hashTags);
    }

}