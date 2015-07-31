package com.example.administrador.myapplication.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
    private TextView editTextTipoLogradouro;
    private TextView editTextLogradouro;
    private TextView editTextBairro;
    private TextView editTextCidade;
    private TextView editTextEstado;
    private TextView buttonFindCEP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        bindFields();
        getParameter();
        hideFields(null == editTextTipoLogradouro.getText() || editTextTipoLogradouro.getText().toString().trim().isEmpty());
    }

    private void bindFields() {
        editTextName = (EditText)findViewById(R.id.editTextName);
        editTextName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_edittext_client, 0);
        editTextName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (editTextName.getRight() - editTextName.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        //TODO: Explanation 2:
                        final Intent goToSOContacts = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                        goToSOContacts.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
                        startActivityForResult(goToSOContacts, 999);
                    }
                }
                return false;
            }

        });

        editTextAge = (EditText) findViewById(R.id.editTextAge);
        editTextPhone = (EditText)findViewById(R.id.editTextPhone);
        editTextCep = (EditText)findViewById(R.id.editTextCep);
        editTextCep.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_search, 0);
        editTextCep.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (editTextCep.getRight() - editTextCep.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if(FormHelper.requireValidate(ClientPersistActivity.this, editTextCep)){
                            new GetAdressByCep().execute(editTextCep.getText().toString());
                        }else{
                            hideFields(true);
                        }
                    }
                }
                return false;
            }
        });
        editTextTipoLogradouro = (TextView)findViewById(R.id.editTextTipoLogradouro);
        editTextLogradouro = (TextView) findViewById(R.id.editTextLogradouro);
        editTextBairro = (TextView) findViewById(R.id.editTexBairro);
        editTextCidade = (TextView) findViewById(R.id.editTextCidade);
        editTextEstado = (TextView) findViewById(R.id.editTextEstado);
    }

    private void hideFields(boolean hide) {
        if(hide){
            editTextTipoLogradouro.setVisibility(View.INVISIBLE);
            editTextLogradouro.setVisibility(View.INVISIBLE);
            editTextBairro.setVisibility(View.INVISIBLE);
            editTextCidade.setVisibility(View.INVISIBLE);
            editTextEstado.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * @see <a href="http://developer.android.com/training/basics/intents/result.html">Getting a Result from an Activity</a>
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 999) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    final Uri contactUri = data.getData();
                    final String[] projection = {
                            ContactsContract.CommonDataKinds.Identity.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                    };
                    final Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
                    cursor.moveToFirst();

                    editTextName.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Identity.DISPLAY_NAME)));
                    editTextPhone.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));

                    cursor.close();
                } catch (Exception e) {
                    Log.d("TAG", "Unexpected error");
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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
                if(editTextTipoLogradouro.getVisibility() == View.VISIBLE){
                    bindClient();
                    client.save();

                    Toast.makeText(ClientPersistActivity.this, R.string.sucess, Toast.LENGTH_LONG).show();
                    ClientPersistActivity.this.finish();
                }else{
                    editTextCep.setError(getString(R.string.zip_invalid));
                }
            }else{
                hideFields(true);
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
        client.getAddress().setEstado(editTextEstado.getText().toString());
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

    public static boolean isNumeric (String s) {
        try {
            Long.parseLong (s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
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
            if(null != params){
                try{
                    return CepService.getAdrressBy(params[0]);
                }catch (RuntimeException e){
                    return null;
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(ClientAddress clientAddress) {
            super.onPostExecute(clientAddress);

            if(clientAddress != null){
                editTextTipoLogradouro.setText(clientAddress.getTipoDeLogradouro());
                editTextTipoLogradouro.setVisibility(View.VISIBLE);
                editTextLogradouro.setText(clientAddress.getLogradouro());
                editTextLogradouro.setVisibility(View.VISIBLE);
                editTextBairro.setText(clientAddress.getBairro());
                editTextBairro.setVisibility(View.VISIBLE);
                editTextCidade.setText(clientAddress.getCidade());
                editTextCidade.setVisibility(View.VISIBLE);
                editTextEstado.setText(clientAddress.getEstado());
                editTextEstado.setVisibility(View.VISIBLE);
            }else{
                Toast.makeText(ClientPersistActivity.this, R.string.zip_invalid, Toast.LENGTH_LONG).show();
                hideFields(true);
            }

            progressDialog.dismiss();
        }
    }
}
