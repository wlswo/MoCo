package com.board.board.dto;

import com.board.board.domain.Role;
import com.board.board.domain.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDto { //홈페이지로 가입하는 사용자

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
                .role(Role.USER)
                .build();
    }

}
