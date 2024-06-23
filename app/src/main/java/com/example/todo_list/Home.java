package com.example.todo_list;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import android.app.AlarmManager;
import android.app.PendingIntent;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;

public class Home extends AppCompatActivity implements MyTaskAdapter.Parentfinish {

    private MaterialToolbar topbar;
    private DrawerLayout drawerLayout;
    private NavigationView nav;

    private RecyclerView recycleview;
    private RecyclerView.Adapter myadapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<MyTaskList> taskLists;

    private RelativeLayout relativeLayout, r1, r2, r3;
    private TextView welcometxt, header, subheader;
    private LocalData localData;
    private String name, uname, pass;
    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        localData = new LocalData(Home.this);
        databaseHandler = new DatabaseHandler(Home.this);

        topbar = findViewById(R.id.topbar);
        drawerLayout = findViewById(R.id.drawerlayout);
        nav = findViewById(R.id.nav);
        recycleview = findViewById(R.id.recycleview);
        relativeLayout = findViewById(R.id.relative);
        welcometxt = findViewById(R.id.welcometxt);
        r1 = findViewById(R.id.r1);
        r2 = findViewById(R.id.r2);
        r3 = findViewById(R.id.r3);

        View headerview = nav.getHeaderView(0);
        header = headerview.findViewById(R.id.header);
        subheader = headerview.findViewById(R.id.subheader);

        // Request notification permissions for Android 13 and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }


//        makenotify();

        name = localData.getsession("key_name");
        uname = localData.getsession("key_uname");
        pass = localData.getsession("key_pass");
        welcometxt.setText("Hi " + name);
        header.setText(name);
        subheader.setText(uname);

        if (savedInstanceState == null) {
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

                if (item.getItemId() == R.id.home) {
                    Toast.makeText(Home.this, "Home", Toast.LENGTH_SHORT).show();
                    nav.setCheckedItem(R.id.home);
                    drawerLayout.close();
                } else if (item.getItemId() == R.id.fav) {
                    Toast.makeText(Home.this, "Favourite", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Home.this, Task.class);
                    intent.putExtra("title", "FAVOURITE TASK'S");
                    startActivityForResult(intent, 0);
                    drawerLayout.close();
                    finish();

                } else if (item.getItemId() == R.id.add) {
                    Toast.makeText(Home.this, "Add Task", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Home.this, AddTask.class);
                    startActivityForResult(intent, 0);
                    drawerLayout.close();
                    finish();
                } else if (item.getItemId() == R.id.logout) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Home.this, R.style.CustomDatePickerDialog);
                    builder.setMessage(Html.fromHtml("<font color='#00000' style='bold' >Do you want to LOGOUT ?</font>"));
                    builder.setTitle(Html.fromHtml("<font color='#2b8ad2'>Logout! </font>"));
                    builder.setCancelable(false);
                    builder.setPositiveButton(Html.fromHtml("<font color='#2b8ad2'>LOGOUT</font>"), (dialog, which) -> {
                        localData.logout();
                        finish();
                    });
                    builder.setNegativeButton(Html.fromHtml("<font color='#2b8ad2'>CANCEL</font>"), (dialog, which) -> {
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
                intent.putExtra("name", name);
                intent.putExtra("uname", uname);
                intent.putExtra("pass", pass);
                startActivity(intent);
                return true;
            }
        });

        // Recycleview
        recycleview.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycleview.setLayoutManager(layoutManager);
        taskLists = new ArrayList<>();
        relativeLayout.setVisibility(View.GONE);
        recycleview.setVisibility(View.VISIBLE);

        myadapter = new MyTaskAdapter(this, taskLists, Home.this);

        Cursor res = databaseHandler.getalltask(localData.getsession("key_uname"));
        if (res.getCount() > 0) {
            while (res.moveToNext()) {
                taskLists.add(new MyTaskList(res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6), res.getString(7), Integer.parseInt(res.getString(8))));
                recycleview.setVisibility(View.VISIBLE);
                relativeLayout.setVisibility(View.GONE);
            }
        } else {
            recycleview.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.VISIBLE);
        }

        recycleview.setAdapter(myadapter);
    }

    public void rclick(View v) {
        int id = v.getId();
        Intent intent = new Intent(Home.this, Task.class);
        if (id == R.id.r1) {
            intent.putExtra("title", "TO-DO TASK'S");
            startActivity(intent);
            finish();
        } else if (id == R.id.r2) {
            intent.putExtra("title", "IN-PROGRESS TASK'S");
            startActivity(intent);
            finish();
        } else if (id == R.id.r3) {
            intent.putExtra("title", "TASK'S DONE");
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0)
            nav.setCheckedItem(R.id.home);
    }

    @Override
    public void finishparemt() {
        finish();

    }


    public void makenotify()
    {
        Intent serviceIntent = new Intent(this, NotificationService.class);

        Calendar calendar = Calendar.getInstance();
        int hr = calendar.get(Calendar.HOUR);
        int min = calendar.get(Calendar.MINUTE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ContextCompat.startForegroundService(this, serviceIntent);
        } else {
            startService(serviceIntent);
        }

    }
}
