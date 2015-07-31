package com.example.administrador.myapplication.model.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrador.myapplication.model.entities.Client;
import com.example.administrador.myapplication.model.entities.User;
import com.example.administrador.myapplication.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrador on 23/07/2015.
 */
public class SQLiteUserRepository implements UserRepository {

    private static SQLiteUserRepository singletonInstance;

    private SQLiteUserRepository() {
        super();
    }

    public static SQLiteUserRepository getInstance() {
        if (SQLiteUserRepository.singletonInstance == null) {
            SQLiteUserRepository.singletonInstance = new SQLiteUserRepository();
        }
        return SQLiteUserRepository.singletonInstance;
    }


    @Override
    public User getUserByLoginPassword(String login, String password) {
        User user;
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();

        String[] args = new String[]{login, password};
        Cursor cursor = db.query(UserContract.TABLE, UserContract.COLUMNS, " login = ? AND password = ? ", args, null, null, UserContract.ID);

        user = UserContract.bind(cursor);
        db.close();
        helper.close();
        return user;
    }
}
