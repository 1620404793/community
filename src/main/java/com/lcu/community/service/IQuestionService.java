package com.lcu.community.commmunity.service;

import com.lcu.community.commmunity.DTO.PageDTO;
import com.lcu.community.commmunity.DTO.QuestionDTO;
import com.lcu.community.commmunity.model.Question;

import java.util.List;

public interface IQuestionService {
    public void create(Question question);

    List<QuestionDTO> queryList();
    public PageDTO queryByPageList(Integer page, Integer size);

    PageDTO queryListByQuestionId(Integer id, Integer page, Integer size);

    QuestionDTO getQuestionById(Integer id);

    void createOrUpdate(Question question);
}
