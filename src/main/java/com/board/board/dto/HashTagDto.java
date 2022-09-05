package com.board.board.dto;

import com.board.board.domain.Board;
import com.board.board.domain.HashTag;
import lombok.*;

public class HashTagDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        private Long id;
        private String tagcontent;
        private Board board;

        /* DTO -> Entity */
        public HashTag toEntity() {
            HashTag hashTag = HashTag.builder().id(id).tagcontent(tagcontent).board(board).build();
            return hashTag;
        }
    }

    @Getter
    public static class Response {
        private Long id;
        private String tagcontent;
        private Long boardId;

        /* Entity -> DTO */
        public Response(HashTag hashTag) {
            this.id = hashTag.getId();
            this.tagcontent = hashTag.getTagcontent();
            this.boardId = hashTag.getBoard().getId();
        }
    }
}
