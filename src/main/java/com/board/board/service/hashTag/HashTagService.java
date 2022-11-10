package com.board.board.service.hashTag;

import com.board.board.domain.Board;
import com.board.board.domain.HashTag;
import com.board.board.dto.HashTagDto;
import com.board.board.repository.BoardRepository;
import com.board.board.repository.HashTagRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Service
public class HashTagService {
    private final HashTagRepository hashTagRepository;
    private final BoardRepository boardRepository;
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

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

    /* DELETE */
    @Transactional
    public void DeleteAll(Long board_id, List<String> hashTag) {
        hashTagRepository.deleteByBoardIdAndTagcontentIn(board_id,hashTag);
    }

    /* READ */
    @Transactional(readOnly = true)
    public HashSet<HashTag> getTags(Long board_id) {
        return hashTagRepository.findAllByBoardId(board_id);
    }

    /* PUT */
    public void updateHashTag(String tags, Long boardId) {
        /* 해시태그 수정 */
        if(!tags.isEmpty()) {
            HashSet<String> hashTagList = new HashSet<>();
            try{
                JSONParser parser = new JSONParser();
                JSONArray json = (JSONArray) parser.parse(tags);
                json.forEach(item -> {
                    JSONObject jsonObject = (JSONObject) JSONValue.parse(item.toString());
                    hashTagList.add(jsonObject.get("value").toString());
                });

                /* 기존 해시태그와 비교 */
                HashSet<HashTag> OriginHashTags =  getTags(boardId);
                HashSet<String> OriginHashTagsContent = new HashSet<>();
                OriginHashTags.forEach(item -> {
                    OriginHashTagsContent.add(item.getTagcontent());
                });

                /* 추가된 해시태그 */
                HashSet<String> AddTags = new HashSet<>(hashTagList);  // s1으로 substract 생성
                AddTags.removeAll(OriginHashTagsContent);              // 차집합 수행
                if(!AddTags.isEmpty()) {
                    List<HashTagDto.Request> hashTagDtoList = new ArrayList<>();
                    AddTags.forEach(item -> {
                        HashTagDto.Request hashTagDto = new HashTagDto.Request();
                        hashTagDto.setTagcontent(item);
                        hashTagDtoList.add(hashTagDto);
                    });
                    SaveAll(boardId,hashTagDtoList);
                }

                /* 삭제된 해시태그 */
                HashSet<String> SubTags = new HashSet<>(OriginHashTagsContent);  // s1으로 substract 생성
                SubTags.removeAll(hashTagList);                                  // 차집합 수행
                List<String> setTolist = new ArrayList<>(SubTags);

                if(!SubTags.isEmpty()) {
                    DeleteAll(boardId,setTolist);
                }


            }catch (ParseException e) {
                log.info(e.getMessage());
            }
        }
    }

    /* 해시태그 내용만 Filter */
    public StringBuilder hashTagFilter(Long boardId) {
        Set<HashTag> hashTags = getTags(boardId);
        Iterator<HashTag> hashTagContents = hashTags.iterator();
        StringBuilder sb = new StringBuilder();

        while (hashTagContents.hasNext()) {
            sb.append(hashTagContents.next().getTagcontent()).append(",");
        }
        return sb;
    }

    /* 해시태그 저장 */
    public void hashTagSave(String tags,Long boardId) {
        /* 해시태그 저장 */
        if(!tags.isEmpty()) {
            List<HashTagDto.Request> hashTagDtoList = new ArrayList<>();
            try{
                JSONParser parser = new JSONParser();
                JSONArray json = (JSONArray) parser.parse(tags);
                json.forEach(item -> {
                    JSONObject jsonObject = (JSONObject) JSONValue.parse(item.toString());
                    HashTagDto.Request hashTagDto = new HashTagDto.Request();
                    hashTagDto.setTagcontent(jsonObject.get("value").toString());
                    hashTagDtoList.add(hashTagDto);
                });
                SaveAll(boardId,hashTagDtoList);
            }catch (ParseException e) {
                log.info(e.getMessage());
            }
        }
    }

}
