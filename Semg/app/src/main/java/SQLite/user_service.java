package SQLite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.ByteArrayOutputStream;

import SQLite.user;

public class user_service {
        private SQLiteOpenHelper dbhelper;
        public user_service(Context context){
            dbhelper = new sql(context);
        }
        public boolean register(user s){
            SQLiteDatabase db = dbhelper.getReadableDatabase();
            String sql="insert into user(username,password) values(?,?)";
            Object[] obj ={s.getUsername(),s.getPassword()};
            db.execSQL(sql, obj);
            return true;
        }
        public boolean login(String username,String password){
            SQLiteDatabase db = dbhelper.getReadableDatabase();
            String sql="select * from user where username=? and password=?";
            Cursor cursor=db.rawQuery(sql, new String[]{username,password});
            if(cursor.moveToFirst()){
                cursor.close();
                return true;
            }
            return false;
        }
        public boolean set_photo(String img,String username){
            SQLiteDatabase db = dbhelper.getReadableDatabase();
            ContentValues values = new ContentValues();
            values.put("photo",img);
            db.update("user",values,"username = ?",new String[]{username});
            return true;
        }
        public boolean set_sign(String sign,String username){
            SQLiteDatabase db = dbhelper.getReadableDatabase();
            ContentValues values = new ContentValues();
            values.put("sign",sign);
            db.update("user",values,"username = ?",new String[]{username});
            return true;
        }
        public String serch_sign(String username){
            SQLiteDatabase db = dbhelper.getReadableDatabase();
            String sql = "select * from user where username=?";
            Cursor cursor = db.rawQuery(sql,new String[]{username});
            String sign = "error";
            while(cursor.moveToNext()){
                sign = cursor.getString(cursor.getColumnIndex("sign"));
            }
            return sign;
        }
        public String serch_photo(String username){
            SQLiteDatabase db = dbhelper.getReadableDatabase();
            String sql = "select * from user where username=?";
            Cursor cursor = db.rawQuery(sql,new String[]{username});
            String photo = "error";
            while(cursor.moveToNext()){
                photo = cursor.getString(cursor.getColumnIndex("photo"));
            }
            return photo;
        }
}
