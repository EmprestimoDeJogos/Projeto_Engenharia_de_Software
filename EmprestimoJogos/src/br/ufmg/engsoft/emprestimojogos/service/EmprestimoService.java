package br.ufmg.engsoft.emprestimojogos.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Collectors;

import br.ufmg.engsoft.emprestimojogos.domain.*;
import br.ufmg.engsoft.emprestimojogos.repository.EmprestimoBD;
import br.ufmg.engsoft.emprestimojogos.session.GerenciadorSessao;
import br.ufmg.engsoft.emprestimojogos.helper.*;

public class EmprestimoService {
	
	private EmprestimoBD emprestimoBD;
	
	public EmprestimoService() {
		emprestimoBD = EmprestimoBD.getInstance();
	}
	
	public void cadastrarEmprestimo(Usuario dono, Usuario solicitante, Jogo jogoSolicitado, Date dataLimite) {
		
		if(dono == null) {
			
			 throw new InputMismatchException("Usuário dono do jogo não encontrado.");
			 
		} else if(dono.getEmail() == null || dono.getEmail() == "") {
			
			throw new InputMismatchException("Email de usuário dono do jogo faltante.");
			
		}
		
		if(solicitante == null) {
			
			 throw new InputMismatchException("Usuário solicitante não encontrado.");
			 
		} else if(solicitante.getEmail() == null || solicitante.getEmail() == "") {
			
			throw new InputMismatchException("Email de usuário solicitante do jogo faltante.");
			
		}
		
		if(jogoSolicitado == null) {
			
			throw new InputMismatchException("Nenhum jogo foi selecionado.");
			
		}else if(jogoSolicitado.getPreco() < 0) {
			
			throw new InputMismatchException("Preco incorreto, não pode ser negativo.");
			
		}
		
		if(dataLimite == null || !validarDataLimite(dataLimite)) {
			throw new InputMismatchException("A data limite deve ser posterior à data atual.");
		}
		
		emprestimoBD.cadastrarEmprestimo(new Emprestimo(dono, solicitante, jogoSolicitado, dataLimite));
	}
	
	public List<Emprestimo> listarEmprestimosPorUsuario(Usuario usuarioLogado) {
		
		if(usuarioLogado == null) {
			
			throw new InputMismatchException("Usuário não encontrado.");
			
		} else if(usuarioLogado.getEmail() == null || usuarioLogado.getEmail() == "") {
			
			throw new InputMismatchException("Email de usuário faltante.");
			
		}
		
		return this.emprestimoBD
				   .getEmprestimos()
				   .stream()
				   .filter(emprestimo -> emprestimo.getSolicitante().getEmail().equals(usuarioLogado.getEmail()))
				   .collect(Collectors.toList());
	}
	
	private static boolean validarDataLimite(Date dataLimite) {
		
       Date hoje = Helper.getDataAtualSemHoras();
       
       if(hoje.compareTo(dataLimite) < 0) {
    	   return true;
       }
       
		return false;
	}
	
	public void imprimirListagemDeEmprestimosPorUsuario(Usuario usuarioLogado, List<Emprestimo> emprestimosUsuario) {
		
		if(emprestimosUsuario.isEmpty()) {
    		System.out.println("Você não possui nenhum empréstimo cadastrado!");
    	}
    	
    	else {
    		System.out.printf("-------------------------------------------------------%n");
    		System.out.printf("|        Empréstimos cadastrados de %-18s|%n", usuarioLogado.getNome());
    		System.out.printf("-------------------------------------------------------%n");
    		System.out.printf("| %-15s | %-15s | %-15s |%n", "Dono", "Jogo solicitado", "Data limite");
    		System.out.printf("-------------------------------------------------------%n");
    		
    		for(Emprestimo emprestimo : emprestimosUsuario) {
    			
    			String dataFormatada = Helper.formatarDataParaString(emprestimo.getDataLimite());
    			
		    	System.out.printf("| %-15s | %-15s | %-15s |%n", emprestimo.getDonoDoJogo().getNome(), 
		    													 emprestimo.getJogoEmprestado().getNome(),
		    													 dataFormatada
	    		);
    		}

    		System.out.printf("-------------------------------------------------------%n");
    	}
	}
}
