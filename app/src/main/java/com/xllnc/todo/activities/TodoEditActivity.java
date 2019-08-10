package com.xllnc.todo.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.xllnc.todo.R;
import com.xllnc.todo.fragments.TodoEditFragment;

import java.util.UUID;

public class TodoEditActivity extends AppCompatActivity {

    private static final String EXTRA_TODO_ID = "extra_todo_is";
    private static final String EXTRA_TODO_IS_NEW_CREATED =  "extra_todo_is_new_created";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragment_container);
        if(fragment == null){
            fragment = TodoEditFragment.newInstance(
                    (UUID)getIntent().getSerializableExtra(EXTRA_TODO_ID),
                    getIntent().getBooleanExtra(EXTRA_TODO_IS_NEW_CREATED, false)
            );
            manager.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }

    public static Intent newIntent(Context context, UUID id, boolean isNewCreated){
        Intent intent = new Intent(context, TodoEditActivity.class);
        intent.putExtra(EXTRA_TODO_ID, id);
        intent.putExtra(EXTRA_TODO_IS_NEW_CREATED, isNewCreated);
        return intent;
    }

    @Override
    public void onBackPressed() {
        TodoEditFragment fragment = (TodoEditFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        fragment.backPressed();
        super.onBackPressed();
    }
}
