package com.xllnc.todo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;

import com.xllnc.todo.R;
import com.xllnc.todo.Todo;
import com.xllnc.todo.fragments.TodoListFragment;

public class TodoListActivity extends AppCompatActivity implements TodoListFragment.Callbacks{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragment_container);
        if(fragment == null){
            fragment = TodoListFragment.newInstance();
            manager.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }

    @Override
    public void onItemSelected(Todo todo, boolean isNewCreated) {
        Intent intent = TodoEditActivity.newIntent(this, todo.getId(), isNewCreated);
        startActivity(intent);
    }

}
