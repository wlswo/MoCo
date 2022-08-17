package com.board.board.dto;

import com.board.board.domain.Board;
import com.board.board.domain.Comment;
import com.board.board.domain.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class BoardDto {

    @Data
    @NoArgsConstructor //인자 없이 객체 생성 가능
    @AllArgsConstructor
    @Builder
    public static class Request{
        private Long id;
        private String writer;
        private String title;
        private String content;
        private int view;
        private LocalDateTime createdDate;
        private LocalDateTime modifiedDate;
        private User user;

        /* Dto -> Entity */
        public Board toEntity() {
            Board board = Board.builder().id(id).writer(writer).title(title).content(content).view(0).user(user).build();
            return board;
        }
    }

    @Getter
    public static class Response {
        private Long id;
        private String title;
        private String writer;
        private String content;
        private int  view;
        private LocalDateTime createdDate;
        private LocalDateTime modifiedDate;
        private Long userId;
        private List<CommentDto.Response> comments;

        /* Entity -> Dto */
        public Response(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.writer = board.getWriter();
            this.content = board.getContent();
            this.view = board.getView();
            this.createdDate = board.getCreatedDate();
            this.modifiedDate = board.getModifiedDate();
            this.userId = board.getUser().getId();
            this.comments = board.getComments().stream().map(CommentDto.Response::new).collect(Collectors.toList());
        }
    }

}

