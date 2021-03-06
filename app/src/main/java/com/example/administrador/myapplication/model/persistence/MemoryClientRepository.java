package com.example.administrador.myapplication.model.persistence;

import com.example.administrador.myapplication.model.entities.Client;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrador on 21/07/2015.
 */
public class MemoryClientRepository implements ClientRepository {

    private static MemoryClientRepository singletonInstance;
    private List<Client> clients;

    private MemoryClientRepository() {
        super();
        clients = new ArrayList<>();
    }

    public static MemoryClientRepository getInstance() {
        if (MemoryClientRepository.singletonInstance == null) {
            MemoryClientRepository.singletonInstance = new MemoryClientRepository();
        }
        return MemoryClientRepository.singletonInstance;
    }

    @Override
    public void save(Client client) {
        clients.add(client);
    }

    @Override
    public List<Client> getAll() {
        return clients;
    }

    @Override
    public void delete(Client client) {
        clients.remove(client);
    }
}
