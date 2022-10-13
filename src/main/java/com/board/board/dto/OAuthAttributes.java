package com.board.board.dto;

import com.board.board.domain.Role;
import com.board.board.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes,
                           String nameAttributeKey,
                           String name,
                           String email,
                           String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId,String userNameAttributeName, Map<String, Object> attributes) {

        switch (registrationId) {
            case "naver":
                return ofNaver("id",attributes);
            case "kakao":
                return ofKakao("id",attributes);
            case "github":
                return  ofGithub("id",attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture("/img/userIcon.png")
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public static OAuthAttributes ofGithub(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("name")+"@github.com")
                .picture("/img/userIcon.png")
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture("/img/userIcon.png")
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        //kakao는 kakao_account에 유저정보가 있다(email)
        Map<String,Object> kakaoAcount = (Map<String, Object>) attributes.get("kakao_account");
        //kakao_account안에 또 profile이라는 JSON객체가 있다. (nickname, profile_image,)
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAcount.get("profile");
        return OAuthAttributes.builder()
                .name((String) kakaoProfile.get("nickname"))
                .email((String) kakaoAcount.get("email"))
                .picture("/img/userIcon.png")
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    /* 최초가입시 */
    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .namecheck("false")
                .role(Role.SNS)
                .build();
    }
}
