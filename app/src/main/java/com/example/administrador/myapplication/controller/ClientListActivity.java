package com.example.administrador.myapplication.controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrador.myapplication.model.entities.Client;
import com.example.administrador.myapplication.R;

import java.util.List;


public class ClientListActivity extends AppCompatActivity {

    public static final String TAG = "TAG";

    private ListView listViewClients;

    private Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindClientList();
    }

    private void bindClientList() {
        List<Client> clientNames = Client.getAll();
        listViewClients = (ListView) findViewById(R.id.listViewClientNew);
        final ClientListAdapter clientAdapter = new ClientListAdapter(ClientListActivity.this, clientNames);
        listViewClients.setAdapter(clientAdapter);

        listViewClients.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        client = (Client) parent.getItemAtPosition(position);
                        return false;
                    }
                }
        );

        registerForContextMenu(listViewClients);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshClientList();
    }

    private void refreshClientList() {
        ClientListAdapter adapter = (ClientListAdapter) listViewClients.getAdapter();
        adapter.setClients(Client.getAll());
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adicionar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menuAdd){
            Intent goToMainActivity = new Intent(ClientListActivity.this, ClientRegisterActivity.class);
            startActivity(goToMainActivity);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        this.getMenuInflater().inflate(R.menu.menu_client_list_context, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menuEdit){
            Intent intent = new Intent(ClientListActivity.this, ClientRegisterActivity.class);
            intent.putExtra(ClientRegisterActivity.CLIENT_PARAM, (Parcelable)client);
            startActivity(intent);
        }else if(item.getItemId() == R.id.menuDelete){
            client.delete();
            refreshClientList();
            Toast.makeText(ClientListActivity.this, getString(R.string.delete_sucess), Toast.LENGTH_LONG).show();
        }

        return super.onContextItemSelected(item);
    }
}
