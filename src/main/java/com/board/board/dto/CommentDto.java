package com.board.board.dto;

import com.board.board.domain.Board;
import com.board.board.domain.Comment;
import com.board.board.domain.User;
import lombok.*;

import java.time.LocalDateTime;

public class CommentDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        private Long id;
        private String comment;
        private LocalDateTime createdDate;
        private LocalDateTime modifiedDate;
        private User user;
        private Board board;

        /* DTO -> Entity */
        public Comment toEntity() {
            Comment comments = Comment.builder().id(id).comment(comment).user(user).board(board).build();
            return comments;
        }
    }

    @Getter
    public static class Response {
        private Long id;
        private String comment;
        private LocalDateTime createdDate;
        private LocalDateTime modifiedDate;
        private String name;
        private Long userId;
        private Long boardId;

        /* Entity -> Dto */
        public Response(Comment comment) {
            this.id = comment.getId();
            this.comment = comment.getComment();
            this.createdDate = comment.getCreatedDate();
            this.modifiedDate = comment.getModifiedDate();
            this.name = comment.getUser().getName();
            this.userId = comment.getUser().getId();
            this.boardId = comment.getBoard().getId();
        }
    }


}
