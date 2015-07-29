package com.example.administrador.myapplication.model.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrador on 23/07/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    private   static  final String BANCO_DADOS = "MY_DATANASE";
    private   static  final int VERSION = 2;

    public DatabaseHelper(Context context){
        super(context, DatabaseHelper.BANCO_DADOS, null, DatabaseHelper.VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ClientContract.getCreateTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
