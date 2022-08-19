package com.board.board.dto;

import com.board.board.domain.Board;
import com.board.board.domain.Like;
import com.board.board.domain.User;
import lombok.*;

public class LikeDto {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        private Long id;
        private User user;
        private Board board;

        /* DTO -> Entity */
        public Like toEntity() {
            Like like = Like.builder().id(id).user(user).board(board).build();
            return like;
        }
    }

    @Getter
    public static class Response {
        private Long id;
        private Long userId;
        private Long boardId;

        /* Entity -> DTO */
        public  Response(Like like) {
            this.id = like.getId();
            this.userId = like.getUser().getId();
            this.boardId = like.getBoard().getId();
        }
    }
}
