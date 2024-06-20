package com.example.todo_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    private MaterialToolbar topbar;
    private DrawerLayout drawerLayout;
    private NavigationView nav;

    private RecyclerView recycleview;
    private  RecyclerView.Adapter myadapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<MyTaskList> taskLists;

    private RelativeLayout relativeLayout,r1,r2,r3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        topbar = (MaterialToolbar) findViewById(R.id.topbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        nav = (NavigationView) findViewById(R.id.nav);
        recycleview = (RecyclerView) findViewById(R.id.recycleview);
        relativeLayout =  (RelativeLayout) findViewById(R.id.relative);
        r1 = (RelativeLayout) findViewById(R.id.r1);
        r2 = (RelativeLayout) findViewById(R.id.r2);
        r3 = (RelativeLayout) findViewById(R.id.r3);
        nav.setCheckedItem(R.id.home);


        if (savedInstanceState==null)
        {
            nav.setCheckedItem(R.id.home);
        }




        topbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.open();
            }
        });

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                item.setChecked(true);

               if(item.getItemId()==R.id.home)
               {
                   Toast.makeText(Home.this, "Home", Toast.LENGTH_SHORT).show();
               }
               else if(item.getItemId()==R.id.fav)
               {
                   Toast.makeText(Home.this, "Favourite", Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(Home.this, Task.class);
                   intent.putExtra("title","FAVOURITE TASK'S");
                   startActivity(intent);


               } else if (item.getItemId()==R.id.add) {
                   Toast.makeText(Home.this, "Add Task", Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(Home.this, AddTask.class);
                   startActivity(intent);
               }
               else if(item.getItemId()==R.id.logout)
               {
                   Toast.makeText(Home.this, "Logout", Toast.LENGTH_SHORT).show();
               }

                return true;
            }
        });

        topbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Toast.makeText(Home.this, "Account Logo Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Home.this, Profile.class);
                startActivity(intent);
                
                return false;
            }
        });


        //recycleview

        recycleview.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycleview.setLayoutManager(layoutManager);
        taskLists = new ArrayList<MyTaskList>();
        relativeLayout.setVisibility(View.GONE);
        recycleview.setVisibility(View.VISIBLE);

        myadapter = new MyTaskAdapter(this,taskLists);

        String[] arr = {"To-Do","In-Progress","Done","To-Do","In-Progress","Done","To-Do"};

        for(int i=1;i<=7;i++)
        {
            taskLists.add(new MyTaskList("Mobile Application","I want to develop some mobile application for completion of my codsoft intern","18/6/2024","06:20","HIGH",arr[i-1],R.color.c1));
        }

        recycleview.setAdapter(myadapter);


    }

    public void rclick(View v)
    {
        int id = v.getId();
        Intent intent = new Intent(Home.this, Task.class);
        if(id==R.id.r1)
        {
            intent.putExtra("title","TO-DO TASK'S");
            startActivity(intent);
        } else if (id==R.id.r2) {
            intent.putExtra("title","IN-PROGRESS TASK'S");
            startActivity(intent);
        } else if (id==R.id.r3) {
            intent.putExtra("title","TASK'S DONE");
            startActivity(intent);
        }
    }
}