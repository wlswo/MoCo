package com.board.board.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi boardApi() {
        String[] paths = {"/board/**"};
        return GroupedOpenApi.builder()
                .group("게시판API")
                .pathsToMatch(paths)
                .build();
    }
    @Bean
    public GroupedOpenApi commentApi() {
        String[] paths = {"/comment/**"};
        return GroupedOpenApi.builder()
                .group("댓글API")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public GroupedOpenApi likeApi() {
        String[] paths = {"/like/**"};
        return GroupedOpenApi.builder()
                .group("좋아요API")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public OpenAPI springBoardOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("MoCo API")
                        .description("MoCo 프로젝트 API 설명서 입니다.")
                        .version("v0.0.1"));
    }
}