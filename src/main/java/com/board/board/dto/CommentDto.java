package com.board.board.dto;

import com.board.board.domain.Board;
import com.board.board.domain.Comment;
import com.board.board.domain.User;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        private Comment parent;

        /* DTO -> Entity */
        public Comment toEntity() {
            Comment comments = Comment.builder().id(id).comment(comment).user(user).board(board).parent(parent).build();
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
        private Comment parent;
        private List<CommentDto.Response> childList;
        private boolean isRemoved;

        /* Entity -> DTO */
        public Response(Comment comment) {
            this.id = comment.getId();
            this.comment = comment.getComment();
            this.createdDate = comment.getCreatedDate();
            this.modifiedDate = comment.getModifiedDate();
            this.name = comment.getUser().getName();
            this.userId = comment.getUser().getId();
            this.boardId = comment.getBoard().getId();
            this.parent = comment.getParent();
            this.childList = new ArrayList<>();
            this.isRemoved = comment.isRemoved();
        }
    }


}
