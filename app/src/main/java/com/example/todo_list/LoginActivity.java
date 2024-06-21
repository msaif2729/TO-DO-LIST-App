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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private TextView gotoregister,forgot;
    private Button login;
    private TextInputEditText luname,lpass;
    private TextInputLayout lunamet,lpasst;

    private DatabaseHandler databaseHandler;
    private LocalData localData;

    boolean allright = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        localData = new LocalData(LoginActivity.this);

        gotoregister = (TextView) findViewById(R.id.gotoregister);
        forgot = (TextView) findViewById(R.id.forgot);
        login = (Button) findViewById(R.id.login);
        luname = (TextInputEditText) findViewById(R.id.luname);
        lunamet = (TextInputLayout) findViewById(R.id.lunamet);
        lpass = (TextInputEditText) findViewById(R.id.lpass);
        lpasst = (TextInputLayout) findViewById(R.id.lpasst);

        databaseHandler = new DatabaseHandler(LoginActivity.this);

//        login.setVisibility(View.VISIBLE);
        lpass.setEnabled(false);
        login.setBackground(ContextCompat.getDrawable(LoginActivity.this,R.drawable.button_dis));


        gotoregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,Register.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(allright)
                {
                    Toast.makeText(LoginActivity.this, luname.getText().toString(), Toast.LENGTH_SHORT).show();
                    Cursor res = databaseHandler.getuser(luname.getText().toString(),lpass.getText().toString());
                    while (res.moveToNext())
                    {
                        Log.d("Mohammed Saif",res.getString(0));
                        Intent intent = new Intent(LoginActivity.this, Home.class);
                        localData.createsession(res.getString(0),res.getString(1),res.getString(2));
                        startActivity(intent);
                        finish();
                    }

                }
            }
        });


        luname.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Cursor res = databaseHandler.getalluser();
                boolean exist = false;

                while (res.moveToNext())
                {
                    if(res.getString(1).equals(charSequence.toString()))
                    {
                        exist = true;
                        Log.d("Mohammed Saif",String.valueOf(exist)+" : "+res.getString(1)+" : "+charSequence.toString());
                        break;
                    }
                    else {
                        exist=false;
                    }
                }
                if(charSequence.toString().equals(""))
                {
                    lunamet.setError("Plz Enter Username");
                    lpass.setEnabled(false);
                    login.setBackground(ContextCompat.getDrawable(LoginActivity.this,R.drawable.button_dis));
                } else if (!exist) {
                    lunamet.setError("Username Not Exists");
                    lpass.setEnabled(false);
                    login.setBackground(ContextCompat.getDrawable(LoginActivity.this,R.drawable.button_dis));
                } else{
                    lunamet.setError(null);
                    lpass.setEnabled(true);
                    login.setBackground(ContextCompat.getDrawable(LoginActivity.this,R.drawable.button_dis));

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        lpass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                Cursor res = databaseHandler.getalluser();
                boolean passc = false;

                while (res.moveToNext())
                {
                    if(res.getString(1).equals(luname.getText().toString()) && res.getString(2).equals(charSequence.toString()))
                    {
                        passc = true;
                        Log.d("Mohammed Saif",String.valueOf(passc)+" : "+res.getString(1)+" : "+charSequence.toString());
                        break;
                    }
                    else {
                        passc=false;
                    }
                }

                if(charSequence.toString().equals(""))
                {
                    lpasst.setError("Plz Enter Password");
                    login.setBackground(ContextCompat.getDrawable(LoginActivity.this,R.drawable.button_dis));
                    allright = false;
                } else if (!passc) {
                    lpasst.setError("Password Incorrect");
                    login.setBackground(ContextCompat.getDrawable(LoginActivity.this,R.drawable.button_dis));
                    allright = false;
                } else{
                    lpasst.setError(null);
                    login.setBackground(ContextCompat.getDrawable(LoginActivity.this,R.drawable.button));
                    allright = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }
}