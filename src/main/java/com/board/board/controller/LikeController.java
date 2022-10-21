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

    /* ----- Likes 🌠 ----- */

    /* CREATE - 좋아요 */
    @Operation(summary = "좋아요 추가", description = "좋아요를 추가합니다.")
    @PostMapping("/{boardId}")
    public ResponseEntity likeSave(@Parameter(description = "해당 번호를 가진 게시글에 좋아요를 추가합니다.") @PathVariable Long boardId, @Parameter(description = "좋아요를 추가한 사용자를 식별합니다.")@LoginUser SessionUser sessionUser) {
        return ResponseEntity.ok(likeService.likeSave(sessionUser.getName(),boardId));
    }

    /* DELETE - 좋아요 취소 */
    @Operation(summary = "좋아요 삭제", description = "좋아요를 삭제합니다.")
    @DeleteMapping("/{boardId}")
    public ResponseEntity deleteLike(@Parameter(description = "해당 번호를 가진 게시글에 좋아요를 삭제합니다.") @PathVariable Long boardId, @Parameter(description = "좋아요를 취소한 사용자를 식별합니다.") @LoginUser SessionUser sessionUser) {
        return ResponseEntity.ok(likeService.deleteLike(sessionUser.getName(), boardId));
    }
}
