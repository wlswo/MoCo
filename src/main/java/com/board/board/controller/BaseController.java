package com.board.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class BaseController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
