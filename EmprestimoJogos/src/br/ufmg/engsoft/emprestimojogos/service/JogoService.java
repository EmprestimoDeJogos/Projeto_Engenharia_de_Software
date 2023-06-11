package br.ufmg.engsoft.emprestimojogos.service;

import br.ufmg.engsoft.emprestimojogos.domain.Emprestimo;
import br.ufmg.engsoft.emprestimojogos.domain.Jogo;
import br.ufmg.engsoft.emprestimojogos.domain.Usuario;
import br.ufmg.engsoft.emprestimojogos.helper.Helper;
import br.ufmg.engsoft.emprestimojogos.repository.JogoBD;
import br.ufmg.engsoft.emprestimojogos.session.GerenciadorSessao;

import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Collectors;

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
    
    public Jogo getJogoPorNome(String nome) {
    	List<Jogo> jogos = JogoBD.getInstance().getJogos();
    	jogos = jogos.stream()
				.filter(jogo -> jogo.getNome().equals(nome))
				.collect(Collectors.toList());
    	
    	if(jogos.isEmpty()) {
    		throw new InputMismatchException("Jogo não encontrado.");
    	}
    	
    	return jogos.get(0);
    }
    
    public List<Jogo> listarJogosPorUsuario(Usuario usuarioLogado) {
    	
    	if(usuarioLogado == null) {
			
			throw new InputMismatchException("Usuário não encontrado.");
			
		} else if(usuarioLogado.getEmail() == null || usuarioLogado.getEmail() == "") {
			
			throw new InputMismatchException("Email de usuário faltante.");
			
		}
    	
    	return this.jogoBD
				   .getJogos()
				   .stream()
				   .filter(jogo -> jogo.getEmailDono().equals(usuarioLogado.getEmail()))
				   .collect(Collectors.toList());
    }
    
	public void imprimirListagemDeJogosPorUsuario(Usuario usuarioLogado, List<Jogo> jogosUsuario) {
			
			if(jogosUsuario.isEmpty()) {
	    		System.out.println("Você não possui nenhum jogo cadastrado!");
	    	}
	    	
	    	else {
	    		System.out.printf("-------------------------------------------------------%n");
	    		System.out.printf("|              Jogos cadastrados de %-18s|%n", usuarioLogado.getNome());
	    		System.out.printf("-------------------------------------------------------%n");
	    		System.out.printf("| %-15s | %-15s | %-15s |%n", "Nome", "Descrição", "Preço");
	    		System.out.printf("-------------------------------------------------------%n");
	    		
	    		for(Jogo jogo : jogosUsuario) {
	    			
			    	System.out.printf("| %-15s | %-15s | %-15s |%n", jogo.getNome(),
			    													 		 jogo.getDescricao(),
			    													 		 jogo.getPreco()
		    		);
	    		}
	
	    		System.out.printf("-------------------------------------------------------%n");
	    	}
		}
}
