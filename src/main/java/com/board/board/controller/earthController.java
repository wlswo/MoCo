package com.board.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashSet;
import java.util.Set;

@Controller
public class earthController {

    @GetMapping("/earth")
    public String earthPage(Model model) {
        Set<String> dotList = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            dotList.add(Integer.toString(i));
        }
        model.addAttribute("dotList",dotList);
        return "earth/earth";
    }
}
