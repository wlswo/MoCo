package com.board.board.config;

import com.board.board.service.CustomOAuth2UserService;
import com.board.board.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;


@RequiredArgsConstructor
@EnableWebSecurity
@ConditionalOnDefaultWebSecurity
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomUserDetailsService customUserDetailsService;
    /* 로그인 실패 핸들러 의존성 주입 */
    private final AuthenticationFailureHandler  customFailureHandler;

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(customUserDetailsService).passwordEncoder(encoder());
    }


    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/", "/Savory-gh-pages/**").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/", "/css/**", "/img/**", "/js/**", "/h2/**", "/h2-console/**", "/favicon.ico").permitAll()
                .antMatchers("/notice").permitAll()
                .antMatchers("/board").permitAll()
                .antMatchers("/signup","/login/signup").permitAll()
                .antMatchers("/id/check").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .and()
                .formLogin()
                .loginPage("/login")                  //form 기반으로 인증 진입점
                .loginProcessingUrl("/loginProcess")
                .failureHandler(customFailureHandler).usernameParameter("username") //로그인 실패 핸들러
                .defaultSuccessUrl("/")
                .usernameParameter("username")
                .and()
                .logout().logoutSuccessUrl("/")
                .and()
                .oauth2Login().userInfoEndpoint().userService(customOAuth2UserService);

        return http.build();
    }
}

/*
@EnableWebSecurity
- Spring Security 설정들을 활성화하기 위한 애노테이션이다.

.csrf().disable().headers().frameOptions().disable()
- h2-console 화면을 사용하기 위해 해당 옵션들을 disable

authorizeRequests
- URL별 권한 관리를 설정하는 옵션의 시작점
- authorizeRequests가 선언되어야만 antMatchers 옵션을 사용이 가능하다.

antMatchers
- 권한 관리 대상을 지정하는 옵션이다.
- URL, HTTP 메서드별로 관리가 가능하다.

.anyRequest().authenticated()
- 설정된 값을 이외 나머지 URL들을 나타낸다.
- authenticated() 메서드를 통해 나머지 URL들은 모두 인증된 사용자에게만 허용하게 한다.

.oauth2Login().userInfoEndpoint().userService(customOAuth2UserService);
- OAuth2 로그인 기능에 대한 여러 설정의 진입점
- userInfoEndpoint : OAuth2 로그인이 성공된 이후 사용자 정보를 가져올 때 설정을 담당한다.
- userService
  : 소셜 로그인 성공 시 후속 조치를 진행 할 UserService 인터페이스의 구현체를 등록한다.
  : 서비스 제공자(Google, Kakao, ..)에서 사용자 정보를 가져온 정보 (Email, Name, Picture, ..)을 기반으로 추가로 진행하고자 하는 기닝들을 명시할 수 있다.
  (예를 들어, 해당 정보를 가지고 우리 웹 서비스의 DB에 사용자들을 저장한다든지 등등..)
 */