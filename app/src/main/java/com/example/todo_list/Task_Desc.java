package com.example.todo_list;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.Objects;

public class Task_Desc extends AppCompatActivity {
    
    private MaterialToolbar topbar;
    private EditText tdname,tddate,tdtime,tddesc,tdpriority,tdstatus;

    private Boolean isLiked = false;

    private Button tdstart,tdedit,tddelete;
    private LocalData localData;
    private DatabaseHandler databaseHandler;
    int tid = 0;
    String parentactivity;
    Intent intent;
//    boolean started = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_desc);

        setTitle("Task Detail");
        localData = new LocalData(Task_Desc.this);
        databaseHandler = new DatabaseHandler(Task_Desc.this);
        
        topbar = (MaterialToolbar) findViewById(R.id.topbar);
        tdname = (EditText) findViewById(R.id.tdname);
        tddate =(EditText) findViewById(R.id.tddate);
        tdtime =(EditText) findViewById(R.id.tdtime);
        tddesc =(EditText) findViewById(R.id.tddesc);
        tdpriority =(EditText) findViewById(R.id.tdpriority);
        tdstatus =(EditText) findViewById(R.id.tdstatus);
        tdstart = (Button) findViewById(R.id.tdstart);
        tdedit = (Button) findViewById(R.id.tdedit);
        tddelete = (Button) findViewById(R.id.tddelete);






        intent = getIntent();
        if(intent.hasExtra("tid"))
        {
            String uname = localData.getsession("key_uname");
            tid = intent.getIntExtra("tid",0);


            Cursor res = databaseHandler.getusertask(uname,tid);
            if(res!=null)
            {
                Log.d("Mohammed Saif",uname+" : "+String.valueOf(tid));
                while(res.moveToNext())
                {
                    Log.d("Mohammed Saif",res.getString(0)+" : "+res.getString(2));
                    String tn = res.getString(2);
                    String tde = res.getString(3);
                    String td = res.getString(4);
                    String tt = res.getString(5);
                    String tp = res.getString(6);
                    String ts = res.getString(7);
                    String tfav = res.getString(9);
                    tdname.setText(tn);
                    tddate.setText(td);
                    tdtime.setText(tt);
                    tddesc.setText(tde);
                    tdpriority.setText(tp);
                    tdstatus.setText(ts);
                    if(ts.equals("To-Do"))
                    {
                        tdstart.setText("Start Task");
                    }
                    else if(ts.equals("In-Progress"))
                    {
                        tdstart.setText("Mark Completed");
                    } else if (ts.equals("Done")) {
                        tdstart.setText("Completed");
                    }
                    if(tfav.equals("fav"))
                    {
                        MenuItem item = topbar.getMenu().findItem(R.id.addfav);
                        Drawable imgRed = ContextCompat.getDrawable(Task_Desc.this, R.drawable.likered);
                        item.setIcon(imgRed);
                        isLiked = true;
                    }
                }
            }
        }

        
        topbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Task_Desc.this, Home.class);
                startActivity(i);
                finish();
            }
        });
        
        topbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Drawable imgRed = ContextCompat.getDrawable(Task_Desc.this, R.drawable.likered);
                Drawable imgWhite = ContextCompat.getDrawable(Task_Desc.this, R.drawable.like);

                if (!isLiked) {
                    item.setIcon(imgRed);
                    String res = databaseHandler.updatefav(localData.getsession("key_uname"),tid,"fav");
                    if(res.equals("successful"))
                    {
                        Toast.makeText(Task_Desc.this, "Liked", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    item.setIcon(imgWhite);
                    String res = databaseHandler.updatefav(localData.getsession("key_uname"),tid,"notfav");
                    if(res.equals("successful"))
                    {
                        Toast.makeText(Task_Desc.this, "Unliked", Toast.LENGTH_SHORT).show();
                    }
                }

                // Toggle the state
                isLiked = !isLiked;


                
                return true;
            }
        });


        //task delete
        tddelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Task_Desc.this,R.style.CustomDatePickerDialog);
                builder.setMessage(Html.fromHtml("<font color='#00000' style='bold' >Do you want to delete this task?</font>"));
                builder.setTitle(Html.fromHtml("<font color='#2b8ad2'>DELETE TASK! </font>"));
                builder.setCancelable(false);
                builder.setPositiveButton(Html.fromHtml("<font color='#2b8ad2'>DELETE</font>"), (DialogInterface.OnClickListener) (dialog, which) -> {
                    String res = databaseHandler.deletetask(localData.getsession("key_uname"),tid);
                    if(res.equals("deleted"))
                    {
                       ShowToast.showToast(Task_Desc.this,"Task Deleted Succesfully");
                        Intent intent = new Intent(Task_Desc.this,Home.class);
                        startActivity(intent);
                        finish();
                    }

                });
                builder.setNegativeButton(Html.fromHtml("<font color='#2b8ad2'>CANCEL</font>"), (DialogInterface.OnClickListener) (dialog, which) -> {
                    dialog.cancel();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

        //TASKSTART
        tdstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(tdstart.getText().toString().equals("Start Task"))
                {
                    tdstart.setText("Mark Completed");
                    tdstatus.setText("In-Progress");
                    String res = databaseHandler.updatestatus(localData.getsession("key_uname"),tid,"In-Progress");
                    if(res.equals("successful"))
                    {
//                        Toast.makeText(Task_Desc.this, "Task In-Progress", Toast.LENGTH_SHORT).show();
                        ShowToast.showToast(Task_Desc.this,"Task IN-PROGRESS");
                    }
                } else if (tdstart.getText().toString().equals("Mark Completed")) {
                    tdstart.setText("Completed");
                    tdstatus.setText("Done");
                    String res = databaseHandler.updatestatus(localData.getsession("key_uname"),tid,"Done");
                    if(res.equals("successful"))
                    {
//                        Toast.makeText(Task_Desc.this, "Task Completed", Toast.LENGTH_SHORT).show();
                        ShowToast.showToast(Task_Desc.this,"Task Completed");
                    }

                }

            }
        });


        //TASKEDIT
        tdedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tdedit.getText().toString().equals("EDIT"))
                {
                    tdedit.setText("SAVE");
                    tdname.setEnabled(true);
                    tddesc.setEnabled(true);
                    tdname.setBackground(ContextCompat.getDrawable(Task_Desc.this,R.drawable.edittextback));
                    tddesc.setBackground(ContextCompat.getDrawable(Task_Desc.this,R.drawable.edittextback));

                } else {
                    tdedit.setText("EDIT");
                    tdname.setBackground(ContextCompat.getDrawable(Task_Desc.this,R.drawable.edittext_design1));
                    tddesc.setBackground(ContextCompat.getDrawable(Task_Desc.this,R.drawable.edittext_design1));
                    tdname.setEnabled(false);
                    tddesc.setEnabled(false);
                    String res = databaseHandler.edittask(localData.getsession("key_uname"),tid,tdname.getText().toString(),tddesc.getText().toString());
                    if(res.equals("successful"))
                    {
                        Log.d("Mohammed Saif","task updated");
                        ShowToast.showToast(Task_Desc.this,"UPDATED SUCCESSFULLY ");
                    }
                    else {
                        Log.d("Mohammed Saif","task not updated");
                    }

                }
            }
        });
        
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Task_Desc.this, Home.class);
        startActivity(i);
        finish();
    }


}