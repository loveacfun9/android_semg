package com.example.semg;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import SQLite.user_service;
import SQLite.user;
import androidx.appcompat.app.AppCompatActivity;


public class register extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        final EditText edit_name = findViewById(R.id.editText_name_re);
        final EditText edit_pwd = findViewById(R.id.editText_password_re);
        final EditText edit_pwd_c = findViewById(R.id.editText_passwordc_re);
        final ImageView success = findViewById(R.id.success);
        final ImageView fail = findViewById(R.id.fail);
        final ImageView success_2 = findViewById(R.id.succ_2);
        Button btn2 = (Button)findViewById(R.id.register_button);

        edit_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("WrongConstant")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>=6){
                    success.setVisibility(0);
                }else {
                    success.setVisibility(4);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edit_pwd_c.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("WrongConstant")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    success_2.setVisibility(4);
                    fail.setVisibility(4);
                }else if(s.toString().equals(edit_pwd.getText().toString())){
                    success_2.setVisibility(0);
                    fail.setVisibility(4);
                }else{
                    success_2.setVisibility(4);
                    fail.setVisibility(0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name  = edit_name.getText().toString().trim();
                String password  = edit_pwd.getText().toString().trim();
                Log.i("TAG",name+"_"+password);
                user_service re = new user_service(register.this);
                user user1 = new user();
                user1.setUsername(name);
                user1.setPassword(password);
                boolean flag = re.register(user1);
                if(flag) {
                    Intent b = new Intent(register.this, show.class);
                    b.putExtra("data", name);
                    startActivity(b);
                    Toast.makeText(register.this, "注册成功", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(register.this, "注册失败", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}