package com.example.administrador.myapplication.model.entities;

import com.example.administrador.myapplication.model.persistence.SQLiteClientRepository;
import com.example.administrador.myapplication.model.persistence.SQLiteUserRepository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrador on 30/07/2015.
 */
public class User implements Serializable {

    private Integer id;

    private String login;

    private String password;

    public User() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUserByLoginPassword(){
        return SQLiteUserRepository.getInstance().getUserByLoginPassword(this.getLogin(), this.getPassword());
    }
}
