package com.xllnc.todo;

import android.content.Context;
import android.text.format.DateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TodoLab {

    private static TodoLab sTodoLab;
    private List<Todo> mItems;
    private Context mContext;

    private TodoLab(Context context){
        mItems = new ArrayList<>();
        mContext = context;

        for(int i = 0; i < 10; i++){
            Todo todo = new Todo();
            todo.setTitle("Item " + i);
            mItems.add(todo);
        }
    }

    public static TodoLab get(Context context){
        if(sTodoLab == null){
            sTodoLab = new TodoLab(context);
        }
        return sTodoLab;
    }

    public List<Todo> getItems(){
        return mItems;
    }

    public Todo getCrime(UUID id){
        for(Todo item : mItems){
            if(item.getId().equals(id)){
                return item;
            }
        }
        return null;
    }

    public void addTodo(Todo todo){
        mItems.add(todo);
    }

    public void updateTodo(Todo todo){
        for(Todo item : mItems){
            if(item.getId().equals(todo.getId())){
                item.setTitle(todo.getTitle());
            }
        }
    }

    public void deleteTodo(Todo todo){
        for(Todo item : mItems){
            if(item.getId().equals(todo.getId())){
                mItems.remove(item);
            }
        }
    }

    public String formatDate(Date date){
        return DateFormat.format("EEE, MMM dd", date).toString();
    }
}
