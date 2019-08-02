package com.xllnc.todo;


import java.util.Date;
import java.util.UUID;

public class Todo {

    private String mTitle;
    private Date mDate;
    private UUID mId;

    public Todo(){
        mDate = new Date();
        mId = UUID.randomUUID();
    }

    public void setTitle(String title){
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setDate(Date date){
        mDate = date;
    }

    public Date getDate(){
        return mDate;
    }

    public void setId(UUID id){
        mId = id;
    }

    public UUID getId(){
        return mId;
    }
}
