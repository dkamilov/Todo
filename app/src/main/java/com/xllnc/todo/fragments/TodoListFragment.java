package com.xllnc.todo.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.xllnc.todo.R;
import com.xllnc.todo.Todo;
import com.xllnc.todo.TodoLab;

import java.util.List;

public class TodoListFragment extends Fragment{

    private static final String TAG = "TodoListFragment";

    private FloatingActionButton fab;
    private RecyclerView mRecyclerView;
    private TextView noItemsTextView;
    private View view;

    private TodoAdapter mAdapter;
    private Callbacks mCallbacks;
    private List<Todo> items;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        view = inflater.inflate(R.layout.fragment_list_todo, container, false);
        noItemsTextView = view.findViewById(R.id.no_items_text_view);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Todo todo = new Todo();
                TodoLab.get(getActivity()).addTodo(todo);
                mCallbacks.onItemSelected(todo, true);
            }
        });
        Log.i(TAG, "onCreateView");
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
        updateUI();
        checkNoItems();
    }

    private void checkNoItems(){
        Log.i(TAG, "checkNoItems");
        if(items.size() == 0){
            noItemsTextView.setVisibility(View.VISIBLE);
            return;
        }
        noItemsTextView.setVisibility(View.GONE);
    }

    private void updateUI(){
        Log.i(TAG, "updateUi");
        items = TodoLab.get(getActivity()).getItems();
        if(mAdapter == null){
            mAdapter = new TodoAdapter(items);
            mRecyclerView.setAdapter(mAdapter);
        }else{
            mAdapter.setItems(items);
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     *
     *
     *ItemTouchHepler
     *
     *
     */
    private ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.RIGHT, ItemTouchHelper.LEFT){
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            TodoLab.get(getActivity()).deleteTodo(items.get(viewHolder.getAdapterPosition()));
            items.remove(viewHolder.getAdapterPosition());
            mAdapter.setItems(items);
            mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            checkNoItems();
            Log.i(TAG, "onSwiped");
        }
    });


    /**
     *
     *
     * ViewHolder
     *
     *
     */
    private class TodoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleView;
        private TextView mDateView;
        private Todo mTodo;

        public TodoHolder(View itemView) {
            super(itemView);

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
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_list_view, parent, false);
            return new TodoHolder(view);
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

        public void setItems(List<Todo> items){
            mItems = items;
        }


    }

    public interface Callbacks {
        void onItemSelected(Todo todo, boolean isNewCreated);
    }
}
