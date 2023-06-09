package com.example.trucksharingtask.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import com.example.trucksharingtask.User;
import com.example.trucksharingtask.Views.orderModel;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataBase extends SQLiteOpenHelper {
    public DataBase(@Nullable Context context) {
        super(context, "truck_app_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_USERS_TABLE = "CREATE TABLE USERS (UID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT, password TEXT, image TEXT, name TEXT, phone_number TEXT);";
        sqLiteDatabase.execSQL(CREATE_USERS_TABLE);
        String CREATE_DELIVERIES_TABLE = "CREATE TABLE DELIVERIES (DID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " receiver TEXT, sender TEXT, goods_type TEXT, veh_type TEXT," +
                "weight INTEGER, length INTEGER, width INTEGER, height INTEGER" +
                ", time TEXT, location TEXT, date TEXT);";
        sqLiteDatabase.execSQL(CREATE_DELIVERIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS DELIVERIES;");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS USERS;");
        onCreate(sqLiteDatabase);
    }
    public long addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user.getUsername());
        contentValues.put("password", user.getPassword());
        contentValues.put("image", user.getImageUrl());
        contentValues.put("name", user.getName());
        contentValues.put("phone_number", user.getPhoneNumber());
        long row = db.insert("USERS", null, contentValues);
        db.close();
        return row;
    }

    public int getUser(String uname, String pass) {
        SQLiteDatabase db = this.getReadableDatabase();
        String rawQuery = "SELECT * FROM USERS WHERE username LIKE '%" + uname + "%' AND password LIKE '%" + pass + "%'";
        Cursor cursor = db.rawQuery(rawQuery, null);
        if(cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("UID"));
                cursor.close();
                return id;
            }
        }
        cursor.close();
        return 0;
    }
    public class MyResult {
        public int id;
        public String name;
        public String password;
        public String phone;
        public String username;
        public String imageUrl; // Here
    }

    public MyResult GUS(int UID) {
        MyResult result = new MyResult();
        SQLiteDatabase db = this.getReadableDatabase();
        String rawQuery = "SELECT * FROM USERS WHERE UID=" + UID;
        Cursor cursor = db.rawQuery(rawQuery, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") int id = result.id = cursor.getInt(cursor.getColumnIndex("UID"));
                @SuppressLint("Range") String name = result.name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String username = result.username = cursor.getString(cursor.getColumnIndex("username"));
                @SuppressLint("Range") String passs = result.password = cursor.getString(cursor.getColumnIndex("password"));
                @SuppressLint("Range") String phone = result.phone =  cursor.getString(cursor.getColumnIndex("phone_number"));
                @SuppressLint("Range") String imageUrl = result.imageUrl = cursor.getString(cursor.getColumnIndex("image"));
            }
            cursor.close();
            return result;
        }
        return result;
    }


    public List<orderModel> getOrders() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM DELIVERIES";
        Cursor cursor = db.rawQuery(sql, null);

        List<orderModel> orderList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                orderModel order = new orderModel(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                        cursor.getString(5), cursor.getString(6), Long.parseLong(cursor.getString(7)),
                        cursor.getInt(8), cursor.getInt(9), cursor.getInt(10), cursor.getInt(11));
                orderList.add(order);
                cursor.moveToNext();
            } while(!cursor.isAfterLast());
        }
        cursor.close();
        return orderList;
    }


    public long addOrder(orderModel order) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("sender", order.getSender());
        contentValues.put("receiver", order.getReceiver());
        contentValues.put("date", order.getDate().toString());
        contentValues.put("time", order.getTime());
        contentValues.put("location", order.getLocation());
        contentValues.put("goods_type", order.getGoodType());
        contentValues.put("veh_type", order.getVehicleType());
        contentValues.put("weight", order.getWeight());
        contentValues.put("length", order.getLength());
        contentValues.put("width", order.getWidth());
        contentValues.put("height", order.getHeight());
        long row = db.insert("DELIVERIES", null, contentValues);
        db.close();
        return row;
    }


}
