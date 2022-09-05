package com.board.board.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;

@Builder
@AllArgsConstructor @NoArgsConstructor
@Getter
@Table(name = "comments")
@Entity
public class Comment extends Time{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String comment; //댓글 내용

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board; //게시글 id

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;  //작성자

    /* 댓글 수정 */
    public void update(String comment) {
        this.comment = comment;
    }
}













