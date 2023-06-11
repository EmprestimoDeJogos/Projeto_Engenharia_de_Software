package br.ufmg.engsoft.emprestimojogos.repository;

import br.ufmg.engsoft.emprestimojogos.domain.Jogo;

import java.util.ArrayList;
import java.util.List;

public class JogoBD {
    private static JogoBD instance;
    private List<Jogo> jogoDB = new ArrayList<>();

    private JogoBD(){
        jogoDB.addAll(
                List.of(
                        new Jogo("Uno", "Jogo de Cartas", 5.0, "admin@email.com"),
                        new Jogo("Dos", "Jogo de Cartas 2", 10.0, "admin@email.com"),
                        new Jogo("Batalha Naval", "Batalha com navios", 2.0, "teste@email.com")
                )
        );
    }
    public static JogoBD getInstance() {
        if (instance == null) {
            instance = new JogoBD();
        }
        return instance;
    }

    public void cadastrarJogo(Jogo jogo){
        jogoDB.add(jogo);
    }

    public List<Jogo> getJogos(){
        return jogoDB;
    }

}
