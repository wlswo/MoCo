package com.board.board.controller;

import com.board.board.config.LoginUser;
import com.board.board.config.auth.SessionUser;
import com.board.board.domain.User;
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

import java.security.Security;


@AllArgsConstructor
@Controller
@RequestMapping("profile")
public class ProfileController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/")
    public String ProfilePage() { return "profile/profile"; }

    @PatchMapping("/change/{nickname}")
    public ResponseEntity ChangeNickname(@PathVariable("nickname") String nickname,Long userid) {
        if(userService.checkUsernameDuplication(nickname)){
            return ResponseEntity.status(400).build();
        }

        User user =  userService.nameUpdateInSetting(userid, nickname);
        /* 수정된 정보로 세션 재할당 */
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok("ok");
    }
}
