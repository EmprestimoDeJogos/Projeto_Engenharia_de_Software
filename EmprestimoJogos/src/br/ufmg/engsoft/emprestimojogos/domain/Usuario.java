package br.ufmg.engsoft.emprestimojogos.domain;

public class Usuario {

    private String email;
    private String nome;
    private String interesses;
    private String senha;

    public Usuario(String email, String nome, String interesses, String senha) {
        this.email = email;
        this.nome = nome;
        this.interesses = interesses;
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getInteresses() {
        return interesses;
    }

    public String getSenha() {
        return senha;
    }
}
