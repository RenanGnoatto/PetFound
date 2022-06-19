package com.example.petfound.DAO;

import android.graphics.Bitmap;

import java.sql.Blob;

public class Pet {

    private int id;
    private String nome;
    private byte[] foto;
    private String cidade;
    private String detalhesPet;
    private String detalhesSumico;
    private String nomeDono;

    public Pet(int id, String nome, String cidade, String detalhesPet, String detalhesSumico, String nomeDono, byte[] foto) {
        this.id = id;
        this.nome = nome;
        this.cidade = cidade;
        this.detalhesPet = detalhesPet;
        this.detalhesSumico = detalhesSumico;
        this.nomeDono = nomeDono;
        this.foto = foto;
    }

    public Pet(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getDetalhesPet() {
        return detalhesPet;
    }

    public void setDetalhesPet(String detalhesPet) {
        this.detalhesPet = detalhesPet;
    }

    public String getDetalhesSumico() {
        return detalhesSumico;
    }

    public void setDetalhesSumico(String detalhesSumico) {
        this.detalhesSumico = detalhesSumico;
    }

    public String getNomeDono() {
        return nomeDono;
    }

    public void setNomeDono(String nomeDono) {
        this.nomeDono = nomeDono;
    }
}
