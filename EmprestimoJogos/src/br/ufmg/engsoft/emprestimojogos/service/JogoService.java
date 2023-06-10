package br.ufmg.engsoft.emprestimojogos.service;

import br.ufmg.engsoft.emprestimojogos.domain.Jogo;
import br.ufmg.engsoft.emprestimojogos.repository.JogoBD;
import br.ufmg.engsoft.emprestimojogos.session.GerenciadorSessao;

import java.util.InputMismatchException;

public class JogoService {
    private JogoBD jogoBD;

    public JogoService() {
        jogoBD = JogoBD.getInstance();
    }

    public void cadastrarJogo(String nome, String descricao, double preco){
        if(descricao == null || descricao == ""){
            throw new InputMismatchException("Descricao incorreta ou faltante");
        }

        if(nome == null || nome == ""){
            throw new InputMismatchException("Nome incorreto ou faltante");
        }

        if(preco < 0){
            throw new InputMismatchException("Preco incorreto, não pode ser negativo");
        }

        jogoBD.cadastrarJogo
                (new Jogo(nome, descricao, preco, GerenciadorSessao.getSessao().getUsuarioLogado().getEmail()));
    }
}
