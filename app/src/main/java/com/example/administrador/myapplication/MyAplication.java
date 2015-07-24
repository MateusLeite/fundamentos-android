package com.example.administrador.myapplication;

import android.app.Application;

import com.example.administrador.myapplication.util.AppUtil;

/**
 * Created by Administrador on 23/07/2015.
 */
public class MyAplication extends Application {

    @Override
    public void onCreate() {
        AppUtil.CONTEXT = getApplicationContext();
        super.onCreate();
    }
}
