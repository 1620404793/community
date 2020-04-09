package com.lcu.community.commmunity.controller;

import com.lcu.community.commmunity.DTO.PageDTO;
import com.lcu.community.commmunity.DTO.QuestionDTO;
import com.lcu.community.commmunity.model.Question;
import com.lcu.community.commmunity.model.User;
import com.lcu.community.commmunity.service.IQuestionService;
import com.lcu.community.commmunity.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IQuestionService questionService;
    @GetMapping("/")  // /代表根路径
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(value = "page",defaultValue = "1") Integer page,
                        @RequestParam(value = "size",defaultValue = "6") Integer size){
        PageDTO pageDTO=questionService.queryByPageList(page,size);
        model.addAttribute("pageDTO",pageDTO);
        return "index";
    }
}
