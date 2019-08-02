package com.xllnc.todo.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.xllnc.todo.Todo;
import com.xllnc.todo.database.TodoDbSchema.TodoTable;

import java.util.Date;
import java.util.UUID;

/**
 * Класс в котором делаю запрос в БД и получаю строки
 */

public class TodoCursorWrapper extends CursorWrapper {

    public TodoCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Todo getTodo(){
        String uuidString = getString(getColumnIndex(TodoTable.Cols.UUID));
        String title = getString(getColumnIndex(TodoTable.Cols.TITLE));
        long date = getLong(getColumnIndex(TodoTable.Cols.DATE));

        Todo todo = new Todo();
        todo.setId(UUID.fromString(uuidString));
        todo.setTitle(title);
        todo.setDate(new Date(date));
        return todo;
    }
}
