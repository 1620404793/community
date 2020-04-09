package com.lcu.community.commmunity.controller;

import com.lcu.community.commmunity.DTO.AccesstokenDTO;
import com.lcu.community.commmunity.DTO.GitHubUser;
import com.lcu.community.commmunity.model.User;
import com.lcu.community.commmunity.provider.GitHubProvider;
import com.lcu.community.commmunity.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private IUserService userService;
    @Autowired
    private GitHubProvider gitHubProvider;
    @Value("${github.client.id}")
    private String client_id;
    @Value("${github.client.secret}")
    private String client_secret;
    @Value("${github.redirect_uri}")
    private String redirect_uri;
    @GetMapping("callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest request, HttpServletResponse response) {
        AccesstokenDTO accesstokenDTO = new AccesstokenDTO();
        accesstokenDTO.setClient_id(client_id);
        accesstokenDTO.setClient_secret(client_secret);
        accesstokenDTO.setCode(code);
        accesstokenDTO.setRedirect_uri(redirect_uri);
        accesstokenDTO.setState(state);
        String accessToken = gitHubProvider.getAccessToken(accesstokenDTO);
        GitHubUser gituser = gitHubProvider.getUser(accessToken);
        System.out.println(gituser);
        if(gituser!=null){
            User user = new User();
            user.setName(gituser.getName());
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setAccountId(String.valueOf(gituser.getId()));
            user.setAvatarUrl(gituser.getAvatar_url());
            userService.createOrUpdate(user);
           // userService.insert(user);
            //写入cookie
            response.addCookie(new Cookie("token",token));

            return "redirect:/";
        }else {
            //登陆失败
            return "redirect:/";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        //移除session
        request.getSession().removeAttribute("user");
        //消除cookie
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
