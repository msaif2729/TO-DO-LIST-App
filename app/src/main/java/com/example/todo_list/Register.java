package com.example.todo_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Register extends AppCompatActivity {

    private Button register;
    private TextInputLayout rnamet,runamet,rpasst;
    private TextInputEditText rname,runame,rpass;

    private DatabaseHandler databaseHandler;
    private LocalData localData;
    boolean allright = false;
    private TextView gotologin;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        localData = new LocalData(Register.this);

        register = (Button) findViewById(R.id.register);
        rname = (TextInputEditText) findViewById(R.id.rname);
        runame = (TextInputEditText) findViewById(R.id.runame);
        rpass = (TextInputEditText) findViewById(R.id.rpass);
        rnamet = (TextInputLayout) findViewById(R.id.rnamet);
        runamet = (TextInputLayout) findViewById(R.id.runamet);
        rpasst = (TextInputLayout) findViewById(R.id.rpasst);
        gotologin = (TextView) findViewById(R.id.gotologin);
        databaseHandler = new DatabaseHandler(Register.this);

        register.setBackground(ContextCompat.getDrawable(Register.this,R.drawable.button_dis));
        runame.setEnabled(false);
        rpass.setEnabled(false);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(allright)
                {
                    String result = databaseHandler.adduser(rname.getText().toString(),runame.getText().toString(),rpass.getText().toString());
                    if(result.equals("Inserted"))
                    {
                        Intent intent = new Intent(Register.this, Home.class);
                        localData.createsession(rname.getText().toString(),runame.getText().toString(),rpass.getText().toString());
                        startActivity(intent);
                        finish();
//                        Toast.makeText(Register.this, result, Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(Register.this, "Error In Inserting", Toast.LENGTH_SHORT).show();
                    }
                    
                }
            }
        });

        gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String upcase = (".*[A-Z].*");
        String locase = (".*[a-z].*");
        String numbers = "(.*[0-9].*)";
        String specialChars = "(.*[,~,!,@,#,$,%,^,&,*,(,),-,_,=,+,[,{,],},|,;,:,<,>,/,?].*$)";
        String spaces = ("^\\S*$");

        rname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().equals(""))
                {
                    register.setBackground(ContextCompat.getDrawable(Register.this,R.drawable.button_dis));
                    rnamet.setError("Plz Enter Name");
                    runame.setEnabled(false);
                    rpass.setEnabled(false);
                } else if (charSequence.toString().matches(numbers)|| charSequence.toString().matches(specialChars)) {
                    register.setBackground(ContextCompat.getDrawable(Register.this,R.drawable.button_dis));
                    rnamet.setError("Only Contains Characters");
                    runame.setEnabled(false);
                    rpass.setEnabled(false);
                }
                else {
                    register.setBackground(ContextCompat.getDrawable(Register.this,R.drawable.button_dis));
                    rnamet.setError(null);
                    runame.setEnabled(true);
                    rpass.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        runame.addTextChangedListener(new TextWatcher() {



            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                Cursor res = databaseHandler.getalluser();
                boolean alreadyexist = false;

                while (res.moveToNext())
                {
                    if(res.getString(1).equals(charSequence.toString()))
                    {
                        alreadyexist = true;
                        Log.d("Mohammed Saif",String.valueOf(alreadyexist)+" : "+res.getString(1)+" : "+charSequence.toString());
                        break;
                    }
                    else {
                        alreadyexist=false;
                    }
                }

                if(charSequence.toString().equals(""))
                {
                    register.setBackground(ContextCompat.getDrawable(Register.this,R.drawable.button_dis));
                    runamet.setError("Plz Enter Name");
                    rpass.setEnabled(false);
                } else if (!charSequence.toString().matches(upcase)|| !charSequence.toString().matches(locase)|| !charSequence.toString().matches(numbers)) {
                    register.setBackground(ContextCompat.getDrawable(Register.this,R.drawable.button_dis));
                    runamet.setError("Should contain only alphanumeric character");
                    rpass.setEnabled(false);
                } else if (charSequence.length()<4 || charSequence.length()>8) {
                    register.setBackground(ContextCompat.getDrawable(Register.this,R.drawable.button_dis));
                    runamet.setError("Should be greater than 4 and less than 8");
                    rpass.setEnabled(false);
                } else if (!charSequence.toString().matches(spaces)) {
                    register.setBackground(ContextCompat.getDrawable(Register.this,R.drawable.button_dis));
                    runamet.setError("Should not contain spaces");
                    rpass.setEnabled(false);

                } else if (alreadyexist) {
                    register.setBackground(ContextCompat.getDrawable(Register.this,R.drawable.button_dis));
                    runamet.setError("Already Exists");
                    rpass.setEnabled(false);
                } else {
                    register.setBackground(ContextCompat.getDrawable(Register.this,R.drawable.button));
                    runamet.setError(null);
                    rpass.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        
        rpass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().equals(""))
                {
                    register.setBackground(ContextCompat.getDrawable(Register.this,R.drawable.button_dis));
                    rpasst.setError("Plz Enter Password");
                    allright = false;
                } else if (!charSequence.toString().matches(upcase)|| !charSequence.toString().matches(locase)|| !charSequence.toString().matches(numbers)|| !charSequence.toString().matches(specialChars)) {
                    register.setBackground(ContextCompat.getDrawable(Register.this,R.drawable.button_dis));
                    rpasst.setError("Should contain 1 Upper & Lower Case Alphabet, Numbers,Special Characters");
                    allright = false;
                } else if (charSequence.length()<4 || charSequence.length()>10) {
                    register.setBackground(ContextCompat.getDrawable(Register.this,R.drawable.button_dis));
                    rpasst.setError("Should be greater than 4 and less than 10");
                    allright = false;
                } else {
                    register.setBackground(ContextCompat.getDrawable(Register.this,R.drawable.button));
                    rpasst.setError(null);
                    allright = true;
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}