package com.board.board.controller;

import com.board.board.dto.BoardDto;
import com.board.board.dto.BoardListVo;
import com.board.board.dto.CommentDto;
import com.board.board.service.board.BoardService;
import com.board.board.service.board.CommentService;
import com.board.board.service.board.RecruitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller @RequestMapping("/admin")
public class AdminController {
    private final BoardService boardService;
    private final CommentService commentService;
    private final RecruitService recruitService;

    @GetMapping("/")
    public String adminPage(Model model, @RequestParam(value = "page", defaultValue = "1") Integer pageNum) {
        List<BoardListVo> boardList = boardService.getBoardListOnRecruit(pageNum);
        Integer pageList = boardService.getPageList(pageNum);

        model.addAttribute("boardList",boardList);
        model.addAttribute("totalPage", pageList);

        return "admin/admin";
    }

    /* READ - 게시글 읽기 */
    @GetMapping("/read/{boardId}")
    public String adminDetail(@PathVariable("boardId") Long boardId, Model model) {
        BoardDto.Response boardDTO = boardService.findById(boardId);
        List<CommentDto.Response> comments = commentService.convertNestedStructure(boardDTO.getComments());

        /* 댓글 리스트 */
        if(comments != null && !comments.isEmpty()) {
            model.addAttribute("comments",comments);
        }

        /* 현재 참가 인원 */
        Long joinUsers = recruitService.countToJoinUsers(boardId);
        model.addAttribute("joinUsers",joinUsers);
        model.addAttribute("boardDto",boardDTO);
        return "admin/adminDetail";
    }

    /* READ - 무한스크롤 AJAX */
    @GetMapping("/listJson/{page}/{isRecruitOn}")
    public ResponseEntity listJson(@PathVariable("page") Integer pageNum, @PathVariable("isRecruitOn") Boolean isRecruitOn) {
        List<BoardListVo> boardList = new ArrayList<>();
        if(isRecruitOn) { /* 모집중만 */
            boardList = boardService.getBoardListOnRecruit(pageNum);
        }else {           /* 전체 게시글 */
            boardList = boardService.getBoardlist(pageNum);
        }
        return ResponseEntity.ok(boardList);
    }

    /* READ - 검색 */
    @GetMapping("/search")
    public String search(@RequestParam(value = "page", defaultValue = "1") Integer pageNum ,@RequestParam(value = "keyword") String keyword, Model model) {
        List<BoardListVo> boardDtoList = boardService.searchPosts(pageNum, keyword);
        model.addAttribute("boardList", boardDtoList);
        return "/admin/admin";
    }

    /* DELETE - 게시글 삭제 */
    @DeleteMapping("/post/{boardId}")
    public String deletePost(@PathVariable("boardId") Long boardId){
        boardService.deletePost(boardId);
        return "redirect:/admin/";
    }

    /* DELETE - 댓글 삭제 */
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity deleteComment(@PathVariable("commentId") Long commentId) {
        commentService.commentDelete(commentId);
        return ResponseEntity.ok().build();
    }
}
