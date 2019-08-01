package com.xllnc.todo.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.xllnc.todo.R;
import com.xllnc.todo.Todo;
import com.xllnc.todo.TodoLab;

import java.util.List;

public class TodoListFragment extends Fragment{

    private FloatingActionButton fab;
    private RecyclerView mRecyclerView;

    private TodoAdapter mAdapter;
    private List<Todo> mItems;

    private Callbacks mCallbacks;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItems = TodoLab.get(getActivity()).getItems();
        mAdapter = new TodoAdapter(mItems);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    public static TodoListFragment newInstance(){
        return new TodoListFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_todo, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Todo todo = new Todo();
                TodoLab.get(getActivity()).addTodo(todo);
                mCallbacks.onItemSelected(todo, true);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }


    /**
     *
     *
     * ViewHolder
     *
     *
     */
    private class TodoHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTitleView;
        private TextView mDateView;
        private Todo mTodo;

        public TodoHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_list_view, parent, false));

            mTitleView = itemView.findViewById(R.id.title_view);
            mDateView = itemView.findViewById(R.id.date_view);
        }

        public void bind(Todo todo){
            mTodo = todo;
            mTitleView.setText(todo.getTitle());
            mDateView.setText(TodoLab.get(getActivity()).formatDate(todo.getDate()));
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mCallbacks.onItemSelected(mTodo, false);
        }
    }

    /**
     *
     *
     *RecyclerView.Adapter
     *
     *
     */
    private class TodoAdapter extends RecyclerView.Adapter<TodoHolder>{
        private List<Todo> mItems;

        public TodoAdapter(List<Todo> items){
            mItems = items;
        }

        @NonNull
        @Override
        public TodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new TodoHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull TodoHolder holder, int position) {
            Todo todo = mItems.get(position);
            holder.bind(todo);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
    }

    public interface Callbacks {
        void onItemSelected(Todo todo, boolean isNewCreated);
    }
}
