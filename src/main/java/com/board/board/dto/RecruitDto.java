package com.board.board.dto;

import com.board.board.domain.Board;
import com.board.board.domain.Like;
import com.board.board.domain.Recruit;
import com.board.board.domain.User;
import lombok.*;

public class RecruitDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        private Long id;
        private User user;
        private Board board;

        /* DTO -> Entity */
        public Recruit toEntity() {
            Recruit recruit = Recruit.builder().id(id).user(user).board(board).build();
            return recruit;
        }
    }

    @Getter
    public static class Response {
        private Long id;
        private Long userId;
        private Long boardId;

        /* Entity -> DTO */
        public  Response(Recruit recruit) {
            this.id = recruit.getId();
            this.userId = recruit.getUser().getId();
            this.boardId = recruit.getBoard().getId();
        }
    }
}
