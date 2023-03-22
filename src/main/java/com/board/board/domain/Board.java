package com.board.board.domain;

//Board : 실제 DB와 매칭될 클래스 (Entity Class)

//JPA에서는 프록시 생성을 위해 기본 생성자를 반드시 하난 생성해야 한다.
//생성자 자동 생성 : NoArgsConstructor, AllArgsConstructor
//NoArgsConstructor : 객체 생성 시 초기 인자 없이 객체를 생성할 수 있다.


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) //외부에서 생성을 열어 둘 필요가 없을 때 , 보안적으로 권장함
@Getter
@Entity
@Table(name = "board")
public class Board extends Time {
    @Id //Primary Key Field
    @GeneratedValue(strategy = GenerationType.IDENTITY) //PK 생성 규칙
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(length = 10, nullable = false)
    private String writer;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String hashTag;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String subcontent;

    @Column(length = 300, nullable = false)
    private String thumbnail;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int likecnt;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int commentcnt;

    @Column
    @Builder.Default
    private boolean isfull = false;


    /* 댓글 */
    @OrderBy("id asc") //댓글 정렬
    @JsonIgnoreProperties({"board"})
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    /* 좋아요 */
    @JsonIgnoreProperties({"board"})
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<Like> likes;

    /* 모집된 인원 */
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<Recruit> recruits;

    public void update(String title, String hashTag, String content, String subcontent, String thumbnail, String location) {
        this.title = title;
        this.hashTag = hashTag;
        this.content = content;
        this.subcontent = subcontent;
        this.thumbnail = thumbnail;
        this.location = location;
    }

    public boolean close() {
        this.isfull = !this.isfull;
        return this.isfull;
    }
}








