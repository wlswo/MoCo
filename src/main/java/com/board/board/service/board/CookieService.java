package com.board.board.service.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Service
public class CookieService {
    private final BoardService boardService;

    public void cookieAndView(HttpServletRequest request, HttpServletResponse response, Long boardId) {
        /* 쿠키 관련 */
        Cookie oldCookie = null;
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("viewCookie")) {
                    oldCookie = cookie;
                }
            }
        }
        if (oldCookie != null) {
            if (!oldCookie.getValue().contains("[" + boardId.toString() + "]")) {
                boardService.updateView(boardId); /* 조회수++ */
                oldCookie.setValue(oldCookie.getValue() + "[" + boardId + "]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60 * 60 * 24); /* 유효시간 */
                response.addCookie(oldCookie);
            }
        }else {
            Cookie newCookie = new Cookie("viewCookie", "[" + boardId + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 2);
            response.addCookie(newCookie);
            boardService.updateView(boardId); /* 조회수++ */
        }
    }
}
