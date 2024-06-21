package com.example.todo_list;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
    private TextView welcometxt,header,subheader;
    private LocalData localData;
    private String name,uname,pass;
    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        localData= new LocalData(Home.this);
        databaseHandler = new DatabaseHandler(Home.this);

        topbar = (MaterialToolbar) findViewById(R.id.topbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        nav = (NavigationView) findViewById(R.id.nav);
        recycleview = (RecyclerView) findViewById(R.id.recycleview);
        relativeLayout =  (RelativeLayout) findViewById(R.id.relative);
        welcometxt = (TextView) findViewById(R.id.welcometxt);
        r1 = (RelativeLayout) findViewById(R.id.r1);
        r2 = (RelativeLayout) findViewById(R.id.r2);
        r3 = (RelativeLayout) findViewById(R.id.r3);

        View headerview = nav.getHeaderView(0);
        header = (TextView) headerview.findViewById(R.id.header);
        subheader = (TextView) headerview.findViewById(R.id.subheader);




        Intent intent = getIntent();
        name = localData.getsession("key_name");
        uname = localData.getsession("key_uname");
        pass = localData.getsession("key_pass");
        welcometxt.setText("Hi "+name);
        header.setText(name);
        subheader.setText(uname);



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
                   nav.setCheckedItem(R.id.home);
                   drawerLayout.close();
               }
               else if(item.getItemId()==R.id.fav)
               {
                   Toast.makeText(Home.this, "Favourite", Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(Home.this, Task.class);
                   intent.putExtra("title","FAVOURITE TASK'S");
                   startActivityForResult(intent,0);
                   drawerLayout.close();

               } else if (item.getItemId()==R.id.add) {
                   Toast.makeText(Home.this, "Add Task", Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(Home.this, AddTask.class);
                   startActivityForResult(intent,0);
                   drawerLayout.close();
               }
               else if(item.getItemId()==R.id.logout)
               {
                   Toast.makeText(Home.this, "Logout", Toast.LENGTH_SHORT).show();
                   AlertDialog.Builder builder = new AlertDialog.Builder(Home.this,R.style.CustomDatePickerDialog);
                   builder.setMessage(Html.fromHtml("<font color='#00000' style='bold' >Do you want to LOGOUT ?</font>"));
                   builder.setTitle(Html.fromHtml("<font color='#2b8ad2'>Logout! </font>"));
                   builder.setCancelable(false);
                   builder.setPositiveButton(Html.fromHtml("<font color='#2b8ad2'>LOGOUT</font>"), (DialogInterface.OnClickListener) (dialog, which) -> {
                       localData.logout();
                       finish();
                   });
                   builder.setNegativeButton(Html.fromHtml("<font color='#2b8ad2'>CANCEL</font>"), (DialogInterface.OnClickListener) (dialog, which) -> {
                       dialog.cancel();
                       nav.setCheckedItem(R.id.home);
                   });
                   AlertDialog alertDialog = builder.create();
                   alertDialog.show();

                   drawerLayout.close();
               }

                return true;
            }
        });

        topbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(Home.this, Profile.class);
                intent.putExtra("name",name);
                intent.putExtra("uname",uname);
                intent.putExtra("pass",pass);
                startActivity(intent);
                return true;
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

        Cursor res = databaseHandler.getalltask();
        if(res!=null)
        {
            while (res.moveToNext())
            {
                taskLists.add(new MyTaskList(res.getString(2),res.getString(3),res.getString(4),res.getString(5),res.getString(6),res.getString(7),Integer.parseInt(res.getString(8))));
                recycleview.setVisibility(View.VISIBLE);
                relativeLayout.setVisibility(View.GONE);
            }
        }
        else {
            recycleview.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.VISIBLE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==0)
            nav.setCheckedItem(R.id.home);

    }
}