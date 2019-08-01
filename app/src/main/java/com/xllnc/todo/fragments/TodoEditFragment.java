package com.xllnc.todo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.xllnc.todo.R;
import com.xllnc.todo.Todo;
import com.xllnc.todo.TodoLab;

import java.util.UUID;

public class TodoEditFragment extends Fragment {

    private static final String ARGS_TODO_ID = "todo_id";
    private static final String ARGS_TODO_IS_NEW_CREATED = "todo_is_new_created";

    private EditText editText;

    private Todo mTodo;


    public static TodoEditFragment newInstance(UUID id, boolean isNewCreated){
        Bundle args = new Bundle();
        args.putSerializable(ARGS_TODO_ID, id);
        args.putBoolean(ARGS_TODO_IS_NEW_CREATED, isNewCreated);
        TodoEditFragment fragment = new TodoEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID mId = (UUID)getArguments().getSerializable(ARGS_TODO_ID);
        mTodo = TodoLab.get(getActivity()).getCrime(mId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_todo, container, false);
        editText = view.findViewById(R.id.editText);
        editText.setText(mTodo.getTitle());
        Toolbar mToolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.edit_fragment_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save:
                checkSave();
                return true;
            case R.id.cancel:
                checkCancel();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void checkSave(){
        if(editText.getText().toString().trim().isEmpty()) {
            TodoLab.get(getActivity()).deleteTodo(mTodo);
            makeToast(getResources().getString(R.string.edit_fragment_no_content));
        }else{
            mTodo.setTitle(editText.getText().toString());
            TodoLab.get(getActivity()).updateTodo(mTodo);
            makeToast(getResources().getString(R.string.edit_fragment_saved));
        }
        getActivity().finish();
    }

    private void checkCancel(){
        if(getArguments().getBoolean(ARGS_TODO_IS_NEW_CREATED)){
            TodoLab.get(getActivity()).deleteTodo(mTodo);
        }
        getActivity().finish();
    }

    private void makeToast(String text){
        Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
    }

    public void backPressed(){
        checkSave();
    }

}
