package com.example.petfound;

public class RegistroPessoa {

    public static String[] colunas = new String[] { Pessoas._ID, Pessoas.NOME,
            Pessoas.CPF, Pessoas.IDADE };

    public static final String AUTHORITY = "com.br.CadPessoa.provider.pessoa";

    public long id;
    public String nome;
    public String cpf;
    public int idade;

    public RegistroPessoa() {

    }

    public RegistroPessoa(String nome, String cpf, int idade) {
        super();
        this.nome = nome;
        this.cpf = cpf;
        this.idade = idade;
    }

    public RegistroPessoa(long id, String nome, String cpf, int idade) {
        super();
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.idade = idade;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + ", cpf: " + cpf + ", Idade: " + idade;
    }


}
