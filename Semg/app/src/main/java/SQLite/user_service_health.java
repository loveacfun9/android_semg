package SQLite;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import SQLite.sql_main;

public class user_service_health {
    private sql_main helper;
    private ArrayList<String> healths = new ArrayList<String>();
    private ArrayList<String> times = new ArrayList<String>();
    public user_service_health(Context context){
        helper = new sql_main(context);
    }
    public boolean save_model(String name,String heath_data,String time){
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql="insert into semg_main(name,health,time) values(?,?,?)";
        Object[] obj ={name,heath_data,time};
        db.execSQL(sql, obj);
        return true;
    }
    public void serach_data(String name){

        SQLiteDatabase db = helper.getReadableDatabase();
        String sql="select * from semg_main";
        Cursor cursor=db.rawQuery(sql, null);
        while (cursor.moveToNext()){
            if(cursor.getString(cursor.getColumnIndex("name")).equals(name)){
                healths.add(cursor.getString(cursor.getColumnIndex("health")));
                times.add(cursor.getString(cursor.getColumnIndex("time")));
            }
        }
        return;
    }
    public ArrayList<String> getHealths(){
        return healths;
    }
    public ArrayList<String> getTimes(){
        return times;
    }
}
