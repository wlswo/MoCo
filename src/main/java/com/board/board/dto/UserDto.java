package com.board.board.dto;

import com.board.board.domain.Role;
import com.board.board.domain.User;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDto { //홈페이지로 가입하는 사용자

    private String name;
    private String password;
    private String email;
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
