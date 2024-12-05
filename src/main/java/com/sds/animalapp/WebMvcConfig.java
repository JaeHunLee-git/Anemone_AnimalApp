package com.sds.animalapp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
정적인 리소스에 대한 요청을 처리하는 핸들러, 정적 파일들의 경로를 잡아주는 메서드이다!
내가 설정한 내용은,
addResourceHandler에 정의한 루트로 들어오는 모든 정적 리소스 요청을
addResourceLocations에서 정의한 경로에서 찾는다는 의미이다.
*/
@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");

        registry.addResourceHandler("/mypage")
                .addResourceLocations("file:src/main/resources/static/");
    }
}