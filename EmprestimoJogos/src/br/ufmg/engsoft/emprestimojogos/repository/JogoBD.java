package br.ufmg.engsoft.emprestimojogos.repository;

import br.ufmg.engsoft.emprestimojogos.domain.Jogo;
import br.ufmg.engsoft.emprestimojogos.helper.Helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JogoBD {
    private static JogoBD instance;
    private List<Jogo> jogoDB = new ArrayList<>();

    private JogoBD() {
        jogoDB.addAll(
                List.of(
                        new Jogo("Uno", "Jogo de Cartas", 5.0, "admin@email.com"),
                        new Jogo("Dos", "Jogo de Cartas 2", 10.0, "admin@email.com"),
                        new Jogo("Batalha Naval", "Batalha com navios", 2.0, "teste@email.com")));
    }

    public static JogoBD getInstance() {
        if (instance == null) {
            instance = new JogoBD();
        }
        return instance;
    }

    public void cadastrarJogo(Jogo jogo) {
        jogoDB.add(jogo);
    }

    public void imprimeJogos() {
        for (Jogo jogo : jogoDB) {
            System.out.println("Nome: " + jogo.getNome());
            System.out.println("Descrição: " + jogo.getDescricao());
            System.out.println("Preço: R$" + jogo.getPreco());
            System.out.println("Email do Dono: " + jogo.getEmailDono());
            System.out.println("--------------------");
            Helper.timer();
        }
    }

    public List<Jogo> getJogos() {
        return jogoDB;
    }

    public void procurarJogo(String palavraChave) {
        List<Jogo> jogosEncontrados = new ArrayList<>();

        for (Jogo jogo : jogoDB) {
            if (jogo.getNome().toLowerCase().contains(palavraChave.toLowerCase()) ||
                    jogo.getDescricao().toLowerCase().contains(palavraChave.toLowerCase())) {
                jogosEncontrados.add(jogo);
            }
        }

        if (jogosEncontrados.isEmpty()) {
            System.out.println("Nenhum jogo encontrado com a palavra-chave: " + palavraChave);
        } else {
            System.out.println("Jogos encontrados com a palavra-chave: " + palavraChave);
            for (Jogo jogo : jogosEncontrados) {
                System.out.println("Nome: " + jogo.getNome());
                System.out.println("Descrição: " + jogo.getDescricao());
                System.out.println("Preço: R$" + jogo.getPreco());
                System.out.println("Email do Dono: " + jogo.getEmailDono());
                System.out.println("--------------------");
                Helper.timer();
            }
        }
    }
}
