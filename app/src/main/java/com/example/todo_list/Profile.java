package com.example.todo_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

public class Profile extends AppCompatActivity {

    private MaterialToolbar topbar;

    private String name,uname,pass;
    private TextInputEditText name1,username,password;
    private LocalData localData;
    private TextView ttotal,ptotal,dtotal;

    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        localData = new LocalData(Profile.this);

        setTitle("Profile");

        Intent intent = getIntent();
        name = localData.getsession("key_name");
        uname = localData.getsession("key_uname");
        pass = localData.getsession("key_pass");
        name1 = findViewById(R.id.name);
        username =  findViewById(R.id.username);
        password =  findViewById(R.id.password);
        ttotal = (TextView) findViewById(R.id.ttotal);
        ptotal = (TextView) findViewById(R.id.ptotal);
        dtotal = (TextView) findViewById(R.id.dtotal);

        databaseHandler = new DatabaseHandler(Profile.this);

        Cursor todo = databaseHandler.showtodo(localData.getsession("key_uname"));
        ttotal.setText("Total Task : "+todo.getCount());

        Cursor prog = databaseHandler.showinprogress(localData.getsession("key_uname"));
        ptotal.setText("Total Task : "+prog.getCount());

        Cursor done = databaseHandler.showdone(localData.getsession("key_uname"));
        dtotal.setText("Total Task : "+done.getCount());


        name1.setText(name);
        username.setText(uname);
        password.setText(pass.substring(0,(pass.length()/2)-2)+"***"+pass.substring((pass.length()/2)+1));



        topbar = (MaterialToolbar) findViewById(R.id.topbar);

        topbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, Home.class);
                startActivity(intent);
                finish();
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Profile.this, Home.class);
        startActivity(intent);
        finish();
    }
}