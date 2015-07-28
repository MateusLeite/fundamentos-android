package com.example.administrador.myapplication.controller;

import android.app.ProgressDialog;
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
    private EditText editTextTipoLogradouro;
    private EditText editTextLogradouro;
    private EditText editTextBairro;
    private EditText editTextCidade;
    private EditText editTextEstado;
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
        editTextCep = (EditText)findViewById(R.id.editTextCep);
        editTextCep.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    new GetAdressByCep().execute(editTextCep.getText().toString());
                }
            }
        });
        editTextTipoLogradouro = (EditText)findViewById(R.id.editTextTipoLogradouro);
        editTextLogradouro = (EditText) findViewById(R.id.editTextLogradouro);
        editTextBairro = (EditText) findViewById(R.id.editTexBairro);
        editTextCidade = (EditText) findViewById(R.id.editTextCidade);
        editTextEstado = (EditText) findViewById(R.id.editTextEstado);
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
            if(FormHelper.requireValidate(ClientPersistActivity.this, editTextName, editTextAge, editTextPhone, editTextCep)){
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
        client.getAddress().setCep(editTextCep.getText().toString());
        client.getAddress().setTipoDeLogradouro(editTextTipoLogradouro.getText().toString());
        client.getAddress().setLogradouro(editTextLogradouro.getText().toString());
        client.getAddress().setBairro(editTextBairro.getText().toString());
        client.getAddress().setCidade(editTextCidade.getText().toString());
        client.getAddress().setEstado(editTextBairro.getText().toString());
    }

    private void bindForm(Client client){
        editTextName.setText(client.getName());
        editTextAge.setText(client.getAge().toString());
        editTextPhone.setText(client.getPhone());
        editTextCep.setText(client.getAddress().getCep());
        editTextTipoLogradouro.setText(client.getAddress().getTipoDeLogradouro());
        editTextLogradouro.setText(client.getAddress().getLogradouro());
        editTextBairro.setText(client.getAddress().getBairro());
        editTextCidade.setText(client.getAddress().getCidade());
        editTextEstado.setText(client.getAddress().getEstado());
    }

    private class GetAdressByCep extends AsyncTask<String, Void, ClientAddress> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ClientPersistActivity.this);
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.show();
        }

        @Override
        protected ClientAddress doInBackground(String... params) {
            return CepService.getAdrressBy(params[0]);
        }

        @Override
        protected void onPostExecute(ClientAddress clientAddress) {
            super.onPostExecute(clientAddress);

            if(clientAddress != null){
                editTextTipoLogradouro.setText(clientAddress.getTipoDeLogradouro());
                editTextLogradouro.setText(clientAddress.getLogradouro());
                editTextBairro.setText(clientAddress.getBairro());
                editTextCidade.setText(clientAddress.getCidade());
                editTextEstado.setText(clientAddress.getEstado());
            }

            progressDialog.dismiss();
        }
    }
}
