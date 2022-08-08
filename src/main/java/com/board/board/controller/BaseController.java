package com.board.board.controller;

import com.board.board.config.auth.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class BaseController {
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        //Optional 으로 변경 예정
        if(user != null) {
            model.addAttribute("userName",user.getName());
            model.addAttribute("userImg",user.getPicture());
        }

        return "index";
    }
}
