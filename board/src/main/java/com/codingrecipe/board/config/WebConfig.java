package com.codingrecipe.board.config;

import com.codingrecipe.board.util.CustomObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {




    private final CustomObjectMapper customObjectMapper;



    /*
*   web.resources.add-mappings: false
*  정적 파일의 URL 접근 비활성 , 아래 스프링컨트롤러에서 직접 경로 처리
  기본 true : 존재하지 않는 url 요청일경우 정적 리소스를 먼저 검색, 이로인해 404예외를 spring의 예외 핸들러 (NoHandlerFoundException)에서 제대로 처리 못해줄수있음.
* 핸들러가 없다고 판단하기때문에 그대로 404 에러 처리되므로 @ExceptionHandler 호출되지않음
*
* */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/static/**")
//                .addResourceLocations("classpath:/static/");

            /* '/js/**'로 호출하는 자원은 '/static/js/' 폴더 아래에서 찾는다. */
            registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/").setCachePeriod(60 * 60 * 24 * 365);
            /* '/css/**'로 호출하는 자원은 '/static/css/' 폴더 아래에서 찾는다. */
            registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/").setCachePeriod(60 * 60 * 24 * 365);
            /* '/img/**'로 호출하는 자원은 '/static/img/' 폴더 아래에서 찾는다. */
            registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/").setCachePeriod(60 * 60 * 24 * 365);
            /* '/font/**'로 호출하는 자원은 '/static/font/' 폴더 아래에서 찾는다. */
            registry.addResourceHandler("/font/**").addResourceLocations("classpath:/static/font/").setCachePeriod(60 * 60 * 24 * 365);
            /* '/html/**'로 호출하는 자원은 '/static/html/' 폴더 아래에서 찾는다. */
            registry.addResourceHandler("/html/**").addResourceLocations("classpath:/static/html/").setCachePeriod(60 * 60 * 24 * 365);


        }


    // Spring 서버  CORS 설정
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // CORS 를 적용할  URL  패턴
                .allowedOriginPatterns("*") // “*“같은 와일드카드를 사용, 자원 공유를 허락할  origin 지정
                //.allowedOrigins("http://localhost:8080", "http://localhost:8081"), *추가할수없고 특정도메인만 받을수있음
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP method
                .allowCredentials(true)
                .allowedHeaders("*"); // 쿠키 인증 요청 허용
    }



    //
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {


        converters.add(new MappingJackson2HttpMessageConverter(customObjectMapper));  // JSON 메세지 컨버터 추가
        //converters.add(new MappingJackson2XmlHttpMessageConverter()); // XML 처리


    }


}