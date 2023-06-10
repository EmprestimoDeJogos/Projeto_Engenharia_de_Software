package br.ufmg.engsoft.emprestimojogos.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import br.ufmg.engsoft.emprestimojogos.domain.*;
import br.ufmg.engsoft.emprestimojogos.repository.*;
import br.ufmg.engsoft.emprestimojogos.session.GerenciadorSessao;

public class EmprestimoBD {
	
	private static EmprestimoBD instance;
	private List<Emprestimo> emprestimosDB = new ArrayList<Emprestimo>();
	
	private EmprestimoBD() {
		
		List<Jogo> jogos = JogoBD.getInstance().getJogos();
		Usuario dono = UsuarioBD.getInstance().getUsuarios().get(0);
		Usuario solicitante = null;
		Date dataLimite = null;
		Usuario usuarioLogado = GerenciadorSessao.getSessao().getUsuarioLogado();
		
		if(usuarioLogado != null) {
			solicitante = usuarioLogado;
		} else {
			solicitante = UsuarioBD.getInstance().getUsuarios().get(1);
		}
		
		
		try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            dataLimite = formatter.parse("01/07/2023");
        } catch (ParseException e) {
            e.printStackTrace();
        }
		
		this.emprestimosDB.addAll(List.of(
											new Emprestimo(dono, solicitante, jogos.get(0), dataLimite)
										)
								);
	}
	
	public static EmprestimoBD getInstance() {
        if (instance == null) {
            instance = new EmprestimoBD();
        }
        return instance;
    }
	
	public void cadastrarEmprestimo(Emprestimo emprestimo){
		emprestimosDB.add(emprestimo);
    }

    public List<Emprestimo> getEmprestimos(){
        return emprestimosDB;
    }
	
}
