package com.board.board.controller;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("board") //board 경로로 들어오는 경우 , 그후 해당 필드의 Method로 분기될 수 있도록 설정
public class BoardController {
    private BoardService boardService;

    //게시판

    //게시글 목록
    //list 경로로 GET메서드 요청이 들어올 경우 list 메서드와 매핑
    //list 경로에 요청 파라미터가 있을 경우 (?page=1), 그에 따른 페이징을 수행

    @GetMapping({"","/list"})
    public String list(Model model, @RequestParam(value = "page", defaultValue = "1") Integer pageNum) {
        List<BoardDto> boardList = boardService.getBoardlist(pageNum);
        Integer[] pageList = boardService.getPageList(pageNum);

        model.addAttribute("boardList",boardList);
        model.addAttribute("pageList", pageList);

        return "board/list";
    }

    //글쓰는 페이지
    @GetMapping("/post")
    public String write(){
        return "board/write";
    }

    //글을 쓴뒤 POST메서드로 글 쓴 내용을 DB에 저장
    //그 후에는 /list 경로로 리디렉션해준다.
    @PostMapping("/post")
    public String write(BoardDto boardDto) {
        boardService.savePost(boardDto);
        return "redirect:/board/list";
    }



}
