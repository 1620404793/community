package com.lcu.community.commmunity.controller;

import com.lcu.community.commmunity.DTO.PageDTO;
import com.lcu.community.commmunity.model.User;
import com.lcu.community.commmunity.service.IQuestionService;
import com.lcu.community.commmunity.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IQuestionService questionService;
    @GetMapping("/profile/{action}")//可以实现动态的切换路径
    public  String profile(@PathVariable(name = "action") String action,
                           HttpServletRequest request,
                           @RequestParam(value = "page",defaultValue = "1") Integer page,
                           @RequestParam(value = "size",defaultValue = "6") Integer size,
                           Model model){
        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            return "redirect:/";
        }
        if (action.equals("questions")){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
        }else if (action.equals("replies")){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }
        PageDTO pageDTO = questionService.queryListByQuestionId(user.getId(), page, size);
        model.addAttribute("pageDTO",pageDTO);
        return "profile";
    }
}
