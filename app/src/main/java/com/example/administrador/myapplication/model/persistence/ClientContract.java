package com.example.administrador.myapplication.model.persistence;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.administrador.myapplication.model.entities.Client;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrador on 23/07/2015.
 */
public class ClientContract {

    public  static final String TABLE = "client";
    public  static final String ID = "id";
    public  static final String NAME = "name";
    public  static final String AGE = "age";
    public  static final String PHONE = "phone";
    public  static final String CEP = "cep";
    public  static final String TIPOLOGRADOURDO = "tipoLogradouro";
    public  static final String LOGRADOURO = "logradouro";
    public  static final String BAIRRO = "bairro";
    public  static final String CIDADE = "cidade";
    public  static final String ESTADO = "estado";

    public static  final String [] COLUMNS = {ID, NAME, AGE, PHONE, CEP, TIPOLOGRADOURDO, LOGRADOURO, BAIRRO, CIDADE, ESTADO};

    public static String getCreateTable(){
        StringBuilder sql = new StringBuilder();
        sql.append(" CREATE TABLE ");
        sql.append(TABLE);
        sql.append(" ( ");
        sql.append(ID + " INTEGER PRIMARY KEY, ");
        sql.append(NAME + " TEXT, ");
        sql.append(AGE + " INTEGER, ");
        sql.append(PHONE + " TEXT, ");
        sql.append(CEP + " TEXT, ");
        sql.append(TIPOLOGRADOURDO + " TEXT, ");
        sql.append(LOGRADOURO + " TEXT, ");
        sql.append(BAIRRO + " TEXT, ");
        sql.append(CIDADE + " TEXT,  ");
        sql.append(ESTADO + " TEXT ");
        sql.append(" ); ");
        return sql.toString();
    }

    public  static ContentValues getContentValue(Client client){
        ContentValues values = new ContentValues();
        values.put(ClientContract.ID, client.getId());
        values.put(ClientContract.NAME, client.getName());
        values.put(ClientContract.AGE, client.getAge());
        values.put(ClientContract.PHONE, client.getPhone());
        values.put(ClientContract.CEP, client.getAddress().getCep());
        values.put(ClientContract.TIPOLOGRADOURDO, client.getAddress().getTipoDeLogradouro());
        values.put(ClientContract.LOGRADOURO, client.getAddress().getLogradouro());
        values.put(ClientContract.BAIRRO, client.getAddress().getBairro());
        values.put(ClientContract.CIDADE, client.getAddress().getCidade());
        values.put(ClientContract.ESTADO, client.getAddress().getEstado());
        return values;
    }

    public static  Client bind(Cursor cursor){
        if(!cursor.isBeforeFirst() || cursor.moveToNext()){
            Client client = new Client();
            client.setId(cursor.getInt(cursor.getColumnIndex(ClientContract.ID)));
            client.setName(cursor.getString(cursor.getColumnIndex(ClientContract.NAME)));
            client.setAge(cursor.getInt(cursor.getColumnIndex(ClientContract.AGE)));
            client.setPhone(cursor.getString(cursor.getColumnIndex(ClientContract.PHONE)));
            client.getAddress().setCep(cursor.getString(cursor.getColumnIndex(ClientContract.CEP)));
            client.getAddress().setTipoDeLogradouro(cursor.getString(cursor.getColumnIndex(ClientContract.TIPOLOGRADOURDO)));
            client.getAddress().setLogradouro(cursor.getString(cursor.getColumnIndex(ClientContract.LOGRADOURO)));
            client.getAddress().setBairro(cursor.getString(cursor.getColumnIndex(ClientContract.BAIRRO)));
            client.getAddress().setCidade(cursor.getString(cursor.getColumnIndex(ClientContract.CIDADE)));
            client.getAddress().setEstado(cursor.getString(cursor.getColumnIndex(ClientContract.ESTADO)));
            return client;
        }
        return null;
    }

    public static List<Client> bindList(Cursor cursor){
        List<Client> clientList = new ArrayList<>();
        while (cursor.moveToNext()){
            clientList.add(bind(cursor));
        }
        return clientList;
    }

}
