package com.board.board.controller;

import com.board.board.config.LoginUser;
import com.board.board.config.auth.SessionUser;
import com.board.board.dto.DotMapDto;
import com.board.board.service.dotmap.DotMapService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@AllArgsConstructor
@Controller
public class earthController {

    private final DotMapService dotMapService;

    @GetMapping("/earth")
    public String earthPage(Model model) {
        List<DotMapDto.Response> dotList = dotMapService.getDotMapInfo();
        model.addAttribute("dotList",dotList);
        return "earth/earth";
    }

    @PostMapping("/earth/buy/{userid}")
    public ResponseEntity buyDot(@PathVariable Long userid ,@RequestBody DotMapDto.Request dotDto,@LoginUser SessionUser sessionUser) {

        if(!userid.equals(sessionUser.getId())) {
            return ResponseEntity.status(400).build();
        }
        return ResponseEntity.ok(dotMapService.saveDot(dotDto,userid));
    }
}
