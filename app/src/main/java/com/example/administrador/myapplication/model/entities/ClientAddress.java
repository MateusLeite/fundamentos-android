package com.example.administrador.myapplication.model.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Administrador on 27/07/2015.
 */
public class ClientAddress implements Serializable, Parcelable {

    public ClientAddress(Parcel in) {
        readToParcel(in);
    }

    public ClientAddress(){
        super();
    }

    public String cep;

    private String tipoDeLogradouro;

    private String logradouro;

    private String bairro;

    private String cidade;

    private String estado;

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTipoDeLogradouro() {
        return tipoDeLogradouro;
    }

    public void setTipoDeLogradouro(String tipoDeLogradouro) {
        this.tipoDeLogradouro = tipoDeLogradouro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cep == null ? "" : cep);
        dest.writeString(tipoDeLogradouro == null ? "" : tipoDeLogradouro);
        dest.writeString(logradouro == null ? "" : logradouro);
        dest.writeString(bairro == null ? "" : bairro);
        dest.writeString(cidade == null ? "" : cidade);
        dest.writeString(estado == null ? "" : estado);
    }

    public void readToParcel(Parcel in) {
        cep = in.readString();
        tipoDeLogradouro = in.readString();
        logradouro = in.readString();
        bairro = in.readString();
        cidade = in.readString();
        estado = in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public static  final Parcelable.Creator<ClientAddress> CREATOR = new Parcelable.Creator<ClientAddress>(){

        @Override
        public ClientAddress createFromParcel(Parcel source) {
            return new ClientAddress(source);
        }

        @Override
        public ClientAddress[] newArray(int size) {
            return new ClientAddress[size];
        }
    };
}
