package com.codingrecipe.board.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {


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

}