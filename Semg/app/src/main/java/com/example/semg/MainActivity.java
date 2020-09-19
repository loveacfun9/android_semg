package com.example.semg;

import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText edit_name = findViewById(R.id.editText_name);
        final EditText edit_password = findViewById(R.id.editText_password);
        final ImageView mark = findViewById(R.id.imageView6);
        Button btn1 = (Button)findViewById(R.id.login_button);


        edit_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("WrongConstant")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>=6){
                    mark.setVisibility(0);
                }else{
                    mark.setVisibility(4);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
             @SuppressLint("WrongConstant")
             @Override
             public void onClick(View view) {

                     mark.setVisibility(0);


                     String name = edit_name.getText().toString();
                     String password = edit_password.getText().toString();
                     user_service user = new user_service(MainActivity.this);
                     boolean flag = user.login(name, password);
                     if (flag) {
                         Intent b = new Intent(MainActivity.this, show.class);
                         b.putExtra("data", name);
                         startActivity(b);
                     } else {
                         Log.i("TAG", "登录失败");
                         Toast.makeText(MainActivity.this, "账号或密码错误", Toast.LENGTH_LONG).show();
                     }

             }
        });
    }
}
