package com.board.board.dto;

import com.board.board.domain.Board;
import com.board.board.domain.Comment;
import com.board.board.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
public class CommentDto {
    private Long id;
    private String comment;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private User user;   //유저 번호
    private Board board; //게시글 번호

    /* DTO -> Entity */
    public Comment toEntity() {
        Comment comments = Comment.builder().id(id).comment(comment).user(user).board(board).build();
        return comments;
    }

    public CommentDto(Long id, String comment, LocalDateTime createdDate, LocalDateTime modifiedDate, User user, Board board) {
        this.id = id;
        this.comment = comment;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.user = user;
        this.board = board;
    }
}
