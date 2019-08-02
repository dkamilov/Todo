package com.xllnc.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.DateFormat;

import com.xllnc.todo.database.TodoBaseHelper;
import com.xllnc.todo.database.TodoCursorWrapper;
import com.xllnc.todo.database.TodoDbSchema.TodoTable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TodoLab {

    private static TodoLab sTodoLab;
    private SQLiteDatabase mDatabase;

    private TodoLab(Context context){
        Context mContext = context.getApplicationContext();
        mDatabase = new TodoBaseHelper(mContext).getWritableDatabase();
    }

    public static TodoLab get(Context context){
        if(sTodoLab == null){
            sTodoLab = new TodoLab(context);
        }
        return sTodoLab;
    }

    private static ContentValues getContentValues(Todo todo){
        ContentValues values = new ContentValues();
        values.put(TodoTable.Cols.UUID, todo.getId().toString());
        values.put(TodoTable.Cols.TITLE, todo.getTitle());
        values.put(TodoTable.Cols.DATE, todo.getDate().getTime());
        return values;
    }

    private TodoCursorWrapper queryTodo(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
            TodoTable.NAME,
            null,
            whereClause,
            whereArgs,
            null,
            null,
            null
        );
        return new TodoCursorWrapper(cursor);
    }

    public void addTodo(Todo todo){
        ContentValues values = getContentValues(todo);
        mDatabase.insert(TodoTable.NAME, null, values);
    }

    public void updateTodo(Todo todo){
        ContentValues values = getContentValues(todo);
        String uuidString = todo.getId().toString();

        mDatabase.update(
                TodoTable.NAME, values,
                TodoTable.Cols.UUID + " =? ",
                new String[]{uuidString});
    }

    public void deleteTodo(Todo todo){
        String uuidString = todo.getId().toString();

        mDatabase.delete(
                TodoTable.NAME,
                TodoTable.Cols.UUID + " =? ",
                new String[]{uuidString});
    }

    public Todo getTodo(UUID id){
        TodoCursorWrapper cursor = queryTodo(
                TodoTable.Cols.UUID + " =? ",
                new String[]{id.toString()}
        );

        try{
            if(cursor.getCount() == 0){
                return null;
            }

            cursor.moveToFirst();
            return cursor.getTodo();
        }finally {
            cursor.close();
        }
    }

    public List<Todo> getItems(){
        List<Todo> items = new ArrayList<>();
        TodoCursorWrapper cursor = queryTodo(null, null);

        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                items.add(cursor.getTodo());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return items;
    }


    public String formatDate(Date date){
        return DateFormat.format("EEE, MMM dd", date).toString();
    }
}
