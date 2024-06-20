package com.example.todo_list;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Register extends AppCompatActivity {

    private Button register;
    private TextInputLayout rnamet,runamet,rpasst;
    private TextInputEditText rname,runame,rpass;

    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register = (Button) findViewById(R.id.register);
        rname = (TextInputEditText) findViewById(R.id.rname);
        runame = (TextInputEditText) findViewById(R.id.runame);
        rpass = (TextInputEditText) findViewById(R.id.rpass);
        rnamet = (TextInputLayout) findViewById(R.id.rnamet);
        runamet = (TextInputLayout) findViewById(R.id.runamet);
        rpasst = (TextInputLayout) findViewById(R.id.rpasst);
        databaseHandler = new DatabaseHandler(Register.this);

        register.setEnabled(false);
        runame.setEnabled(false);
        rpass.setEnabled(false);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Home.class);
                startActivity(intent);
                finish();
            }
        });

        String upcase = (".*[A-Z].*");
        String locase = (".*[a-z].*");
        String numbers = "(.*[0-9].*)";
        String specialChars = "(.*[,~,!,@,#,$,%,^,&,*,(,),-,_,=,+,[,{,],},|,;,:,<,>,/,?].*$)";

        rname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String name = "^()";

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}