package com.board.board.controller;

import com.board.board.config.LoginUser;
import com.board.board.config.auth.SessionUser;
import com.board.board.domain.User;
import com.board.board.dto.BoardListVo;
import com.board.board.service.aws.AwsS3Service;
import com.board.board.service.board.BoardService;
import com.board.board.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;


@AllArgsConstructor
@Controller
@RequestMapping("profile")
public class ProfileController {

    private final UserService userService;
    private final BoardService boardService;
    private final AwsS3Service awsS3Service;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String ProfilePage() { return "profile/profile"; }

    @PostMapping("/change/{nickname}")
    public ResponseEntity ChangeProfile(@PathVariable("nickname") String nickname, @RequestParam(value = "image" ,required = false) MultipartFile multipartFile,@LoginUser SessionUser sessionUser) {

        /* 프로필 사진만 바꾼 경우 */
        if(sessionUser.getName().equals(nickname)) {
            if (multipartFile != null && !multipartFile.isEmpty()) {
                User user = userService.profileUpdateInSetting(sessionUser.getId(),awsS3Service.uploadImage(multipartFile));
                httpSession.setAttribute("user",new SessionUser(user));
            }
            return ResponseEntity.ok("ok");
        }
        /* 별명이 바뀐경우 */
        else {
            /* 이름중복검사 */
            if(userService.checkUsernameDuplication(nickname)){
                return ResponseEntity.status(400).build();
            }
            /* 프로필도 바뀌었는지 */
            if( multipartFile != null && !multipartFile.isEmpty() ){
                userService.profileUpdateInSetting(sessionUser.getId(),awsS3Service.uploadImage(multipartFile));
            }
            User user = userService.nameUpdateInSetting(sessionUser.getId(), nickname);
            httpSession.setAttribute("user", new SessionUser(user));
        }
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
