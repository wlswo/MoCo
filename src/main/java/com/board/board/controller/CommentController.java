package com.board.board.controller;

import com.board.board.config.LoginUser;
import com.board.board.config.auth.SessionUser;
import com.board.board.dto.CommentDto;
import com.board.board.service.board.BoardService;
import com.board.board.service.board.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("comment")
public class CommentController {
    private final CommentService commentService;

    /* ------ Comment 💬 ------- */

    /* CREATE - 댓글 달기 */
    @Operation(summary = "댓글 작성", description = "댓글을 작성하기위한 요청입니다.")
    @PostMapping("/{boardId}")
    public ResponseEntity commentSave(@Parameter(description = "해당 번호를 가진 게시글에 댓글을 작성합니다.") @PathVariable Long boardId, @Parameter(description = "댓글의 정보가 담긴 Request 객체입니다.") @RequestBody CommentDto.Request commentDto, @LoginUser SessionUser sessionUser) {
        return ResponseEntity.ok(commentService.commentSave(sessionUser.getId(), boardId, commentDto));
    }

    /* CREATE - 답글 달기 */
    @Operation(summary = "댓글의 답글 작성", description = "대댓글을 작성하기위한 요청입니다.")
    @PostMapping("/{boardId}/{parentId}")
    public ResponseEntity recommentSave(@Parameter(description = "해당 번호를 가진 게시글에 달린 댓글에 답글을 작성합니다.") @PathVariable Long boardId, @Parameter(description = "답글을 달기 위한 부모댓글의 번호입니다.") @PathVariable Long parentId ,@Parameter(description = "대댓글의 정보가 담긴 Request 객체입니다.") @RequestBody CommentDto.Request commentDto, @LoginUser SessionUser sessionUser) {
        return ResponseEntity.ok(commentService.recommentSave(sessionUser.getId(), boardId, parentId, commentDto));
    }

    /* UPDATE - 댓글/답글 수정 */
    @Operation(summary = "댓글 수정", description = "댓글 과 대댓글의 수정하기 위한 요청입니다.")
    @PutMapping("/{commentId}/{userId}")
    public ResponseEntity commentUpdate(@Parameter(description = "해당번호를 가진 댓글을 수정합니다.") @PathVariable Long commentId, @Parameter(description = "댓글 작성자의 번호입니다.") @PathVariable Long userId, @Parameter(description = "") @RequestBody CommentDto.Request commentDto, @LoginUser SessionUser sessionUser) {
        if(!sessionUser.getId().equals(userId)) {
            return ResponseEntity.badRequest().build();
        }

        commentService.commentUpdate(commentId, commentDto);
        return ResponseEntity.ok(commentId);
    }

    /* DELETE - 댓글 삭제 */
    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
    @DeleteMapping("/{commentId}")
    public ResponseEntity commentDelete(@Parameter(description = "해당번호를 가진 댓글을 삭제합니다.") @PathVariable Long commentId) {
        commentService.commentDelete(commentId);
        return ResponseEntity.ok(commentId);
    }
}
