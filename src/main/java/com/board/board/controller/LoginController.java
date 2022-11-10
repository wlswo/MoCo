package com.board.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @SuppressWarnings("unchecked") //loginPage 경로 설정
    @GetMapping("/login")
    public String getLoginPage(@RequestParam(value = "error", required = false) String error,
                               @RequestParam(value = "exception", required = false) String exception,
                               @RequestParam(value = "id", required = false) String id,
                               Model model) throws Exception {

        model.addAttribute("error",error);
        model.addAttribute("exception",exception);
        model.addAttribute("id", id);
        return "login/login";
    }

    @GetMapping("/OauthNameCheck")
    public String nameCheck() {
        return "login/OauthNameCheck";

    }

}