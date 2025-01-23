package com.example.jwtsecurity.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class AdminController {

    @RequestMapping("/admin")
    public String adminP(){

        return "admin Controller";
    }
}
