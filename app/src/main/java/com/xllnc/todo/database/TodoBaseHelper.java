package com.xllnc.todo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.xllnc.todo.database.TodoDbSchema.TodoTable;

/**
 * Класс в котором создаю БД и таблицу
 */

public class TodoBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "todoBase.db";
    private static final int VERSION = 1;

    public TodoBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TodoTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                TodoTable.Cols.UUID + ", " +
                TodoTable.Cols.TITLE + ", " +
                TodoTable.Cols.DATE + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
