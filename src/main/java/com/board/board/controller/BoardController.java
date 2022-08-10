package com.board.board.controller;


import com.board.board.dto.BoardDto;
import com.board.board.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    //게시글 상세 페이지이며, {no}로 페이지 넘버를 받는다.
    //PathVarible 어노테이션을 통해 no를 받음
    @GetMapping("/post/{no}")
    public String datail(@PathVariable("no") Long no , Model model) {
        BoardDto boardDTO = boardService.getPost(no);

        model.addAttribute("boardDto",boardDTO);
        return "board/detail";
    }

    //게시글 수정 페이지이며, {no}로 페이지 넘버를 받는다.
    @GetMapping("post/edit/{no}")
    public String edit(@PathVariable("no") Long no, Model model) {
        BoardDto boardDTO = boardService.getPost(no);

        model.addAttribute("boardDto",boardDTO);
        return "board/update";
    }

    //위는 GET메서드이며, PUT메서드를 이용해 게시물을 수정한 부분에 대해 적용
    @PutMapping("/post/edit/{no}")
    public String update(BoardDto boardDTO) {
        boardService.savePost(boardDTO);

        return "redirect:/board/list";
    }

    //게시물 삭제는 deletePost 메서드를 사용하여 간단하게 삭제할 수 있다.
    @DeleteMapping("/post/{no}")
    public String delete(@PathVariable("no") Long no) {
        boardService.deletePost(no);
        return "redirect:/board/list";
    }

    //검색
    //keyword를 view로 부터 전달 받고
    //Service로 부터 받은 boardDtoList를 model의 attribute로 전달해준다.
    @GetMapping("/search")
    public String search(@RequestParam(value = "keyword") String keyword, Model model) {
        List<BoardDto> boardDtoList = boardService.searchPosts(keyword);
        System.out.println(keyword);
        model.addAttribute("boardList", boardDtoList);
        return "board/list";
    }

}










