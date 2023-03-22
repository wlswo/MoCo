package com.board.board.controller;

import com.board.board.dto.BoardDto;
import com.board.board.dto.BoardListVo;
import com.board.board.dto.CommentDto;
import com.board.board.service.board.BoardService;
import com.board.board.service.board.CommentService;
import com.board.board.service.board.RecruitService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminController {
    private final BoardService boardService;
    private final CommentService commentService;
    private final RecruitService recruitService;

    @Operation(summary = "페이지 반환", description = "관리자 페이지를 반환합니다. 관리자만 요청이 가능합니다.")
    @GetMapping({"", "/"})
    public String adminPage(Model model, @RequestParam(value = "page", defaultValue = "1") Integer pageNum) {
        List<BoardListVo> boardList = boardService.getBoardListOnRecruit(pageNum);
        Integer pageList = boardService.getPageList(pageNum);

        model.addAttribute("boardList", boardList);
        model.addAttribute("totalPage", pageList);

        return "admin/admin";
    }

    /* READ - 게시글 읽기 */
    @Operation(summary = "페이지 반환", description = "관리자용 게시글 읽기 페이지를 반환합니다.")
    @GetMapping("/{boardId}")
    public String adminDetail(@PathVariable("boardId") Long boardId, Model model) {
        BoardDto.Response boardDTO = boardService.findById(boardId);
        List<CommentDto.Response> comments = commentService.convertNestedStructure(boardDTO.getComments());

        /* 댓글 리스트 */
        if (comments != null && !comments.isEmpty()) {
            model.addAttribute("comments", comments);
        }

        /* 현재 참가 인원 */
        Long joinUsers = recruitService.countToJoinUsers(boardId);
        model.addAttribute("joinUsers", joinUsers);
        model.addAttribute("boardDto", boardDTO);
        return "admin/adminDetail";
    }

    /* DELETE - 게시글 삭제 */
    @Operation(summary = "게시글 삭제", description = "관리자가 게시글을 삭제하기 위한 요청입니다.")
    @DeleteMapping("/{boardId}")
    public String deletePost(@PathVariable("boardId") Long boardId) {
        boardService.deletePost(boardId);
        return "redirect:/admin/";
    }

    /* DELETE - 댓글 삭제 */
    @Operation(summary = "댓글 삭제", description = "관리자가 댓글을 삭제하기 위한 요청입니다.")
    @DeleteMapping("/comment/{commentId}/{boardId}")
    public ResponseEntity deleteComment(@PathVariable("commentId") Long commentId, @PathVariable("boardId") Long boardId) {
        commentService.commentDelete(boardId, commentId);
        return ResponseEntity.ok().build();
    }
}
