package br.ufmg.engsoft.emprestimojogos.domain;

public class Jogo {
    private String nome;
    private String descricao;
    private double preco;
    private String emailDono;

    public Jogo(String nome, String descricao, double preco, String emailDono) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.emailDono = emailDono;
    }

    public String getEmailDono() {
        return emailDono;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getPreco() {
        return preco;
    }
}
