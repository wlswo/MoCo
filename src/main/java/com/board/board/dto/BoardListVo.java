package com.board.board.dto;

import java.time.LocalDateTime;
import java.util.Date;

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
}
