package com.board.board.controller;

import com.board.board.config.LoginUser;
import com.board.board.config.auth.SessionUser;
import com.board.board.domain.User;
import com.board.board.dto.BoardListVo;
import com.board.board.service.board.BoardService;
import com.board.board.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Controller
@RequestMapping("profile")
public class ProfileController {

    private final UserService userService;
    private final BoardService boardService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String ProfilePage() { return "profile/profile"; }

    @PatchMapping("/change/{nickname}")
    public ResponseEntity ChangeNickname(@PathVariable("nickname") String nickname,Long userid) {
        if(userService.checkUsernameDuplication(nickname)){
            return ResponseEntity.status(400).build();
        }

        userService.nameUpdateInSetting(userid, nickname);

        return ResponseEntity.ok("ok");
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteUser(@LoginUser SessionUser sessionUser) {
        userService.deleteUser(sessionUser.getId());
        return ResponseEntity.ok("탈퇴완료");
    }

    @GetMapping("/mypost")
    public String mypost(@RequestParam(value = "page", defaultValue = "1") Integer pageNum, @LoginUser SessionUser sessionUser,Model model) {
        List<BoardListVo> boardList = boardService.getMyBoardList(pageNum,sessionUser.getId());
        Integer totalPage = boardService.getPageList(pageNum);

        model.addAttribute("boardList",boardList);
        model.addAttribute("totalPage",totalPage);

        return "profile/mypost";
    }
    /* 무한스크롤 AJAX */
    @GetMapping("/MyListJson/{page}/{userId}")
    public ResponseEntity listJson(@PathVariable("page") Integer pageNum, @PathVariable("userId") Long userId) {
        List<BoardListVo>  boardList = boardService.getMyBoardList(pageNum,userId);
        return ResponseEntity.ok(boardList);
    }
}
