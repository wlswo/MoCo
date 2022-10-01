package com.board.board.controller;

import com.board.board.config.LoginUser;
import com.board.board.config.auth.SessionUser;
import com.board.board.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@Controller
@RequestMapping("profile")
public class ProfileController {

    private final UserService userService;

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
}
