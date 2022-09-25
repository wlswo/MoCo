package com.board.board.controller;


import com.board.board.config.LoginUser;
import com.board.board.config.auth.SessionUser;
import com.board.board.domain.Comment;
import com.board.board.domain.HashTag;
import com.board.board.dto.BoardDto;
import com.board.board.dto.BoardListVo;
import com.board.board.dto.CommentDto;
import com.board.board.dto.HashTagDto;
import com.board.board.service.board.BoardService;
import com.board.board.service.board.CommentService;
import com.board.board.service.board.LikeService;
import com.board.board.service.hashTag.HashTagService;
import com.board.board.service.web3j.TransferTokenService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.io.JsonEOFException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.github.furstenheim.CopyDown;
import lombok.AllArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;

/* 게시판 */
@AllArgsConstructor
@Controller
@RequestMapping("board") //board 경로로 들어오는 경우 , 그후 해당 필드의 Method로 분기될 수 있도록 설정
public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;
    private final LikeService likeService;
    private final HashTagService hashTagService;
    private final TransferTokenService transferTokenService;
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /* ----Board---- */
    /* 게시글 목록
       list 경로로 GET메서드 요청이 들어올 경우 list 메서드와 매핑
       list 경로에 요청 파라미터가 있을 경우 (?page=1), 그에 따른 페이징을 수행 */
    @GetMapping({"","/list"})
    public String list(Model model, @RequestParam(value = "page", defaultValue = "1") Integer pageNum) {
        List<BoardListVo> boardList = boardService.getBoardlist(pageNum);
        Integer pageList = boardService.getPageList(pageNum);

        model.addAttribute("boardList",boardList);
        model.addAttribute("totalPage", pageList);

        return "board/list";
    }
    /* 무한스크롤 AJAX */
    @GetMapping("/listJson")
    public ResponseEntity listJson(Model model, @RequestParam(value = "page", defaultValue = "1") Integer pageNum) {
        List<BoardListVo> boardList = boardService.getBoardlist(pageNum);

        return ResponseEntity.ok(boardList);
    }

    /* RETURN PAGE */
    @GetMapping("/post")
    public String write(){
        return "board/write";
    }

    /* CREATE */
    @PostMapping("/post")
    public String write(@Valid BoardDto.Request boardDto, Errors errors , @LoginUser SessionUser sessionUser, Model model, @RequestParam(value = "tags", required = false) String tags, @RequestParam(value = "walletAddress", required = false) String walletAddress) {
        /* 글작성 유효성 검사 */
        if(errors.hasErrors()) {
            /* 글작성 실패시 입력 데이터 값 유지 */
            model.addAttribute("boardDto",boardDto) ;
            /* 유효성 통과 못한 필드와 메세지를 핸들링 */
            model.addAttribute("error","제목을 입력해주세요.");
            return "board/write";
        }

        /* 썸네일 부재시 디폴트값 설정 */
        if (boardDto.getThumbnail().equals("") || boardDto.getThumbnail().equals(null)){
            boardDto.setThumbnail("/img/panda.png");
        }
        boardDto.setWriter(sessionUser.getName());
        Long board_Id = boardService.savePost(sessionUser.getName(),boardDto);

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
                hashTagService.SaveAll(board_Id,hashTagDtoList);
            }catch (ParseException e) {
                log.info(e.getMessage());
            }
        }

        /* 스마트 컨트랙트 토큰 지급 */
        if(!walletAddress.isBlank() || walletAddress == null) {
            transferTokenService.transfer(walletAddress);
        }

        return "redirect:/board/list";
    }

    /* READ */
    @GetMapping("/post/read/{boardId}")
    public String detail(@PathVariable("boardId") Long boardId, @LoginUser SessionUser sessionUser, Model model, HttpServletRequest request, HttpServletResponse response) {
        log.info("---FindById 게시글---");
        BoardDto.Response boardDTO = boardService.findById(boardId);
        List<CommentDto.Response> comments = commentService.convertNestedStructure(boardDTO.getComments());

        /* 쿠키 관련 */
        Cookie oldCookie = null;
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("viewCookie")) {
                    oldCookie = cookie;
                }
            }
        }
        if (oldCookie != null) {
            if (!oldCookie.getValue().contains("[" + boardId.toString() + "]")) {
                boardService.updateView(boardId); /* 조회수++ */
                oldCookie.setValue(oldCookie.getValue() + "[" + boardId + "]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60 * 60 * 24); /* 유효시간 */
                response.addCookie(oldCookie);
            }
        }else {
            Cookie newCookie = new Cookie("viewCookie", "[" + boardId + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 2);
            response.addCookie(newCookie);
            boardService.updateView(boardId); /* 조회수++ */
        }

        /* 좋아요 관련 */
        log.info("---FindLikeCunt 좋아요 개수 쿼리---");
        Long like_count = likeService.findLikeCount(boardId);
        model.addAttribute("likeCount", like_count);

        if(sessionUser != null){
            log.info("---FindLike 좋아요 클릭 여부---");
            if(likeService.findLike(sessionUser.getName(), boardId)){
                model.addAttribute("isLiked",true);
            }else {
                model.addAttribute("isLiked", false);
            }
        }else {
            model.addAttribute("isLiked", false);
        }

        /* 댓글 리스트 */
        if(comments != null && !comments.isEmpty()) {
            model.addAttribute("comments",comments);
        }

        /* 사용자 관련 */
        if(sessionUser != null){
            /* 게시글 작성자 본인인지 확인 */
            if(boardDTO.getUserId().equals(sessionUser.getId())) {
                model.addAttribute("iswriter",true);
            }

            /* 댓글 작성자 본인인지 확인 */
            for (int i = 0; i < comments.size(); i++) {
                //댓글 작성자 id와 현재 사용자 id를 비교
                boolean iswriterComment = comments.get(i).getUserId().equals(sessionUser.getId());
                model.addAttribute("iswriterComment", iswriterComment);
            }
        }
        model.addAttribute("boardDto",boardDTO);
        return "board/detail";
    }

    /* RETURN PAGE */
    @GetMapping("post/edit/{no}")
    public String edit(@PathVariable("no") Long no, Model model) {
        BoardDto.Response boardDTO = boardService.getPost(no);

        /* 해시태그 내용만 Filter */
        Set<HashTag> hashTags = hashTagService.getTags(no);
        Iterator<HashTag> hashTagContents = hashTags.iterator();
        StringBuilder sb = new StringBuilder();

        while (hashTagContents.hasNext()) {
           sb.append(hashTagContents.next().getTagcontent()).append(",");
        }

        /* Html -> MarkDown */
        CopyDown converter = new CopyDown();
        String myHtml = boardDTO.getContent();
        boardDTO.setContent(converter.convert(myHtml));

        model.addAttribute("boardDto",boardDTO);
        model.addAttribute("hashTags", sb);
        model.addAttribute("no", no);
        return "board/update";
    }

    /* UPDATE */
    @PutMapping("/post/edit/{no}")
    public String update(@PathVariable("no") Long no,BoardDto.Request boardDto, @RequestParam(value = "tags",required = false) String tags ,@LoginUser SessionUser sessionUser) {
        boardDto.setWriter(sessionUser.getName());
        boardService.updatePost(no,boardDto);

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
                HashSet<HashTag> OriginHashTags =  hashTagService.getTags(no);
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
                    hashTagService.SaveAll(no,hashTagDtoList);
                }

                /* 삭제된 해시태그 */
                HashSet<String> SubTags = new HashSet<>(OriginHashTagsContent);  // s1으로 substract 생성
                SubTags.removeAll(hashTagList);                                  // 차집합 수행
                List<String> setTolist = new ArrayList<>(SubTags);

                if(!SubTags.isEmpty()) {
                    hashTagService.DeleteAll(no,setTolist);
                }


            }catch (ParseException e) {
                log.info(e.getMessage());
            }
        }else{

        }

        return "redirect:/board/list";
    }

    /* DELETE */
    @DeleteMapping("/post/{no}")
    public String delete(@PathVariable("no") Long no) {
        boardService.deletePost(no);
        return "redirect:/board/list";
    }

    /*
    검색
    keyword를 view로 부터 전달 받고
    Service로 부터 받은 boardDtoList를 model의 attribute로 전달해준다. */
    @GetMapping("/search")
    public String search(@RequestParam(value = "page", defaultValue = "1") Integer pageNum ,@RequestParam(value = "keyword") String keyword, Model model) {
        List<BoardListVo> boardDtoList = boardService.searchPosts(pageNum, keyword);
        model.addAttribute("boardList", boardDtoList);
        return "/board/list";
    }


    /* ----Comment---- */

    /* CREATE */
    @PostMapping("/comment/{id}")
    public ResponseEntity commentSave(@PathVariable Long id, @RequestBody CommentDto.Request commentDto, @LoginUser SessionUser sessionUser) {
        return ResponseEntity.ok(commentService.commentSave(sessionUser.getName(), id, commentDto));
    }
    /* CREATE */
    @PostMapping("/recomment/{id}/{parendId}")
    public ResponseEntity recommentSave(@PathVariable Long id,@PathVariable Long parendId ,@RequestBody CommentDto.Request commentDto, @LoginUser SessionUser sessionUser) {

        return ResponseEntity.ok(commentService.recommentSave(sessionUser.getName(), id, parendId, commentDto));
    }

    /* UPDATE */
    @PutMapping("/post/{boardId}/comment/{commentId}")
    public ResponseEntity commentUpdate(@PathVariable Long commentId, @RequestBody CommentDto.Request commentDto) {
        commentService.commentUpdate(commentId, commentDto);
        return ResponseEntity.ok(commentId);
    }

    /* DELETE */
    @DeleteMapping("/post/{boardId}/comment/{commentId}")
    public ResponseEntity commentDelete(@PathVariable Long commentId) {
        commentService.commentDelete(commentId);
        return ResponseEntity.ok(commentId);
    }

    /* ----Likes---- */

    /* CREATE */
    @PostMapping("/post/{boardId}/like")
    public ResponseEntity likeSave(@PathVariable Long boardId, @LoginUser SessionUser sessionUser) {
        return ResponseEntity.ok(likeService.likeSave(sessionUser.getName(),boardId));
    }

    /* DELETE */
    @DeleteMapping("/post/{boardId}/like")
    public ResponseEntity deleteLike(@PathVariable Long boardId, @LoginUser SessionUser sessionUser) {
        return ResponseEntity.ok(likeService.deleteLike(sessionUser.getName(), boardId));
    }


}










