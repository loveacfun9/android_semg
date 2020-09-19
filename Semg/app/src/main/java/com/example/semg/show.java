package com.example.semg;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import IMG_HELPER.ImageUtils;
import IMG_HELPER.RealPathFromUriUtils;
import SQLite.user_service;
import SQLite.user_service_health;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import tensor.TFLiteLoader;
import view.CircularProgressView;


public class show extends AppCompatActivity {

    private static final String TAG = "show";
    private static final int REQUEST_CHOOSE_IMAGE = 100;
    private static final int REQUEST_WRITE_EXTERNAL_PERMISSION_GRANT = 0xff;
    EditText e1;
    ImageView photo;
    ImageView back;
    String text;
    String str;
    user_service user1;
    TextView show_name;
    TextView percent;
    TextView words;
    TextView time;
    TextView show_sign;
    TextView show_time_past;
    TextView show_time_now;
    TextView show_time_after;
    Button btn2;
    Button show_test;
    CircularProgressView c1;
    time t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        init();
        initFont();
        set_show_time();

        Intent intent = getIntent();
        final omg f1 = new omg();
        str = intent.getStringExtra("data");


        show_sign.setText(user1.serch_sign(str));
        String p = user1.serch_photo(str);
        if(p!=null) {
            setphoto(p);
        }

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepareToOpenAlbum();

            }
        });
        show_name.setText(str);
        show_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e1 = new EditText(show.this);
                AlertDialog.Builder edit = new AlertDialog.Builder(show.this);
                edit.setTitle("个性签名");
                edit.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        text = e1.getText().toString();
                        show_sign.setText(text);
                        boolean flag = user1.set_sign(text,str);
                        Log.i(TAG, "onClick: "+flag);
                    }
                });
                edit.setNegativeButton("否",null);
                edit.setView(e1);
                edit.show();
            }
        });


        btn2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {

               // float[][][][] input = new float[][][][]{{{{4.5625f, 20.7657f, 11.8737f, 8.7888f}, {3.3923f, -3.4279f, 11.1989f, 17.1519f}}}};
               // float[][] output = new float[1][6];
               // TFLiteLoader.newInstance(getApplicationContext()).get().run(input, output);
                String health = "健康";
               // int max = 0;
               // for (int i = 0; i < 6; i++) {
               //     if (output[0][i] > output[0][max]) {
               //         max = i;
               //     }
               //     Log.i(TAG, output[0][i] + "");
               // }
                initPython();
                int max =  callpy();
                int local = c1.getProgress();
                System.out.println(local);
                switch (max) {
                    case 0:
                        f1.settime(0, local, c1);
                        percent.setText("0%");
                        words.setText("你很轻松，继续运动吧");
                        break;
                    case 1:
                        f1.settime(20, local, c1);
                        percent.setText("20%");
                        words.setText("有一点疲劳了，继续努力");
                        break;
                    case 2:
                        f1.settime(40, local, c1);
                        percent.setText("40%");
                        words.setText("有点累了");
                        break;
                    case 3:
                        f1.settime(60, local, c1);
                        percent.setText("60%");
                        words.setText("放松一下吧");

                        break;
                    case 4:
                        f1.settime(80, local, c1);
                        percent.setText("80%");
                        words.setText("休息一下吧");
                        break;
                    case 5:
                        f1.settime(100, local, c1);
                        percent.setText("100%");
                        words.setText("注意休息哦");
                }

                Log.i(TAG, max + "");


                //获取现在时间
                Calendar calendar = Calendar.getInstance();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
                sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                String rt = sdf.format(calendar.getTime());
                Log.i(TAG, rt + "");

                //////////////////////////////////////////////
                user_service_health user = new user_service_health(show.this);
                user.save_model(str, health, rt);


            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(show.this, MainActivity.class);
                startActivity(b);
            }
        });
    }
    void init(){
        show_name = findViewById(R.id.textView_name);
        percent = (TextView) findViewById(R.id.textView_show_percent);
        words = findViewById(R.id.textView_show_words);
        c1 = findViewById(R.id.v1);
        photo = findViewById(R.id.imageView_photo);
        back = findViewById(R.id.show_back);
        time = findViewById(R.id.show_time);
        show_sign = findViewById(R.id.textView_word);
        user1 = new user_service(show.this);
        btn2 = (Button) findViewById(R.id.button_test);
        show_test = findViewById(R.id.button_test);
        show_time_past = findViewById(R.id.show_time_past);
        show_time_now = findViewById(R.id.show_time_now);
        show_time_after = findViewById(R.id.show_time_after);
        t1 = new time();
    };
    private void prepareToOpenAlbum() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_PERMISSION_GRANT);
        } else {
            toPicture();
        }
    }
    void initPython(){
        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }
    }
    int callpy(){
        Python py = Python.getInstance();
        List<Integer>ans = new ArrayList<>(100);
        PyObject obj3 = py.getModule("test");
        List<PyObject> pyList = obj3.asList();
        int flag = 0;
        Log.i(TAG, "callpy: "+"类型:"+pyList.getClass());
        float [][][][]input = new float[1][1][2][4];
        float[][] output = new float[1][6];
        for(int m = 0;m<100;m++){
            for(int i = 0;i<2;i++){
                for(int j = 0;j<4;j++){
                    float kk = pyList.get(flag).toFloat();
                    System.out.println(kk);
                    input[0][0][i][j] = kk;
                    flag++;
                }
            }
            ans.add(make(input,output));
        }
        int [][]ans_final = new int[1][6];
        for(int i = 0;i<100;i++){
            int max = ans.get(i);
            ans_final[0][max]++;
        }
        int final_max = 0;
        for(int i = 0;i<6;i++){
            if(ans_final[0][i]>ans_final[0][final_max]){
                final_max = i;
            }
        }
        return final_max;


    }
    int  make(float [][][][]input,float [][]output){
        TFLiteLoader.newInstance(getApplicationContext()).get().run(input, output);
        int max = 0;
        for (int i = 0; i < 6; i++) {
            if (output[0][i] > output[0][max]) {
                max = i;
            }
            Log.i(TAG, output[0][i] + "");
        }
        return max;
    }
    private void toPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);  //跳转到 ACTION_IMAGE_CAPTURE
        intent.setType("image/*");
        startActivityForResult(intent,100);

    }
    void setphoto(String path){
        photo.setImageURI(Uri.fromFile(new File(path)));
    }
    void initFont(){
        Typeface typeface=Typeface.createFromAsset(getAssets(),"font/华文行楷.ttf");
        Typeface typeface2=Typeface.createFromAsset(getAssets(),"font/方正启体简体.ttf");
        Typeface typeface3=Typeface.createFromAsset(getAssets(),"font/SansSerif.ttf");
        show_test.setTypeface(typeface3);
        show_name.setTypeface(typeface);
        show_sign.setTypeface(typeface2);
    }
    void set_show_time(){
        String m[] = {"January","February","March","April","May","June","July","August","September","October","November","December"};
        int month = t1.getMonth();
        show_time_past.setText(m[month-1]);
        show_time_now.setText(m[month]);
        show_time_after.setText(m[month+1]);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i(TAG, "onActivityResult: "+requestCode+"resultcode:"+resultCode);
        if(requestCode == REQUEST_CHOOSE_IMAGE && resultCode == RESULT_OK) {
            Uri uri =  data.getData();
            Log.d("Tianma", "Uri = " + uri);
            String path = ImageUtils.getRealPathFromUri(this, uri);

            //存照片
            user1.set_photo(path,str);


            Log.d("Tianma", "realPath = " + path);

            int requiredHeight = photo.getHeight();
            int requiredWidth = photo.getWidth();
            Bitmap bm = ImageUtils.decodeSampledBitmapFromDisk(path, requiredWidth, requiredHeight);
            photo.setImageBitmap(bm);
        }else{
            Log.i(TAG, "onActivityResult: error");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_WRITE_EXTERNAL_PERMISSION_GRANT) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                toPicture();
            } else {
                Toast.makeText(show.this, "You denied the write_external_storage permission", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
class omg {
    void settime(final int num, final int local, final CircularProgressView c1) {
        final int[] i = {1};
        final Runnable[] r = new Runnable[1];
        final Handler mh = new Handler();
        r[0] = new Runnable() {
            @Override
            public void run() {

                if (local >= num) {
                    c1.setProgress(local - i[0]);
                    i[0]++;
                    if (i[0] >= local - num) {
                        mh.removeCallbacks(r[0]);
                    }
                    if (i[0] < local - num) {
                        mh.postDelayed(this, 10);
                    }
                } else {
                    c1.setProgress(local + i[0]);
                    i[0]++;
                    if (i[0] >= num - local) {
                        mh.removeCallbacks(r[0]);
                    }
                    if (i[0] < num - local) {
                        mh.postDelayed(this, 10);
                    }
                }
            }
        };
        if (i[0] < local - num || i[0] < num - local) {
            mh.postDelayed(r[0], 10);
        }
    }
}
class time{
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int seconds;
    public time(){
        Calendar calendar=Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);
        seconds = calendar.get(Calendar.SECOND);
    }
    public int getYear(){
        return year;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getMonth() {
        return month;
    }

    public int getSeconds() {
        return seconds;
    }
}