package com.example.administrador.myapplication.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrador.myapplication.R;
import com.example.administrador.myapplication.model.entities.Client;

/**
 * Created by Administrador on 21/07/2015.
 */
public class RegisterActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextAge;
    private EditText editTextPhone;
    private EditText editTextAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextName = (EditText)findViewById(R.id.editTextName);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        editTextPhone = (EditText)findViewById(R.id.editTextPhone);
        editTextAddress = (EditText)findViewById(R.id.editTextAddress);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menuSave){
            Client client = bindClient();
            client.save();

            Toast.makeText(RegisterActivity.this, Client.getAll().toString(), Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private Client bindClient(){
        Client client = new Client();
        client.setName(editTextName.getText().toString());
        client.setAge(Integer.valueOf(editTextAge.getText().toString()));
        client.setPhone(editTextPhone.getText().toString());
        client.setAddress(editTextAddress.getText().toString());
        return  client;
    }

}
