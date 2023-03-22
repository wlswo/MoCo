package com.board.board.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardListVo {
    private Long id;
    private LocalDateTime created_date;
    private String title;
    private String writer;
    private String content;
    private Long userId;
    private Integer view;
    private String thumbnail;
    private String subcontent;
    private Integer likecnt;
    private Integer commentcnt;
    private String picture;
    private String hashTag;
    private Boolean isfull;

    @QueryProjection
    public BoardListVo(Long id, LocalDateTime created_date, String title, String writer, String content, Long userId, Integer view, String thumbnail, String subcontent, Integer likecnt, Integer commentcnt, String picture, String hashTag, Boolean isfull) {
        this.id = id;
        this.created_date = created_date;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.userId = userId;
        this.view = view;
        this.thumbnail = thumbnail;
        this.subcontent = subcontent;
        this.likecnt = likecnt;
        this.commentcnt = commentcnt;
        this.picture = picture;
        this.hashTag = hashTag;
        this.isfull = isfull;
    }
}



/*
public interface BoardListVo {
    Integer getId();
    LocalDateTime getCreated_date();
    String  getContent();
    String  getTitle();
    String  getWriter();
    Integer getUser_id();
    Integer getView();
    String  getThumbnail();
    String  getSubcontent();
    Integer getLike_count();
    Integer getComment_count();
    String  getPicture();
    String  getHashTag();
    Boolean getIsfull();
}
*/