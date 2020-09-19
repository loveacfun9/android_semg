package com.example.semg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class First extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        TextView test1=(TextView)findViewById(R.id.textView6);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"ziti1.ttf");
        test1.setTypeface(typeface);

        Button btn1 = (Button)findViewById(R.id.first_r);
        Button btn2 = (Button)findViewById(R.id.first_l);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent b = new Intent(First.this, MainActivity.class);
                startActivity(b);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent b = new Intent(First.this, register.class);
                startActivity(b);
            }
        });

    }
}
