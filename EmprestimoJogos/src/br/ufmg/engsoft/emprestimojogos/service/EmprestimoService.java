package br.ufmg.engsoft.emprestimojogos.service;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Collectors;

import br.ufmg.engsoft.emprestimojogos.domain.*;
import br.ufmg.engsoft.emprestimojogos.repository.EmprestimoBD;
import br.ufmg.engsoft.emprestimojogos.session.GerenciadorSessao;
import br.ufmg.engsoft.emprestimojogos.helper.*;

public class EmprestimoService {

	public static final String linhaHifen = "-------------------------------------------------------%n";
	private EmprestimoBD emprestimoBD;
	private UsuarioService usuarioService;
	private JogoService jogoService;
	
	public EmprestimoService() {
		emprestimoBD = EmprestimoBD.getInstance();
		usuarioService = new UsuarioService();
		jogoService = new JogoService();
	}
	
	public void cadastrarEmprestimo(String emailDono, String nomeJogo, String stringDataLimite) {
		if(stringDataLimite == null || stringDataLimite == ""){
			throw new InputMismatchException("A data limite não pode ser vazia");
		}

		if(emailDono == null || emailDono == "") {
			throw new InputMismatchException("Usuário dono do jogo não encontrado.");
		}

		if(nomeJogo == null || nomeJogo == "") {
			throw new InputMismatchException("Nenhum jogo foi selecionado.");
		}

		Date dataLimite = Helper.converterStringParaData(stringDataLimite);

		if(dataLimite == null || !validarDataLimite(dataLimite)) {
			throw new InputMismatchException("A data limite deve ser posterior à data atual.");
		}

		Usuario dono = usuarioService.getUsuarioPorEmail(emailDono);
		Jogo jogoSolicitado = jogoService.getJogoPorNome(nomeJogo);

		usuarioService.verificarSeUsuarioPossuiJogoSelecionado(nomeJogo, emailDono);
		
		emprestimoBD.cadastrarEmprestimo
				(new Emprestimo(dono, GerenciadorSessao.getSessao().getUsuarioLogado(), jogoSolicitado, dataLimite));
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
				   .toList();
	}
	
	private static boolean validarDataLimite(Date dataLimite) {
       Date hoje = Helper.getDataAtualSemHoras();

	   return hoje.compareTo(dataLimite) < 0;
	}
	
	public void imprimirListagemDeEmprestimosPorUsuario(Usuario usuarioLogado, List<Emprestimo> emprestimosUsuario) {
		
		if(emprestimosUsuario.isEmpty()) {
    		System.out.println("Você não possui nenhum empréstimo cadastrado!");
    	}
    	
    	else {
    		System.out.printf(linhaHifen);
    		System.out.printf("|        Empréstimos cadastrados de %-18s|%n", usuarioLogado.getNome());
    		System.out.printf(linhaHifen);
    		System.out.printf("| %-15s | %-15s | %-15s |%n", "Dono", "Jogo solicitado", "Data limite");
    		System.out.printf(linhaHifen);
    		
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
