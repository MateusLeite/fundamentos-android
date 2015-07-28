package com.example.administrador.myapplication.controller;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrador.myapplication.R;
import com.example.administrador.myapplication.model.entities.Client;
import com.example.administrador.myapplication.model.entities.ClientAddress;
import com.example.administrador.myapplication.model.services.CepService;
import com.example.administrador.myapplication.util.FormHelper;

/**
 * Created by Administrador on 21/07/2015.
 */
public class ClientPersistActivity extends AppCompatActivity {

    public static String CLIENT_PARAM = "CLIENT_PARAM";

    private Client client;
    private EditText editTextName;
    private EditText editTextAge;
    private EditText editTextPhone;
    private EditText editTextAddress;
    private EditText editTextCep;
    private Button buttonFindCEP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        bindFields();
        getParameter();
    }

    private void bindFields() {
        editTextName = (EditText)findViewById(R.id.editTextName);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        editTextPhone = (EditText)findViewById(R.id.editTextPhone);
        editTextAddress = (EditText)findViewById(R.id.editTextAddress);
        editTextCep = (EditText)findViewById(R.id.editTextCep);
        bindButtonFindCep();
    }

    private void bindButtonFindCep() {
        buttonFindCEP = (Button)findViewById(R.id.buttonFindCEP);
        buttonFindCEP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetAdressByCep().execute(editTextCep.getText().toString());
            }
        });
    }

    private void getParameter() {
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
            if(FormHelper.requireValidate(ClientPersistActivity.this, editTextName, editTextAge, editTextPhone, editTextAddress)){
                bindClient();
                client.save();

                Toast.makeText(ClientPersistActivity.this, R.string.sucess, Toast.LENGTH_LONG).show();
                ClientPersistActivity.this.finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void bindClient(){
        if(client == null){
            client = new Client();
        }
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

    private class GetAdressByCep extends AsyncTask<String, Void, ClientAddress> {

        @Override
        protected ClientAddress doInBackground(String... params) {
            return CepService.getAdrressBy(params[0]);
        }
    }
}
