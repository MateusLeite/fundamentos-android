package com.example.administrador.myapplication.model.persistence;

import com.example.administrador.myapplication.model.entities.Client;
import com.example.administrador.myapplication.model.entities.User;

import java.util.List;

/**
 * Created by Administrador on 21/07/2015.
 */
public interface UserRepository {

    public abstract User getUserByLoginPassword(String login, String password);

}
