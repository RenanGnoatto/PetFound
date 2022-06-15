package com.example.petfound;

public class Pets {

    private String nome;
    private String foto;
    private String cidade;
    private String detalhesPet;
    private String detalhesSumico;
    private String nomeDono;

    public Pets (String nome, String cidade, String detalhesPet, String detalhesSumico, String nomeDono) {
        this.nome = nome;
        this.cidade = cidade;
        this.detalhesPet = detalhesPet;
        this.detalhesSumico = detalhesSumico;
        this.nomeDono = nomeDono;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
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
