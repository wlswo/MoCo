package com.board.board.service;

import com.board.board.domain.User;
import com.board.board.dto.UserDto;
import com.board.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    /* 회원가입 */
    @Transactional
    public User join(UserDto userDto){
        userDto.setPassword(encoder.encode(userDto.getPassword()));
        return userRepository.save(userDto.toEntity());
    }
    /* 회원가입 시 유효성 체크 */
    @Transactional(readOnly = true)
    public Map<String,String> validateHandling(Errors errors){
        Map<String,String> validatorResult = new HashMap<>();

        /* 유효성 검사에 실패한 필드 목록을 받음 */
        for(FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }

    /* 회원가입시 이메일 중복 여부 */
    @Transactional(readOnly = true)
    public boolean checkUseremailDuplication(String email) {
        boolean useremailDuplication = userRepository.existsByEmail(email);

        //중복 = true
        return useremailDuplication;
    }

    /* 회원가입시 별명 중복 여부 */
    @Transactional(readOnly = true)
    public boolean checkUsernameDuplication(String name) {
        boolean usernameDuplication = userRepository.existsByName(name);

        //중복 = true
        return usernameDuplication;
    }

    @Transactional
    public User nameUpdate(String email, String name, String picture) {
        User user = userRepository.findByEmail(email)
                .map(entity -> entity.updateName(name, picture))
                .orElseThrow(()-> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        user.isNameCheck();

        return user;
    }

}
