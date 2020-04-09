package com.lcu.community.commmunity.service;

import com.lcu.community.commmunity.model.User;

public interface IUserService {
    public void insert(User user);

    User findUserByToken(String token);

    void createOrUpdate(User user);
}
