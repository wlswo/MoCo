package com.board.board.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    SNS("ROLE_SNS","SNS 사용자"),
    USER("ROLE_USER", "일반 사용자"),
    MASTER("ROLE_MASTER","운영자");

    private final String key;
    private final String title;
}
