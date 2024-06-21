package com.example.todo_list;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import yuku.ambilwarna.AmbilWarnaDialog;

public class AddTask extends AppCompatActivity {

    private EditText taskname,taskdesc,taskdate,tasktime;
    private Spinner taskpriority;
    private ArrayAdapter<String> arrayAdapter;
    int mDefaultColor = 0;
    private ImageView ci1,ci2,ci3,ci4,ci5,ci6,ci7,ci8;
    private LocalData localData;
    private DatabaseHandler databaseHandler;
    private Button addtask;
    private MaterialToolbar topbar;
    String selectedItem;
    int selectedcolor = 0,y,m,d,min,hr;


    View cv1,cv2,cv3,cv4,cv5,cv6,cv7,cv8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        databaseHandler = new DatabaseHandler(AddTask.this);
        localData = new LocalData(AddTask.this);

        topbar = (MaterialToolbar) findViewById(R.id.topbar);
        topbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddTask.this, Home.class);
                startActivity(intent);
                finish();
            }
        });

        taskname = (EditText) findViewById(R.id.taskname);
        taskdesc = (EditText) findViewById(R.id.taskdesc);
        taskdate = (EditText) findViewById(R.id.taskdate);
        tasktime = (EditText) findViewById(R.id.tasktime);
        taskpriority = (Spinner) findViewById(R.id.taskpriority);
        addtask = (Button) findViewById(R.id.addtask);
        cv1 = (View) findViewById(R.id.cv1);
        cv2 = (View) findViewById(R.id.cv2);
        cv3 = (View) findViewById(R.id.cv3);
        cv4 = (View) findViewById(R.id.cv4);
        cv5 = (View) findViewById(R.id.cv5);
        cv6 = (View) findViewById(R.id.cv6);
        cv7 = (View) findViewById(R.id.cv7);
        cv8 = (View) findViewById(R.id.cv8);

        ci1 = (ImageView) findViewById(R.id.ci1);
        ci2 = (ImageView) findViewById(R.id.ci2);
        ci3 = (ImageView) findViewById(R.id.ci3);
        ci4 = (ImageView) findViewById(R.id.ci4);
        ci5 = (ImageView) findViewById(R.id.ci5);
        ci6 = (ImageView) findViewById(R.id.ci6);
        ci7 = (ImageView) findViewById(R.id.ci7);
        ci8 = (ImageView) findViewById(R.id.ci8);


        final Calendar calendar = Calendar.getInstance();
        y = calendar.get(Calendar.YEAR);
        m = calendar.get(Calendar.MONTH);
        d = calendar.get(Calendar.DAY_OF_MONTH);
        taskdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTask.this,R.style.CustomDatePickerDialog,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Log.d("Mohammed Saif",dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                taskdate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, y,m,d);
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });




        min = calendar.get(Calendar.MINUTE);
        hr = calendar.get(Calendar.HOUR);


        tasktime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddTask.this, R.style.CustomDatePickerDialog,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                String amPm;
                                if (hr >= 12) {
                                    amPm = "PM";
                                    if (hr > 12) {
                                        hr -= 12;
                                    }
                                } else {
                                    amPm = "AM";
                                    if (hr == 0) {
                                        hr = 12;
                                    }
                                }
                                Log.d("Mohammed Saif", String.format("%02d:%02d %s", i, i1,amPm));
                                tasktime.setText(String.format("%02d:%02d %s", i, i1,amPm));
                            }
                        },hr,min,false);

                timePickerDialog.show();

            }
        });



        String[] arr = {"Low","Medium","High"};

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,arr);
        taskpriority.setAdapter(arrayAdapter);


        taskpriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    selectedItem = adapterView.getItemAtPosition(i).toString();
                    Toast.makeText(AddTask.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        addtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(taskname.length()>0 && taskdesc.length()>0 && tasktime.length()>0 &&taskdate.length()>0 )
                {
//                    Toast.makeText(AddTask.this, localData.getsession("key_uname")+"", Toast.LENGTH_SHORT).show();
//                    Log.d("Mohammed Saif",localData.getsession("key_uname")+" : "+selectedItem+String.valueOf(selectedcolor));
//                    addtask(String tuser,String tname,String tdesc,String tdate, String ttime,String tprio,String  tstat,String tcolor)
                    String res = databaseHandler.addtask(localData.getsession("key_uname"),taskname.getText().toString(),taskdesc.getText().toString(),taskdate.getText().toString(),tasktime.getText().toString(),selectedItem,"To-Do",String.valueOf(selectedcolor));
                    Log.d("Mohammed Saif",res);
                    if(res.equals("successfully"))
                    {
                        Toast.makeText(AddTask.this, "Task Added Successfullt", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddTask.this, Home.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(AddTask.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });





    }

    public void selectcolor(View v)
    {
        openColorPickerDialogue(v);
    }

    public void openColorPickerDialogue(View v) {

        int id = v.getId();

        if(id==R.id.cv1){
            ColorStateList colorStateList = cv1.getBackgroundTintList();
            int color = colorStateList.getDefaultColor();
            ci1.setVisibility(View.VISIBLE);
            selectedcolor = color;
//            Toast.makeText(this,String.valueOf(color), Toast.LENGTH_SHORT).show();

        } else if (id==R.id.cv2) {
            ColorStateList colorStateList = cv2.getBackgroundTintList();
            int color = colorStateList.getDefaultColor();
            selectedcolor = color;
            ci2.setVisibility(View.VISIBLE);

        }else if (id==R.id.cv3) {
            ColorStateList colorStateList = cv3.getBackgroundTintList();
            int color = colorStateList.getDefaultColor();
            selectedcolor = color;
            ci3.setVisibility(View.VISIBLE);

        }else if (id==R.id.cv4) {
            ColorStateList colorStateList = cv4.getBackgroundTintList();
            int color = colorStateList.getDefaultColor();
            selectedcolor = color;
            ci4.setVisibility(View.VISIBLE);

        }else if (id==R.id.cv5) {
            ColorStateList colorStateList = cv5.getBackgroundTintList();
            int color = colorStateList.getDefaultColor();
            selectedcolor = color;
            ci5.setVisibility(View.VISIBLE);

        }else if (id==R.id.cv6) {
            ColorStateList colorStateList = cv6.getBackgroundTintList();
            int color = colorStateList.getDefaultColor();
            selectedcolor = color;
            ci6.setVisibility(View.VISIBLE);

        }else if (id==R.id.cv7) {
            ColorStateList colorStateList = cv7.getBackgroundTintList();
            int color = colorStateList.getDefaultColor();
            selectedcolor = color;
            ci7.setVisibility(View.VISIBLE);

        }else if (id==R.id.cv8) {
            final AmbilWarnaDialog colorPickerDialogue = new AmbilWarnaDialog(this, mDefaultColor,
                    new AmbilWarnaDialog.OnAmbilWarnaListener() {
                        @Override
                        public void onCancel(AmbilWarnaDialog dialog) {

                        }

                        @Override
                        public void onOk(AmbilWarnaDialog dialog, int color) {

                            mDefaultColor = color;
                            Toast.makeText(AddTask.this, String.valueOf(color), Toast.LENGTH_SHORT).show();
                            ci8.setVisibility(View.VISIBLE);
                            cv8.setBackgroundTintList(ColorStateList.valueOf(mDefaultColor));
                            selectedcolor = color;
                        }
                    });
            colorPickerDialogue.show();
        }



    }
}