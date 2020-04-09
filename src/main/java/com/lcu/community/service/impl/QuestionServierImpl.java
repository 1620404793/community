package com.lcu.community.commmunity.service.impl;

import com.lcu.community.commmunity.DTO.PageDTO;
import com.lcu.community.commmunity.DTO.QuestionDTO;
import com.lcu.community.commmunity.mapper.QuestionMapper;
import com.lcu.community.commmunity.mapper.UserMapper;
import com.lcu.community.commmunity.model.Question;
import com.lcu.community.commmunity.model.User;
import com.lcu.community.commmunity.service.IQuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class QuestionServierImpl implements IQuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    public void create(Question question) {
        questionMapper.create(question);
    }

    @Override
    public List<QuestionDTO> queryList() {
        List<Question> questions = questionMapper.queryList();
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        for (Question question:questions){
            User user=userMapper.findUserByCreater(question.getCreater());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
    public PageDTO queryByPageList(Integer page,Integer size) {
        List<Question> questions = questionMapper.queryByPageList((page-1)*size,size);
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        //分页
        PageDTO pageDTO=new PageDTO();
        Integer totalCount = questionMapper.count();
        pageDTO.pageSeting(page,size,totalCount);
        if (page<1){
            page=1;
        }
        if (page>pageDTO.getTotalPage()){
            page=pageDTO.getTotalPage();
        }
        for (Question question:questions){
            User user=userMapper.findUserByCreater(question.getCreater());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestions(questionDTOList);
        return pageDTO;
    }

    @Override
    public PageDTO queryListByQuestionId(Integer id, Integer page, Integer size) {
        List<Question> questions=questionMapper.queryListByQuestionId(id,(page-1)*size,size);        List<QuestionDTO> questionDTOList=new ArrayList<>();
        //分页
        PageDTO pageDTO=new PageDTO();
        Integer totalCount = questionMapper.countByCreater(id);
        pageDTO.pageSeting(page,size,totalCount);
        if (page<1){
            page=1;
        }
        if (page>pageDTO.getTotalPage()){
            page=pageDTO.getTotalPage();
        }
        for (Question question:questions){
            User user=userMapper.findUserByCreater(id);
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestions(questionDTOList);
        return pageDTO;
    }

    @Override
    public QuestionDTO getQuestionById(Integer id) {
        Question question=questionMapper.getQuestionById(id);
        User user=userMapper.findUserByCreater(question.getCreater());
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }

    @Override
    public void createOrUpdate(Question question) {
        if (question.getId()==null){
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setCommentCount(0);
            question.setViewCount(0);
            question.setLikeCount(0);
            questionMapper.create(question);
        }else {
            //更新
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.update(question);
        }
    }
}
