package com.board.board.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED) //외부에서의 생성을 열어 둘 필요가 없을 때 , 보안적으로 권장됨
public class User extends Time {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)    //id
    private String email;

    @Column(nullable = true)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String picture;

    @Column(nullable = false)
    private String namecheck;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String name, String password, String email, String picture,String namecheck, Role role) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.picture = picture;
        this.namecheck = namecheck;
        this.role = role;
    }
    /* Oauth 로그인 갱신 날짜 갱신 */
    public User update(String picture) {
        this.picture = picture;
        return this;
    }

    /* 첫 소셜 로그인시 별명 중복검사 시키기 */
    public User updateName(String name,String picture) {
        this.name = name;
        this.picture = picture;
        return this;
    }

    /* 별명 체크 여부 */
    public void isNameCheck() {
        this.namecheck = "true";
    }

    /* 권한 타입 가져오기 */
    public String getRoleKey() {
        return this.role.getKey();
    }
}
