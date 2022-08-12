package com.board.board.controller;


import com.board.board.config.auth.BadRequestExection;
import com.board.board.config.auth.CheckUseremailValidator;
import com.board.board.dto.UserDto;
import com.board.board.service.CustomUserDetailsService;
import com.board.board.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.util.Map;

@Controller
@AllArgsConstructor
public class SignupController {
    private CustomUserDetailsService customUserDetailsService;
    private UserService userService;
    private final CheckUseremailValidator checkUseremailValidator;

    /* 커스텀 유효성 검증을 위해 추가 */
    @InitBinder //: 특정 컨트롤러에서 바인딩 또는 검증 설정을 변경하고 싶을 때 사용
    public void validatorBinder(WebDataBinder binder) {  //WebDataBinder : Http 요청 정보를 컨트롤러 메소드의 파라미터나 모델에 바인딩할 때 사용되는 바인딩 객체
        binder.addValidators(checkUseremailValidator);
    }


    //회원가입 페이지
    @GetMapping("/signup")
    public String sign_up_page(Model model) {
        model.addAttribute("userDto",new UserDto()); //빈객체 전달
        return "/login/signup";
    }

    //회원가입화면 아이디 중복확인
    @GetMapping("/id/check")
    public ResponseEntity<?> checkEmailDuplication(@RequestParam(value = "email") String email) throws BadRequestExection {

        if(userService.checkUseremailDuplication(email)) {
            throw new BadRequestExection("이미 사용중인 아이디 입니다.");
        }

        return ResponseEntity.ok("사용 가능한 아이디 입니다.");
    }


    //회원가입 처리
    @PostMapping("/login/signup")
    public String execSignup(@Valid UserDto userDto, Errors errors, Model model) {

        if(errors.hasErrors()) {
            /* 회원가입 실패시 입력 데이터 값을 유지 */
            model.addAttribute("userDto", userDto);

            /* 유효성 통과 못한 필드와 메세지를 핸들링 */
            Map<String, String> validatorResult = userService.validateHandling(errors);
            for (String key :  validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }

            /* 회원가입 페이지로 다시 리턴 */
            return "/login/signup";
        }

        userService.join(userDto);
        return "redirect:/login/login";
    }

}
