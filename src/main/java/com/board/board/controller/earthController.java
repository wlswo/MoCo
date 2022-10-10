package com.board.board.controller;

import com.board.board.service.dotmap.DotMapService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Controller
public class earthController {

    private final DotMapService dotMapService;

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
