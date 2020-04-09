package com.lcu.community.commmunity.service.impl;

import com.lcu.community.commmunity.mapper.UserMapper;
import com.lcu.community.commmunity.model.User;
import com.lcu.community.commmunity.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public void insert(User user) {
        userMapper.insert(user);
    }

    @Override
    public User findUserByToken(String token) {
        return userMapper.findUserByToken(token);
    }

    @Override
    public void createOrUpdate(User user) {
        User dbUser=userMapper.findUserByAccountId(user.getAccountId());
        if (dbUser==null){
            //插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else{
            //更新
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());
            System.out.println("更新中的方法"+user.getToken());
            userMapper.update(dbUser);
        }
    }
}
