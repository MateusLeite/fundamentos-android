package com.example.administrador.myapplication.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrador.myapplication.R;
import com.example.administrador.myapplication.model.entities.User;
import com.example.administrador.myapplication.util.FormHelper;

/**
 * Created by Administrador on 20/07/2015.
 */
public class LoginActivity extends AppCompatActivity {

    Button buttonLogin;

    EditText editTextUserName;

    EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextUserName = (EditText) findViewById(R.id.editTextUserName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        bindLoginButton();
    }

    private void bindLoginButton() {
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                if(FormHelper.requireValidate(LoginActivity.this, editTextUserName, editTextPassword)){
                    user.setLogin(editTextUserName.getText().toString());
                    user.setPassword(editTextPassword.getText().toString());
                    if(null != user.getUserByLoginPassword()){
                        Intent goToMainActivity = new Intent(LoginActivity.this, ClientListActivity.class);
                        startActivity(goToMainActivity);
                    }else{
                        Toast.makeText(LoginActivity.this, getString(R.string.user_invalid), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }


}
