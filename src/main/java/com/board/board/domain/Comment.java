package com.board.board.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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
    private Board board;   //게시글 id

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;     //작성자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent; //부모댓글 id 참조 (Self Join)

    /* 부모 댓글을 삭제해도 자식 댓글은 남아있음 */
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<Comment> childList;

    private boolean isRemoved = false;

    /* 댓글 수정 */
    public void update(String comment) {
        this.comment = comment;
    }

    /* 삭제 표시 */
    public void remove() {
        this.isRemoved = true;
    }

}













