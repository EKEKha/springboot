package com.codingrecipe.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class WebController {


    //favicon.ico 경로 무시
    @GetMapping("favicon.ico")
    @ResponseBody
    public void returnNoFavicon() {
    }
}
