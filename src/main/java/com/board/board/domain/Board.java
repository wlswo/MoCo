package com.board.board.domain;

//Board : 실제 DB와 매칭될 클래스 (Entity Class)

//JPA에서는 프록시 생성을 위해 기본 생성자를 반드시 하난 생성해야 한다.
//생성자 자동 생성 : NoArgsConstructor, AllArgsConstructor
//NoArgsConstructor : 객체 생성 시 초기 인자 없이 객체를 생성할 수 있다.


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import javax.persistence.*;
import java.util.List;

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

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OrderBy("id asc") //댓글 정렬
    @JsonIgnoreProperties({"board"})
    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Comment> comments;
    /*
    *   DB 에는 하나의 Raw 데이터 값만 들어갈수 있다. ( 원자성 조건 )
    *   근데 List<T> 형태로 들어갈수 없기 때문에 comments는 DB에 FK로 생성되면 안된다.
    *   mappedBy를 사용하여 연관관계의 주인이 아니라는 것을 명시해줘야함. (DB의 FK가 아니다)
    *   [무한참조 발생] board 조회 -> comment 조회 -> board조회 ...
    *   @JsonIgnoreProerties({"board"}) 추가로 해결
    */


    //Java 디자인 패턴 , 생성 시점에 값을 채워줌
    @Builder
    public Board(Long id, String title, String content, String writer, int view ,User user){
        //Assert 구문으로 안전한 객체 생성 패턴을 구현
        Assert.hasText(writer,"writer must not be empty");
        Assert.hasText(title, "title must not be empty");
        Assert.hasText(content, "content must not be empty");

        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.view = view;
        this.user = user;

    }
}











