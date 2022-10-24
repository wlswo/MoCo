package com.board.board.config.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Component
public class CustomAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /* 로그인 인증실패 분기 */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String email = request.getParameter("username");

        String errorMessage;
        if(exception instanceof BadCredentialsException) {
            errorMessage = "아이디 또는 비밀번호가 틀렸습니다.";
        }else if (exception instanceof InternalAuthenticationServiceException) {
            errorMessage = "내부적으로 발생한 시스템 문제로 인해 요청을 처리할 수 없습니다.";
        } else if(exception instanceof UsernameNotFoundException) {
            errorMessage = "계정이 존재하지 않습니다.";
        } else if (exception instanceof AuthenticationCredentialsNotFoundException) {
            errorMessage = "인증 요청이 거부되었습니다. 관리자에게 문의하세요.";
        } else if (exception instanceof DisabledException) {
            errorMessage = "알수 없는 에러가 발생했습니다.";
        } else {
            errorMessage = "이메일인증이 필요합니다.";
        }
        errorMessage = URLEncoder.encode(errorMessage,"UTF-8");
        logger.info(errorMessage);
        setDefaultFailureUrl("/login?error=true&exception="+errorMessage+"&id="+email);
        super.onAuthenticationFailure(request, response, exception);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    public static class BadRequestExection extends RuntimeException{
        public BadRequestExection(String message) {
            super(message);
        }
    }
}
