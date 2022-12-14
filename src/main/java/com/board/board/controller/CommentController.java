package com.board.board.controller;

import com.board.board.config.LoginUser;
import com.board.board.config.auth.SessionUser;
import com.board.board.dto.CommentDto;
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

    /* ------ Comment π¬ ------- */

    /* CREATE - λκΈ λ¬κΈ° */
    @Operation(summary = "λκΈ μμ±", description = "λκΈμ μμ±νκΈ°μν μμ²­μλλ€.")
    @PostMapping("/{boardId}")
    public ResponseEntity commentSave(@Parameter(description = "ν΄λΉ λ²νΈλ₯Ό κ°μ§ κ²μκΈμ λκΈμ μμ±ν©λλ€.") @PathVariable Long boardId, @Parameter(description = "λκΈμ μ λ³΄κ° λ΄κΈ΄ Request κ°μ²΄μλλ€.") @RequestBody CommentDto.Request commentDto, @LoginUser SessionUser sessionUser) {
        return ResponseEntity.ok(commentService.commentSave(sessionUser.getId(), boardId, commentDto));
    }

    /* CREATE - λ΅κΈ λ¬κΈ° */
    @Operation(summary = "λκΈμ λ΅κΈ μμ±", description = "λλκΈμ μμ±νκΈ°μν μμ²­μλλ€.")
    @PostMapping("/{boardId}/{parentId}")
    public ResponseEntity recommentSave(@Parameter(description = "ν΄λΉ λ²νΈλ₯Ό κ°μ§ κ²μκΈμ λ¬λ¦° λκΈμ λ΅κΈμ μμ±ν©λλ€.") @PathVariable Long boardId, @Parameter(description = "λ΅κΈμ λ¬κΈ° μν λΆλͺ¨λκΈμ λ²νΈμλλ€.") @PathVariable Long parentId ,@Parameter(description = "λλκΈμ μ λ³΄κ° λ΄κΈ΄ Request κ°μ²΄μλλ€.") @RequestBody CommentDto.Request commentDto, @LoginUser SessionUser sessionUser) {
        return ResponseEntity.ok(commentService.recommentSave(sessionUser.getId(), boardId, parentId, commentDto));
    }

    /* UPDATE - λκΈ/λ΅κΈ μμ  */
    @Operation(summary = "λκΈ μμ ", description = "λκΈ κ³Ό λλκΈμ μμ νκΈ° μν μμ²­μλλ€.")
    @PutMapping("/{commentId}/{userId}")
    public ResponseEntity commentUpdate(@Parameter(description = "ν΄λΉλ²νΈλ₯Ό κ°μ§ λκΈμ μμ ν©λλ€.") @PathVariable Long commentId, @Parameter(description = "λκΈ μμ±μμ λ²νΈμλλ€.") @PathVariable Long userId, @Parameter(description = "") @RequestBody CommentDto.Request commentDto, @LoginUser SessionUser sessionUser) {
        if(!sessionUser.getId().equals(userId)) {
            return ResponseEntity.badRequest().build();
        }

        commentService.commentUpdate(commentId, commentDto);
        return ResponseEntity.ok(commentId);
    }

    /* DELETE - λκΈ μ­μ  */
    @Operation(summary = "λκΈ μ­μ ", description = "λκΈμ μ­μ ν©λλ€.")
    @DeleteMapping("/{commentId}")
    public ResponseEntity commentDelete(@Parameter(description = "ν΄λΉλ²νΈλ₯Ό κ°μ§ λκΈμ μ­μ ν©λλ€.") @PathVariable Long commentId) {
        commentService.commentDelete(commentId);
        return ResponseEntity.ok(commentId);
    }
}
