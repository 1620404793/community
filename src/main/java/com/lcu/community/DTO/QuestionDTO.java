package com.lcu.community.commmunity.DTO;

import com.lcu.community.commmunity.model.Question;
import com.lcu.community.commmunity.model.User;

public class QuestionDTO extends Question {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
