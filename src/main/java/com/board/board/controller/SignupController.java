package com.board.board.controller;


import com.board.board.dto.UserDto;
import com.board.board.service.CustomUserDetailsService;
import com.board.board.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class SignupController {
    private CustomUserDetailsService customUserDetailsService;
    private UserService userService;

    //회원가입 페이지
    @GetMapping("/signup")
    public String sign_up_page() {
        return "/login/signup";
    }

    //회원가입 처리
    @PostMapping("/login/signup")
    public String execSignup(UserDto userDto) {
        System.out.println(userDto.getEmail());
        userService.join(userDto);
        return "redirect:/login/login";
    }

}
