package com.example.testsecurity.controller;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.Iterator;

@Controller
public class MainController {

    @GetMapping("/")
    public String mainP(Model model){

//        사용자가 로그인 후 사용자 정보는 SecurityContextHolder에 의해서 서버 세션에 관리된다. 세션ID 는 사용자의 쿠키에 저장,  (jwt  의 경우  세션이  stateless ...)

         String id = SecurityContextHolder.getContext().getAuthentication().getName();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

         model.addAttribute("id", id);
         model.addAttribute("role", role);
        return "main";
    }
}
