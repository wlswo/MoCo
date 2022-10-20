package com.board.board.dto;

import com.board.board.domain.Role;
import com.board.board.domain.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


public class UserDto { //홈페이지로 가입하는 사용자

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public  static class Request{
        @NotBlank(message = "아이디는 필수 입력 값입니다.")
        private String email;

        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        private String password;

        @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
        private String name;
        private String picture;
        private Role role;

        /* DTO -> Entity */
        public User toEntity() {
            return User
                    .builder()
                    .name(name)
                    .password(password)
                    .email(email)
                    .picture(picture)
                    .role(Role.USER)
                    .namecheck(true)
                    .build();
        }
    }
    @Getter
    public static class Response {
        private String email;
        private String password;
        private String name;
        private String picture;
        private Role role;

        /* Entity -> Dto */
        public Response(User user) {
            this.email = user.getEmail();
            this.name = user.getName();
            this.picture = user.getPicture();
            this.password = user.getPassword();
            this.role = user.getRole();
        }
    }
}
