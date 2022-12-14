package com.board.board.controller;


import com.board.board.config.LoginUser;
import com.board.board.config.auth.SessionUser;
import com.board.board.dto.BoardDto;
import com.board.board.dto.BoardListVo;
import com.board.board.dto.CommentDto;
import com.board.board.dto.RecruitDto;
import com.board.board.service.board.*;
import com.board.board.service.hashTag.HashTagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/* κ²μν */
@AllArgsConstructor
@Controller
@RequestMapping("board")
public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;
    private final LikeService likeService;
    private final HashTagService hashTagService;
    private final RecruitService recruitService;
    private final MarkDownService markDownService;
    private final CookieService cookieService;
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /* ----- Board π ----- */
    @Operation(summary = "λͺ¨μ§μ€μΈ κ²μκΈ νμ΄μ§ λ°ν", description = "λͺ¨μ§μ€μΈ κ²μκΈ λ¦¬μ€νΈ λ°μ΄ν°λ₯Ό λ΄μ νμ΄μ§λ₯Ό λ°νν©λλ€.")
    @GetMapping({"","/list"})
    public String list(@Parameter(description = "νμλ¦¬νμ λ°ννκΈ° μν κ°μ²΄") Model model, @Parameter(description = "λ°νν  κ²μκΈμ νμ΄μ§ λ²νΈ") @RequestParam(value = "page", defaultValue = "1") Integer pageNum) {
        List<BoardListVo> boardList = boardService.getBoardListOnRecruit(pageNum);
        Integer pageList = boardService.getPageList(pageNum);

        model.addAttribute("boardList",boardList);
        model.addAttribute("totalPage", pageList);

        return "board/list";
    }

    /* RETURN PAGE - κ²μκΈ λͺ©λ‘ νμ΄μ§ (μ μ²΄ κ²μκΈ) */
    @Operation(summary = "λͺ¨λ  κ²μκΈ νμ΄μ§ λ°ν", description = "μ μ²΄ κ²μκΈ λ¦¬μ€νΈ λ°μ΄ν°λ₯Ό λ΄μ νμ΄μ§λ₯Ό λ°νν©λλ€.")
    @GetMapping("/list-all")
    public String recruitOn(@Parameter(description = "λ°νν  κ²μκΈμ νμ΄μ§λ²νΈ") @RequestParam(value = "page", defaultValue = "1") Integer pageNum , @Parameter(description = "νμλ¦¬νμ λ°ννκΈ° μν κ°μ²΄") Model model) {
        List<BoardListVo> boardDtoList = boardService.getBoardlist(pageNum);
        model.addAttribute("boardList",boardDtoList);
        return "board/list";
    }

    /* RETURN PAGE - κΈμμ± νμ΄μ§ */
    @Operation(summary = "κΈ μμ± νμ΄μ§ λ°ν", description = "κΈμ°κΈ° νμ΄μ§λ₯Ό λ°νν©λλ€.")
    @GetMapping("/write")
    public String write(@Parameter(description = "νμ¬ λ‘κ·ΈμΈλ μ¬μ©μ μλ³")@LoginUser SessionUser sessionUser){
        if(!sessionUser.isNameCheck()){
            return "login/OauthNameCheck";
        }
        return "board/write";
    }

    /* RETURN PAGE - κΈμ½κΈ° νμ΄μ§ */
    @Operation(summary = "κ²μκΈ μμΈ νμ΄μ§ λ°ν", description = "κ²μκΈμ ν΄λ¦­ νμλ ν΄λΉ κ²μκΈμ μμΈ νμ΄μ§λ‘ μ΄λν©λλ€.")
    @GetMapping("/detail/{boardId}")
    public String detail(@Parameter(description = "ν΄λΉλ²νΈλ₯Ό κ°μ§ κ²μκΈμ μ½μ΅λλ€.")@PathVariable("boardId") Long boardId, @Parameter(description = "νμ¬ λ‘κ·ΈμΈλ μ¬μ©μλ₯Ό μλ³")@LoginUser SessionUser sessionUser, Model model, @Parameter(description = "μ‘°νμ μ€λ³΅ λ°©μ§λ₯Ό μν΄ μΏ ν€κ°μ κ°μ Έμ€κΈ° μν νλΌλ―Έν°μλλ€. ")HttpServletRequest request, @Parameter(description = "μ‘°νμ μ€λ³΅ λ°©μ§λ₯Ό μν΄ μΏ ν€κ°μ κ°μ Έμ€κΈ° μν νλΌλ―Έν°μλλ€. ") HttpServletResponse response) {
        BoardDto.Response boardDTO = boardService.findById(boardId);
        List<CommentDto.Response> comments = commentService.convertNestedStructure(boardDTO.getComments());

        /* μ‘°νμ */
        cookieService.cookieAndView(request,response,boardId);

        /* μ’μμ κ΄λ ¨ */
        Long like_count = likeService.findLikeCount(boardId);
        model.addAttribute("likeCount", like_count);

        if(sessionUser != null){
            if(likeService.findLike(sessionUser.getId(), boardId)){
                model.addAttribute("isLiked",true);
            }else {
                model.addAttribute("isLiked", false);
            }
        }else {
            model.addAttribute("isLiked", false);
        }

        /* λκΈ λ¦¬μ€νΈ */
        if(comments != null && !comments.isEmpty()) {
            model.addAttribute("comments",comments);
        }

        /* μ¬μ©μ κ΄λ ¨ */
        if(sessionUser != null){
            /* κ²μκΈ μμ±μ λ³ΈμΈμΈμ§ νμΈ */
            if(boardDTO.getUserId().equals(sessionUser.getId())) {
                model.addAttribute("iswriter",true);
            }else {
                model.addAttribute("iswriter",false);
            }

            /* λκΈ μμ±μ λ³ΈμΈμΈμ§ νμΈ */
            for (int i = 0; i < comments.size(); i++) {
                //λκΈ μμ±μ idμ νμ¬ μ¬μ©μ idλ₯Ό λΉκ΅
                boolean iswriterComment = comments.get(i).getUserId().equals(sessionUser.getId());
                model.addAttribute("iswriterComment", iswriterComment);
            }
        }
        /* νμ¬ μ°Έκ° μΈμ */
        Long joinUsers = recruitService.countToJoinUsers(boardId);
        model.addAttribute("joinUsers",joinUsers);
        model.addAttribute("boardDto",boardDTO);
        return "board/detail";
    }

    /* RETURN PAGE - κ²μκΈ μμ  νμ΄μ§ */
    @Operation(summary = "κ²μκΈ μμ  νμ΄μ§ λ°ν", description = "κ²μκΈ μμ  νλ©΄μΌλ‘ μ΄λν©λλ€.")
    @GetMapping("/edit/{boardId}")
    public String edit(@Parameter(description = "ν΄λΉ λ²νΈλ₯Ό κ°μ§ κ²μκΈμ μμ ν©λλ€.") @PathVariable("boardId") Long boardId, Model model,@Parameter(description = "νμ¬ λ‘κ·ΈμΈλ μ¬μ©μλ₯Ό μλ³") @LoginUser SessionUser sessionUser) {
        BoardDto.Response boardDTO = boardService.getPost(boardId);

        if( !boardDTO.getUserId().equals(sessionUser.getId()) ) {
            return "error/404error";
        }

        /* ν΄μνκ·Έ λ΄μ©λ§ Filter */
        StringBuilder sb = hashTagService.hashTagFilter(boardId);

        /* Html -> MarkDown */
        boardDTO.setContent(markDownService.convertHtmlToMarkDown(boardDTO.getContent()));

        model.addAttribute("boardDto",boardDTO);
        model.addAttribute("hashTags", sb);
        model.addAttribute("no", boardId);
        return "board/update";
    }

    /* READ - λ¬΄νμ€ν¬λ‘€ AJAX */
    @Operation(summary = "λ€μ νμ΄μ§μ κ²μκΈλ€μ λ°ν", description = "κ°μ Έμ¬ νμ΄μ§λ²νΈλ₯Ό λ°μ λͺ¨μ§μ€μΈ κ²μκΈλ€μ λ°νν©λλ€.")
    @GetMapping("/list-next/{page}/{isRecruitOn}")
    public ResponseEntity listJson(@Parameter(description = "κ°μ Έμ¬ κ²μκΈλ€μ νμ΄μ§ λ²νΈμλλ€.") @PathVariable("page") Integer pageNum,@Parameter(description = "λͺ¨μ§μ€μΈ κ²μκΈμ κ΅¬λΆνκΈ° μν νλΌλ―Έν°μλλ€.") @PathVariable("isRecruitOn") Boolean isRecruitOn) {
        List<BoardListVo> boardList = new ArrayList<>();
        if(isRecruitOn) { /* λͺ¨μ§μ€λ§ */
            boardList = boardService.getBoardListOnRecruit(pageNum);
        }else {           /* μ μ²΄ κ²μκΈ */
            boardList = boardService.getBoardlist(pageNum);
        }
        return ResponseEntity.ok(boardList);
    }

    /* CREATE - κΈμμ± */
    @Operation(summary = "κ²μκΈ μμ±", description = "μ κ· κ²μκΈμ λ±λ‘ν©λλ€.")
    @PostMapping("/write")
    public String write(@Parameter(description = "κ²μκΈμ μ λ³΄κ° λ΄κΈ΄ Request κ°μ²΄μλλ€.") @Valid BoardDto.Request boardDto, Errors errors , @Parameter(description = "νμ¬ λ‘κ·ΈμΈλ μ¬μ©μλ₯Ό μλ³")@LoginUser SessionUser sessionUser, Model model, @Parameter(description = "ν΄μνκ·Έμ μ λ³΄λ₯Ό String μΌλ‘ λ°μ΅λλ€. νμ λ¬Έμμ΄ νμ±μ ν΅ν΄ DBμ μ μ₯ν©λλ€.") @RequestParam(value = "tags", required = false) String tags) {
        /* κΈμμ± μ ν¨μ± κ²μ¬ */
        if(errors.hasErrors()) {
            /* κΈμμ± μ€ν¨μ μλ ₯ λ°μ΄ν° κ° μ μ§ */
            model.addAttribute("boardDto",boardDto) ;
            /* μ ν¨μ± ν΅κ³Ό λͺ»ν νλμ λ©μΈμ§λ₯Ό νΈλ€λ§ */
            model.addAttribute("error","μ λͺ©μ μλ ₯ν΄μ£ΌμΈμ.");
            return "board/write";
        }

        /* μΈλ€μΌ λΆμ¬μ λν΄νΈκ° μ€μ  */
        if (boardDto.getThumbnail().equals("") || boardDto.getThumbnail().equals(null)){
            boardDto.setThumbnail("/img/thumbnail.png");
        }
        boardDto.setWriter(sessionUser.getName());
        Long boardId = boardService.savePost(sessionUser.getName(),boardDto);

        /* ν΄μνκ·Έ μ μ₯ */
        hashTagService.hashTagSave(tags,boardId);

        return "redirect:/board/list";
    }

    /* UPDATE - κ²μκΈ μμ  */
    @Operation(summary = "κ²μκΈ μμ ", description = "κ²μκΈμ μμ  ν©λλ€. μμ  μ±κ³΅μ λͺ¨μ§νκΈ° νμ΄μ§λ‘ λ¦¬λ€μ΄λ νΈ λ©λλ€.")
    @PutMapping("/edit/{boardId}")
    public String update(@Parameter(description = "ν΄λΉ λ²νΈλ₯Ό κ°μ§ κ²μκΈμ μμ ν©λλ€.") @PathVariable("boardId") Long boardId, @Parameter(description = "μμ λ κ²μκΈμ μ λ³΄κ° λ΄κΈ΄ Request κ°μ²΄ μλλ€.") @Valid BoardDto.Request boardDto, @Parameter(description = "ν΄μνκ·Έμ μ λ³΄λ₯Ό String μΌλ‘ λ°μ΅λλ€. νμ λ¬Έμμ΄ νμ±μ ν΅ν΄ DBμ μ μ₯ν©λλ€.") @RequestParam(value = "tags",required = false) String tags ,@LoginUser SessionUser sessionUser) {
        if(!sessionUser.getId().equals(boardService.getPost(boardId).getUserId())) {
            return "error/404error";
        }

        boardDto.setWriter(sessionUser.getName());
        boardService.updatePost(boardId,boardDto);

        hashTagService.updateHashTag(tags,boardId);

        return "redirect:/board/list";
    }

    /* DELETE - κ²μκΈ μ­μ  */
    @Operation(summary = "κ²μκΈ μ­μ ", description = "κ²μκΈμ μ­μ ν©λλ€. μ­μ  μ±κ³΅μ λͺ¨μ§νκΈ° νμ΄μ§λ‘ λ¦¬λ€μ΄λ νΈ λ©λλ€.")
    @DeleteMapping("/{boardId}")
    public String delete(@Parameter(description = "ν΄λΉ λ²νΈλ₯Ό κ°μ§ κ²μκΈμ μ­μ ν©λλ€.") @PathVariable("boardId") Long boardId, @LoginUser SessionUser sessionUser) {
        if(!sessionUser.getId().equals(boardService.getPost(boardId).getUserId())){
            return "/error/404error";
        }

        boardService.deletePost(boardId);
        return "redirect:/board/list";
    }

    /* READ - κ²μ */
    @Operation(summary = "κ²μκΈ κ²μ", description = "κ²μκΈμ κ²μν©λλ€. λͺ¨μ§μ€μΈ κ²μκΈλ§ λ°νν©λλ€.")
    @GetMapping("/search")
    public String search(@Parameter(description = "κ²μν κ²μκΈλ€μ νμ΄μ§ λ²νΈμλλ€. κΈ°λ³Έκ°μΌλ‘λ 1 μλλ€.") @RequestParam(value = "page", defaultValue = "1") Integer pageNum , @Parameter(description = "κ²μν  ν€μλκ° λ΄κΈ΄ νλΌλ―Έν°μλλ€.") @RequestParam(value = "keyword") String keyword, Model model) {
        List<BoardListVo> boardDtoList = boardService.searchPosts(pageNum, keyword);
        model.addAttribute("boardList", boardDtoList);
        return "/board/list";
    }

    /* CREATE - μ€ν°λ μ°Έκ° */
    @Operation(summary = "μ€ν°λ μ°Έκ°", description = "μ€ν°λμ μ°Έκ°ν©λλ€. μλ΅μΌλ‘λ 200 , 400 μλλ€.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "μ°Έκ° μ±κ³΅μ κ²½μ° μλ΅μλλ€."),
            @ApiResponse(responseCode = "400", description = "μ°Έκ° μ€ν¨μ κ²½μ° μλ΅μλλ€."),
    })
    @PostMapping("/recruit/{boardId}/{userId}")
    public ResponseEntity recruitSave(@Parameter(description = "μ°Έκ°νλ κ²μκΈ λ²νΈμλλ€.") @PathVariable Long boardId, @Parameter(description = "μ°Έκ°νλ μ¬μ©μμ λ²νΈμλλ€.") @PathVariable Long userId, @LoginUser SessionUser sessionUser) {
        if (!sessionUser.getId().equals(userId)) {
            return ResponseEntity.badRequest().build();
        }

        RecruitDto.Request dto = new RecruitDto.Request();

        boolean isDuplicate = recruitService.isDuplicate(boardId,userId);
        if(isDuplicate) {
            return ResponseEntity.badRequest().body("μ΄λ―Έ μ μ²­νμμ΅λλ€.");
        }
        return ResponseEntity.ok(recruitService.Join(boardId,userId,dto));
    }

    /* DELETE - λͺ¨μ§ λ§κ° μ·¨μ */
    @DeleteMapping("/recruit-cancel/{boardId}/{userId}")
    @Operation(summary = "λͺ¨κ°μ½ λͺ¨μ§ λ§κ° μ·¨μ", description = "λͺ¨μ§ λ§κ°μ μ·¨μν©λλ€. κ²μκΈ μμ±μλ§ νΈμΆν μ μμ΅λλ€.")
    public ResponseEntity recruitDelete(@Parameter(description = "λͺ¨μ§ λ§κ°μ μ·¨μν  κ²μκΈμ λ²νΈμλλ€.") @PathVariable Long boardId, @Parameter(description = "λͺ¨μ§ μ·¨μλ₯Ό λλ₯Έ μ¬μ©μμ λ²νΈμλλ€.") @PathVariable Long userId, @LoginUser SessionUser sessionUser) {
        if(!sessionUser.getId().equals(userId)) {
            return ResponseEntity.badRequest().build();
        }

        int rows = recruitService.joinCancel(boardId,userId);
        int status = rows == 1 ? 200 : 400;
        return ResponseEntity.status(status).build();
    }

    /* UPDATE - λͺ¨μ§ λ§κ° */
    @Operation(summary = "λͺ¨κ°μ½ λͺ¨μ§μ λ§κ°", description = "λͺ¨κ°μ½ λͺ¨μ§μ λ§κ°ν©λλ€. κ²μκΈ μμ±μλ§ νΈμΆν μ μμ΅λλ€.")
    @PatchMapping("/recruit-off/{boardId}")
    public ResponseEntity recruitClose(@Parameter(description = "ν΄λΉ λ²νΈλ₯Ό κ°μ§ κ²μκΈμ λν΄ μμ²­ν©λλ€.") @PathVariable Long boardId, @LoginUser SessionUser sessionUser) {
        if(!sessionUser.getId().equals(boardService.getPost(boardId))) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(boardService.updateFull(boardId));
    }

}










