package com.board.board.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    /*
        enum 타입 : 열거 타입 이라고 하며 서로 연관된 상수의 집합을 저장하는 자료형 , ex) 요일 , 계절 등등
     */
    SNS("ROLE_SNS","SNS 사용자"),
    USER("ROLE_USER", "일반 사용자");

    private final String key;
    private final String title;
}
