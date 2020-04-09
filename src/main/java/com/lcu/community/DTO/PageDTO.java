package com.lcu.community.commmunity.DTO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class PageDTO {
    private List<QuestionDTO> questions;
    private boolean showPreious;
    private boolean showFistPage;
    private boolean showNext;
    private boolean showFinalPage;
    private Integer totalPage;
    private Integer page;
    private List<Integer> pages=new ArrayList<>();

    public void pageSeting(Integer page, Integer size, Integer totalCount) {
        totalPage=0;
        if(totalCount%size==0){
            totalPage=totalCount/size;
        }else{
            totalPage=totalCount/size+1;
        }
        this.page=page;
        pages.add(page);
        for(int i=1;i<=3;i++){
            if (page-i>0){
                pages.add(0,page-i);
            }
            if (page+i<=totalPage){
                pages.add(page+i);
            }
        }
        //是否展示上一页
        if (page==1){
            showPreious=false;
        }else{
            showPreious=true;
        }
        //是否展示下一页
        if (page==totalPage){
            showNext=false;
        }else{
            showNext=true;
        }
        //是否展示第一页标识
        if (pages.contains(1)){
            showFistPage=false;
        }else {
            showFistPage=true;
        }
        //是否展示第二页的标识
        if (pages.contains(totalPage)){
            showFinalPage=false;
        }else {
            showFinalPage=true;
        }
    }
}
