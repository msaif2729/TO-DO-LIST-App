package com.example.todo_list;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import yuku.ambilwarna.AmbilWarnaDialog;

public class AddTask extends AppCompatActivity {

    private EditText taskname,taskdesc,taskdate,tasktime;
    private Spinner taskpriority;
    private ArrayAdapter<String> arrayAdapter;
    int mDefaultColor = 0;
    private ImageView ci1,ci2,ci3,ci4,ci5,ci6,ci7,ci8;

    View cv1,cv2,cv3,cv4,cv5,cv6,cv7,cv8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        taskname = (EditText) findViewById(R.id.taskname);
        taskdesc = (EditText) findViewById(R.id.taskdesc);
        taskdate = (EditText) findViewById(R.id.taskdate);
        tasktime = (EditText) findViewById(R.id.tasktime);
        taskpriority = (Spinner) findViewById(R.id.taskpriority);
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


        String[] arr = {"Low","Medium","High"};

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,arr);
        taskpriority.setAdapter(arrayAdapter);


        taskpriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    String selectedItem = adapterView.getItemAtPosition(i).toString();
                    Toast.makeText(AddTask.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
            Toast.makeText(this,String.valueOf(color), Toast.LENGTH_SHORT).show();

        } else if (id==R.id.cv2) {

        }else if (id==R.id.cv3) {

        }else if (id==R.id.cv4) {

        }else if (id==R.id.cv5) {

        }else if (id==R.id.cv6) {

        }else if (id==R.id.cv7) {

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
                        }
                    });
            colorPickerDialogue.show();
        }



    }
}