package com.lcu.community.commmunity.provider;

import com.alibaba.fastjson.JSON;
import com.lcu.community.commmunity.DTO.AccesstokenDTO;
import com.lcu.community.commmunity.DTO.GitHubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class GitHubProvider {
    public String getAccessToken(AccesstokenDTO accesstokenDTO){
        MediaType JSONTYPE = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        //把类对象转化成json格式
        String json = JSON.toJSONString(accesstokenDTO);
        RequestBody body = RequestBody.create(json, JSONTYPE);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string=response.body().string();
            //System.out.println(string);
            String[] split = string.split("&");
            String tokenstr=split[0];
            String accessToken=tokenstr.split("=")[1];
            //System.out.println("accessToken:"+accessToken);
            return accessToken;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public GitHubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            //把spring的json对象解析为java的类对象
            //System.out.println(string+"user");    不能自动装配小驼峰和横线连接，需要在配置文件里配置
            GitHubUser gitHubUser = JSON.parseObject(string, GitHubUser.class);
            return gitHubUser;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
