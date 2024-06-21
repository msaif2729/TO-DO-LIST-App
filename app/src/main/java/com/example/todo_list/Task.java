package com.example.todo_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class Task extends AppCompatActivity {


    private RecyclerView recycle;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ArrayList<MyTaskList> list;
    private MaterialToolbar topbar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        topbar = (MaterialToolbar) findViewById(R.id.topbar);


        String title = getIntent().getStringExtra("title");
        topbar.setTitle(title);

        topbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Task.this, Home.class);
                startActivity(intent);
                finish();
            }
        });


        recycle = (RecyclerView) findViewById(R.id.recycle);
        recycle.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycle.setLayoutManager(layoutManager);
        list = new ArrayList<MyTaskList>();
        adapter = new MyTaskAdapter(this,list);
        String[] arr = {"To-Do","In-Progress","Done","To-Do","In-Progress","Done","To-Do"};


        for(int i=1;i<=7;i++)
        {
            list.add(new MyTaskList("Mobile Application","I want to develop some mobile application for completion of my codsoft intern","18/6/2024","06:20","HIGH",arr[i-1],R.color.c2));
        }

        recycle.setAdapter(adapter);

    }
}