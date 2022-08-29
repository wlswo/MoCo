package com.board.board.controller;


import com.board.board.config.LoginUser;
import com.board.board.config.auth.CustomAuthFailureHandler;
import com.board.board.config.auth.CheckUseremailValidator;
import com.board.board.config.auth.SessionUser;
import com.board.board.domain.User;
import com.board.board.dto.UserDto;
import com.board.board.service.mail.ConfirmationTokenService;
import com.board.board.service.user.CustomUserDetailsService;
import com.board.board.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

@Controller
@AllArgsConstructor
public class SignupController {
    private CustomUserDetailsService customUserDetailsService;
    private UserService userService;
    private final CheckUseremailValidator checkUseremailValidator;
    private final ConfirmationTokenService confirmationTokenService;
    private final HttpSession httpSession;

    /* 커스텀 유효성 검증을 위해 추가 */
    @InitBinder //: 특정 컨트롤러에서 바인딩 또는 검증 설정을 변경하고 싶을 때 사용
    public void validatorBinder(WebDataBinder binder) {  //WebDataBinder : Http 요청 정보를 컨트롤러 메소드의 파라미터나 모델에 바인딩할 때 사용되는 바인딩 객체
        binder.addValidators(checkUseremailValidator);
    }


    /* 회원가입 페이지 */
    @GetMapping("/signup")
    public String sign_up_page(Model model) {
        model.addAttribute("userDto",new UserDto.Request()); //빈객체 전달
        return "/login/signup";
    }

    /* 회원가입화면 아이디 중복확인 (email) */
    @GetMapping("/id/check")
    public ResponseEntity<?> checkEmailDuplication(@RequestParam(value = "email") String email) throws CustomAuthFailureHandler.BadRequestExection {
        if(userService.checkUseremailDuplication(email)) {
            throw new CustomAuthFailureHandler.BadRequestExection("이미 사용중인 아이디 입니다.");
        }

        return ResponseEntity.ok("사용 가능한 아이디 입니다.");
    }

    /* 별명 중복 체크 */
    @GetMapping("/name/check")
    public ResponseEntity<?> checkNameDupulication(@RequestParam(value = "nickname") String name) throws CustomAuthFailureHandler.BadRequestExection {
        if(userService.checkUsernameDuplication(name)) {
            throw new CustomAuthFailureHandler.BadRequestExection("이미 사용중인 별명 입니다.");
        }

        return ResponseEntity.ok("사용 가능한 별명 입니다.");
    }


    /* 일반사용자 회원가입 처리 */
    @PostMapping("/login/signup")
    public String execSignup(@Valid UserDto.Request userDto, Errors errors, Model model) {
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
        /* 회원저장 */
        userService.join(userDto);
        /* 이메일 발송 */
        confirmationTokenService.createEmailConfirmationToken(userDto.getEmail(),userDto.getEmail());

        return "redirect:/login";
    }
    /* SNS로그인 사용자 회원가입 처리 (실제로는 이름만 Update) */
    @GetMapping("/signup/name/edit")
    public String nickNameUpdate(@RequestParam String name, @LoginUser SessionUser sessionUser,Model model) {
        String email = sessionUser.getEmail();
        String picture = sessionUser.getPicture();

        /* 별명 중복 가능성 */
        if(userService.checkUsernameDuplication(name)) {
            model.addAttribute("error","이미 존재하는 별명입니다.");
            model.addAttribute("email",email);
            model.addAttribute("nickname",name);
            return "login/OauthNameCheck";
        }

        User user = userService.nameUpdate(email,name,picture);
        httpSession.setAttribute("user", new SessionUser(user)); // SessionUser (직렬화된 dto 클래스 사용)

        return "redirect:/";
    }

    /* 이메일 인증관련 */
    @GetMapping("/confirm-email")
    public String viewConfirmEmail(@Valid @RequestParam String token) {
        /* 이메일 인증 컬럼 Update */
        userService.confirmEmail(token);
        return "redirect:/login";
    }

}
