package com.example.administrador.myapplication.model.persistence;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.administrador.myapplication.model.entities.Client;
import com.example.administrador.myapplication.model.entities.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrador on 23/07/2015.
 */
public class UserContract {

    public  static final String TABLE = "user";
    public  static final String ID = "id";
    public  static final String LOGIN = "login";
    public  static final String PASSWORD = "password";

    public static  final String [] COLUMNS = {ID, LOGIN, PASSWORD};

    public static String getCreateTable(){
        StringBuilder sql = new StringBuilder();
        sql.append(" CREATE TABLE ");
        sql.append(TABLE);
        sql.append(" ( ");
        sql.append(ID + " INTEGER PRIMARY KEY, ");
        sql.append(LOGIN + " TEXT, ");
        sql.append(PASSWORD + " String ");
        sql.append(" ); ");
        return sql.toString();
    }

    public static String insertValue(){
        StringBuilder sql = new StringBuilder();
        sql.append(" INSERT INTO ");
        sql.append(TABLE);
        sql.append(" VALUES (1, 'admin', 'admin'); ");
        return sql.toString();
    }

    public static User bind(Cursor cursor){
        if(cursor.moveToFirst()){
            User user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndex(UserContract.ID)));
            user.setLogin(cursor.getString(cursor.getColumnIndex(UserContract.LOGIN)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(UserContract.PASSWORD)));
            return  user;
        }
        return  null;
    }

}
