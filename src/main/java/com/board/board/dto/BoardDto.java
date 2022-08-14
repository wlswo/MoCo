package com.board.board.dto;

//DTO : 데이터 전달 목절
//데이터를 캡슐화한 객체를 전달

import com.board.board.domain.Board;
import com.board.board.domain.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString          //객체가 가지고 있는 정보나 값들을 문자열로 만들어 리턴하는 메소드
@NoArgsConstructor //인자 없이 객체 생성 가능
public class BoardDto {
    private Long id;
    private String writer;
    private String title;
    private String content;
    private int  view;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private User user;

    /* Dto -> Entity */
    public Board toEntity(){
        Board board = Board.builder().id(id).writer(writer).title(title).content(content).view(0).user(user).build();
        return board;
    }

    @Builder
    public BoardDto(Long id, String title, String content,String writer, int view,LocalDateTime createdDate, LocalDateTime modifiedDate,User user) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.view = view;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.user = user;
    }
}
