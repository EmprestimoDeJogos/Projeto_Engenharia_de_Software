package br.ufmg.engsoft.emprestimojogos.domain;

import java.util.Date;

public class Emprestimo {
	
	private Usuario donoDoJogo;
	private Usuario solicitante;
	private Jogo jogoSolicitado;
	private Date dataLimite;
	
	public Emprestimo(Usuario donoDoJogo, Usuario solicitante, Jogo jogoSolicitado, Date dataLimite) {
		
		this.donoDoJogo = donoDoJogo;
		this.solicitante = solicitante;
		this.jogoSolicitado = jogoSolicitado;
		this.dataLimite = dataLimite;
	}

	public Usuario getDonoDoJogo() {
		return donoDoJogo;
	}

	public Usuario getSolicitante() {
		return solicitante;
	}

	public Jogo getJogoEmprestado() {
		return jogoSolicitado;
	}

	public Date getDataLimite() {
		return dataLimite;
	}
}
