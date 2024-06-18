package com.example.todo_list;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class AddTask extends AppCompatActivity {

    private EditText taskname,taskdesc,taskdate,tasktime;
    private Spinner taskpriority;
    private ArrayAdapter<String> arrayAdapter;

    View cw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        taskname = (EditText) findViewById(R.id.taskname);
        taskdesc = (EditText) findViewById(R.id.taskdesc);
        taskdate = (EditText) findViewById(R.id.taskdate);
        tasktime = (EditText) findViewById(R.id.tasktime);
        taskpriority = (Spinner) findViewById(R.id.taskpriority);
        cw = (View) findViewById(R.id.cw);

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
}