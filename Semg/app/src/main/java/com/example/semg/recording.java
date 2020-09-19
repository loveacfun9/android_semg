package com.example.semg;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import SQLite.user_service_health;
public class recording extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);



        /////////////////////////////////////
        TextView r1= findViewById(R.id.re0);
        TextView r2= findViewById(R.id.re1);
        TextView r3= findViewById(R.id.re2);
        TextView r4= findViewById(R.id.re3);
        TextView r5= findViewById(R.id.re4);
        TextView r6= findViewById(R.id.re5);
        TextView r7= findViewById(R.id.re6);
        TextView r8= findViewById(R.id.re7);
        TextView r9= findViewById(R.id.re8);
        List<TextView>list = new ArrayList<TextView>();
        list.add(r1);
        list.add(r2);
        list.add(r3);
        list.add(r4);
        list.add(r5);
        list.add(r6);
        list.add(r7);
        list.add(r8);
        list.add(r9);
        /////////////////////////////////////
        Intent intent = getIntent();
        final String name = intent.getStringExtra("data");
        user_service_health user = new user_service_health(recording.this);
        user.serach_data(name);
        ArrayList<String>h = user.getHealths();
        ArrayList<String>t = user.getTimes();
        int l = 9;
        int max = h.size();
        if(l>h.size()){
            l = h.size();
        }
        for(int i = 0;i<l;i++){
            TextView k = list.get(i);
            k.setText( h.get(max-1)+"                    "+t.get(max-1));
            max--;
        }
    }
}
