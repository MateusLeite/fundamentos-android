package com.example.administrador.myapplication.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrador.myapplication.R;
import com.example.administrador.myapplication.model.entities.Client;
import com.example.administrador.myapplication.util.FormHelper;

/**
 * Created by Administrador on 21/07/2015.
 */
public class ClientRegisterActivity extends AppCompatActivity {

    public static String CLIENT_PARAM = "CLIENT_PARAM";

    private Client client;
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

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            client = (Client) extras.getParcelable(CLIENT_PARAM);
            if(client == null){
                throw  new IllegalArgumentException();
            }
            bindForm(client);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menuSave){
            if(FormHelper.requireValidate(ClientRegisterActivity.this, editTextName, editTextAge, editTextPhone, editTextAddress)){
                bindClient();
                client.save();

                Toast.makeText(ClientRegisterActivity.this, R.string.sucess, Toast.LENGTH_LONG).show();
                ClientRegisterActivity.this.finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void bindClient(){
        client = new Client();
        client.setName(editTextName.getText().toString());
        client.setAge(Integer.valueOf(editTextAge.getText().toString()));
        client.setPhone(editTextPhone.getText().toString());
        client.setAddress(editTextAddress.getText().toString());
    }

    private void bindForm(Client client){
        editTextName.setText(client.getName());
        editTextAge.setText(client.getAge().toString());
        editTextPhone.setText(client.getPhone());
        editTextAddress.setText(client.getAddress());
    }

}