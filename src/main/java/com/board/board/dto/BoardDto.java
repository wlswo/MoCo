package com.board.board.dto;

import com.board.board.domain.Board;
import com.board.board.domain.Comment;
import com.board.board.domain.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
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
        private LocalDateTime createdDate;
        private LocalDateTime modifiedDate;
        private User user;

        /* Dto -> Entity */
        public Board toEntity() {
            Board board = Board.builder().id(id).writer(writer).title(title).content(content).subcontent(subcontent).thumbnail(thumbnail).view(0).user(user).build();
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
        private LocalDateTime createdDate;
        private LocalDateTime modifiedDate;
        private Long userId;
        private String userImg;
        private List<CommentDto.Response> comments;
        private Set<LikeDto.Response> likes;
        private Set<HashTagDto.Response> hashtags;

        /* Entity -> Dto */
        public Response(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.writer = board.getWriter();
            this.content = board.getContent();
            this.subcontent = board.getSubcontent();
            this.thumbnail = board.getThumbnail();
            this.view = board.getView();
            this.createdDate = board.getCreatedDate();
            this.modifiedDate = board.getModifiedDate();
            this.userId = board.getUser().getId();
            this.userImg = board.getUser().getPicture();
            this.comments = board.getComments().stream().map(CommentDto.Response::new).collect(Collectors.toList());
            this.likes = board.getLikes().stream().map(LikeDto.Response::new).collect(Collectors.toSet());
            this.hashtags = board.getHashTags().stream().map(HashTagDto.Response::new).collect(Collectors.toSet());
        }
    }

}

