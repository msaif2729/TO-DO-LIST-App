package com.example.todo_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class Task extends AppCompatActivity implements MyTaskAdapter.Parentfinish {


    private RecyclerView recycle;
    private RelativeLayout relativeLayout;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ArrayList<MyTaskList> list;
    private MaterialToolbar topbar;
    String title;
    private LocalData localData;
    private DatabaseHandler databaseHandler;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        topbar = (MaterialToolbar) findViewById(R.id.topbar);
        recycle = (RecyclerView) findViewById(R.id.recycle);
        relativeLayout =  (RelativeLayout) findViewById(R.id.relative);


        title = getIntent().getStringExtra("title");
        topbar.setTitle(title);
        localData = new LocalData(Task.this);
        databaseHandler = new DatabaseHandler(Task.this);


        topbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Task.this, Home.class);
                startActivity(intent);
                finish();
            }
        });



        recycle.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycle.setLayoutManager(layoutManager);
        list = new ArrayList<MyTaskList>();
        adapter = new MyTaskAdapter(this,list,Task.this);
        String[] arr = {"To-Do","In-Progress","Done","To-Do","In-Progress","Done","To-Do"};


//        for(int i=1;i<=7;i++)
//        {
//            list.add(new MyTaskList("Mobile Application","I want to develop some mobile application for completion of my codsoft intern","18/6/2024","06:20","HIGH",arr[i-1],R.color.c2));
//        }


        if(title.equals("TO-DO TASK'S"))
        {
            Cursor res = databaseHandler.showtodo(localData.getsession("key_uname"));
            if(res!=null)
            {
                while (res.moveToNext())
                {
                    String desc;
                    if(res.getString(3).length()>50)
                    {
                        desc = res.getString(3).substring(0,res.getString(3).length()-10)+"..........";
                    }
                    else {
                        desc = res.getString(3);
                    }
                    list.add(new MyTaskList(res.getString(2),res.getString(3),res.getString(4),res.getString(5),res.getString(6),res.getString(7),Integer.parseInt(res.getString(8))));
                    recycle.setVisibility(View.VISIBLE);
                    relativeLayout.setVisibility(View.GONE);
                }
            }
            else {
                recycle.setVisibility(View.GONE);
                relativeLayout.setVisibility(View.VISIBLE);
            }

        } else if (title.equals("IN-PROGRESS TASK'S")) {
            Cursor res = databaseHandler.showinprogress(localData.getsession("key_uname"));
            if(res!=null)
            {
                while (res.moveToNext())
                {
                    list.add(new MyTaskList(res.getString(2),res.getString(3),res.getString(4),res.getString(5),res.getString(6),res.getString(7),Integer.parseInt(res.getString(8))));
                    recycle.setVisibility(View.VISIBLE);
                    relativeLayout.setVisibility(View.GONE);
                }
            }
            else {
                recycle.setVisibility(View.GONE);
                relativeLayout.setVisibility(View.VISIBLE);
            }

        } else if (title.equals("TASK'S DONE")) {
            Cursor res = databaseHandler.showdone(localData.getsession("key_uname"));
            if(res!=null)
            {
                while (res.moveToNext())
                {
                    list.add(new MyTaskList(res.getString(2),res.getString(3),res.getString(4),res.getString(5),res.getString(6),res.getString(7),Integer.parseInt(res.getString(8))));
                    recycle.setVisibility(View.VISIBLE);
                    relativeLayout.setVisibility(View.GONE);
                }
            }
            else {
                recycle.setVisibility(View.GONE);
                relativeLayout.setVisibility(View.VISIBLE);
            }

        } else if (title.equals("FAVOURITE TASK'S")) {
            Cursor res = databaseHandler.showfav(localData.getsession("key_uname"));
            if(res!=null)
            {
                while (res.moveToNext())
                {
                    list.add(new MyTaskList(res.getString(2),res.getString(3),res.getString(4),res.getString(5),res.getString(6),res.getString(7),Integer.parseInt(res.getString(8))));
                    recycle.setVisibility(View.VISIBLE);
                    relativeLayout.setVisibility(View.GONE);
                }
            }
            else {
                recycle.setVisibility(View.GONE);
                relativeLayout.setVisibility(View.VISIBLE);
            }


        }

        recycle.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Task.this, Home.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void finishparemt() {
        finish();
    }
}