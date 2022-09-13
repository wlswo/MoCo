package com.board.board.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private User user;    //작성자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;     //부모댓글 id 참조 (Self Join)

    /* 부모 댓글을 삭제해도 자식 댓글은 남아있음 */
    @OneToMany(fetch = FetchType.LAZY , mappedBy = "parent")
    private List<Comment> childList = new ArrayList<>();

    private boolean isRemoved = false;

    /* 댓글 수정 */
    public void update(String comment) {
        this.comment = comment;
    }

    /* 삭제 */
    public void remove() {
        this.isRemoved = true;
    }

    public void addChild(Comment child){
        childList.add(child);
    }

    /* 비즈니스 로직 */
    public List<Comment> findRemovableList() {
        List<Comment> result = new ArrayList<>();
        Optional.ofNullable(this.parent).ifPresentOrElse(
                parentComment ->{//대댓글인 경우 (부모가 존재하는 경우)
                    if( parentComment.isRemoved()&& parentComment.isAllChildRemoved()){
                        result.addAll(parentComment.getChildList());
                        result.add(parentComment);
                    }
                },
                () -> {//댓글인 경우
                    if (isAllChildRemoved()) {
                        result.add(this);
                        result.addAll(this.getChildList());
                    }
                }
        );
        return result;
    }

    //모든 자식 댓글이 삭제되었는지 판단
    private boolean isAllChildRemoved() {
        return getChildList().stream()//https://kim-jong-hyun.tistory.com/110 킹종현님 사랑합니다.
                .map(Comment::isRemoved)//지워졌는지 여부로 바꾼다
                .filter(isRemove -> !isRemove)//지워졌으면 true, 안지워졌으면 false이다. 따라서 filter에 걸러지는 것은 false인 녀석들이고, 있다면 false를 없다면 orElse를 통해 true를 반환한다.
                .findAny()//지워지지 않은게 하나라도 있다면 false를 반환
                .orElse(true);//모두 지워졌다면 true를 반환

    }

}













