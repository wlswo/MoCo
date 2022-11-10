package com.board.board.controller;

import com.board.board.config.LoginUser;
import com.board.board.config.auth.SessionUser;
import com.board.board.domain.User;
import com.board.board.dto.BoardListVo;
import com.board.board.service.aws.AwsS3Service;
import com.board.board.service.board.BoardService;
import com.board.board.service.profile.ProfileService;
import com.board.board.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
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
    private final ProfileService profileService;

    @Operation(summary = "페이지 반환", description = "회원정보 페이지를 반환합니다.")
    @GetMapping("/")
    public String ProfilePage() { return "profile/profile"; }

    @Operation(summary = "회원 정보 수정", description = "회원 프로필과 이름변경 여부를 검사해 변경합니다.")
    @PostMapping("/{name}/{isImgDelete}")
    public ResponseEntity ChangeProfile(@PathVariable("name") String name, @RequestParam(value = "image" ,required = false) MultipartFile multipartFile, @PathVariable("isImgDelete") boolean isImgDelete, @LoginUser SessionUser sessionUser) {
        Long status = profileService.CheckProfileAndChange(sessionUser,name,multipartFile,isImgDelete);
        return status == 200L ? ResponseEntity.ok("ok") : ResponseEntity.badRequest().build();
    }

    @Operation(summary = "회원 탈퇴 요청", description = "회원 탈퇴를 요청합니다.")
    @DeleteMapping("/delete")
    public ResponseEntity deleteUser(@LoginUser SessionUser sessionUser) {
        userService.deleteUser(sessionUser.getId());
        return ResponseEntity.ok("탈퇴완료");
    }

    @Operation(summary = "게시글 리스트 요청", description = "자신이 작성한 게시글들을 반환합니다.")
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
