package com.board.board.controller;

import com.board.board.config.LoginUser;
import com.board.board.config.auth.SessionUser;
import com.board.board.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ResolvableType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class LoginController {

    private static final String authorizationRequestBaseUri = "oauth2/authorization";

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