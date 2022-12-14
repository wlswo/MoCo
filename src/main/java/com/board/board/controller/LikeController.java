package com.board.board.controller;

import com.board.board.config.LoginUser;
import com.board.board.config.auth.SessionUser;
import com.board.board.service.board.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("like")
public class LikeController {
    private final LikeService likeService;

    /* ----- Likes π  ----- */

    /* CREATE - μ’μμ */
    @Operation(summary = "μ’μμ μΆκ°", description = "μ’μμλ₯Ό μΆκ°ν©λλ€.")
    @PostMapping("/{boardId}")
    public ResponseEntity likeSave(@Parameter(description = "ν΄λΉ λ²νΈλ₯Ό κ°μ§ κ²μκΈμ μ’μμλ₯Ό μΆκ°ν©λλ€.") @PathVariable Long boardId, @Parameter(description = "μ’μμλ₯Ό μΆκ°ν μ¬μ©μλ₯Ό μλ³ν©λλ€.")@LoginUser SessionUser sessionUser) {
        return ResponseEntity.ok(likeService.likeSave(sessionUser.getName(),boardId));
    }

    /* DELETE - μ’μμ μ·¨μ */
    @Operation(summary = "μ’μμ μ­μ ", description = "μ’μμλ₯Ό μ­μ ν©λλ€.")
    @DeleteMapping("/{boardId}")
    public ResponseEntity deleteLike(@Parameter(description = "ν΄λΉ λ²νΈλ₯Ό κ°μ§ κ²μκΈμ μ’μμλ₯Ό μ­μ ν©λλ€.") @PathVariable Long boardId, @Parameter(description = "μ’μμλ₯Ό μ·¨μν μ¬μ©μλ₯Ό μλ³ν©λλ€.") @LoginUser SessionUser sessionUser) {
        return ResponseEntity.ok(likeService.deleteLike(sessionUser.getName(), boardId));
    }
}
