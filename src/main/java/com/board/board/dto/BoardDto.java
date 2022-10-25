package com.board.board.dto;

import com.board.board.domain.Board;
import com.board.board.domain.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
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
        @NotBlank(message = "제목을 입력해주세요.")
        private String title;
        private String content;
        private String subcontent;
        private String thumbnail;
        private int view;
        private String location;
        private LocalDateTime createdDate;
        private LocalDateTime modifiedDate;
        private User user;

        /* Dto -> Entity */
        public Board toEntity() {
            Board board = Board.builder().id(id).writer(writer).title(title).content(content).subcontent(subcontent).thumbnail(thumbnail).view(0).location(location).user(user).build();
            return board;
        }
    }

    @Setter
    @Getter
    public static class Response {
        private Long id;
        private String title;
        private String writer;
        private String content;
        private String subcontent;
        private String thumbnail;
        private int  view;
        private String location;
        private LocalDateTime createdDate;
        private LocalDateTime modifiedDate;
        private Long userId;
        private String userImg;
        private List<CommentDto.Response> comments;
        private boolean isfull;

        /* Entity -> Dto */
        public Response(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.writer = board.getWriter();
            this.content = board.getContent();
            this.subcontent = board.getSubcontent();
            this.thumbnail = board.getThumbnail();
            this.view = board.getView();
            this.location = board.getLocation();
            this.createdDate = board.getCreatedDate();
            this.modifiedDate = board.getModifiedDate();
            this.userId = board.getUser().getId();
            this.userImg = board.getUser().getPicture();
            this.comments = board.getComments().stream().map(CommentDto.Response::new).collect(Collectors.toList());
            this.isfull = board.isIsfull();
        }
    }
}

