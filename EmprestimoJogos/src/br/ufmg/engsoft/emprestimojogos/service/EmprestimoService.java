package br.ufmg.engsoft.emprestimojogos.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Collectors;

import br.ufmg.engsoft.emprestimojogos.domain.*;
import br.ufmg.engsoft.emprestimojogos.repository.EmprestimoBD;

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
			
			throw new InputMismatchException("Preco incorreto, não pode ser negativo");
			
		}
		
		if(!validarDataLimite(dataLimite) || dataLimite == null) {
			throw new InputMismatchException("A data limite deve ser posterior à data atual.");
		}
		
		emprestimoBD.cadastrarEmprestimo(new Emprestimo(dono, solicitante, jogoSolicitado, dataLimite));
	}
	
	public List<Emprestimo> listarEmprestimosPorUsuario(Usuario solicitante) {
		
		if(solicitante == null) {
			
			throw new InputMismatchException("Usuário solicitante não encontrado.");
			
		} else if(solicitante.getEmail() == null || solicitante.getEmail() == "") {
			
			throw new InputMismatchException("Email de usuário solicitante do jogo faltante.");
			
		}
		
		return this.emprestimoBD
				   .getEmprestimos()
				   .stream()
				   .filter(emprestimo -> emprestimo.getSolicitante().getEmail() == solicitante.getEmail())
				   .collect(Collectors.toList());
	}
	
	private static boolean validarDataLimite(Date dataLimite) {
		
		try {
			
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
           
            Date hoje = formatter.parse(formatter.format(new Date()));
            
            if(hoje.compareTo(dataLimite) < 0) {
            	return true;
            }
            
        } catch (ParseException e) {
            e.printStackTrace();
        }
		
		return false;
	}
}
