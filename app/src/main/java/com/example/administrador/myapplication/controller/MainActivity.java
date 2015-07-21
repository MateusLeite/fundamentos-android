package com.example.administrador.myapplication.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.administrador.myapplication.model.entities.Client;
import com.example.administrador.myapplication.R;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Client> clientNames = getClients();
        ListView listViewClients = (ListView) findViewById(R.id.listViewClientNew);
        ClientListAdapter clientAdapter = new ClientListAdapter(MainActivity.this, clientNames);
        listViewClients.setAdapter(clientAdapter);
    }

    private List<Client> getClients() {
        List<Client> clientNamesAux = new ArrayList<>();

        Client cliente1 = new Client();
        cliente1.setName("Mateus");
        cliente1.setAge(23);
        clientNamesAux.add(cliente1);


        Client cliente2 = new Client();
        cliente2.setName("Felipe");
        cliente2.setAge(18);
        clientNamesAux.add(cliente2);

        Client cliente3 = new Client();
        cliente3.setName("Maria");
        cliente3.setAge(18);
        clientNamesAux.add(cliente3);

        return clientNamesAux;
    }

}
