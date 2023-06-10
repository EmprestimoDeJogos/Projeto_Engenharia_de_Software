package br.ufmg.engsoft.emprestimojogos.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import br.ufmg.engsoft.emprestimojogos.constants.CoresConsole;
import br.ufmg.engsoft.emprestimojogos.constants.OpcoesMenu;
import br.ufmg.engsoft.emprestimojogos.controller.Controlador;
import br.ufmg.engsoft.emprestimojogos.session.GerenciadorSessao;
import br.ufmg.engsoft.emprestimojogos.domain.*;
import br.ufmg.engsoft.emprestimojogos.repository.EmprestimoBD;

public class Tela {

    private static void printMenu(String[] options){
        for (String option : options){
            System.out.println(option);
        }
        System.out.print("Escolha sua opcão: ");
        System.out.println();
    }

    private static void mostrarErro(String erro){
        System.out.println(
                CoresConsole.RED +
                "Erro: " + erro +
                CoresConsole.RESET
        );
    }
    public static void mostrarLogin() {
        Controlador.handleLogin();
    }

    public static void mostrarCadastro() {
        mostraSeparadorDeTelas();
        Controlador.handleCadastro();
    }

    public static void mostraSeparadorDeTelas(){
        System.out.println();
        System.out.println(
                CoresConsole.CYAN_BOLD +
                "---------------------------------------------------------------------------------------------" +
                CoresConsole.RESET
        );
        System.out.println();
    }
    public static void mostrarHomeLogado(){
        mostraSeparadorDeTelas();
        String nomeUsuario = GerenciadorSessao.getSessao().getUsuarioLogado().getNome();

        System.out.println(
                String.format(
                    CoresConsole.PURPLE +
                    "-----------------  BEM-VINDO(A) %s AO SISTEMA DE EMPRESTIMO DE JOGOS -----------------" +
                    CoresConsole.RESET, nomeUsuario)
        );

        printMenu(OpcoesMenu.HOME_LOGADO);
        Controlador.handleMenuHomeLogado();
    }
    public static void mostrarHome() {
        mostraSeparadorDeTelas();
        System.out.println(
                CoresConsole.BLUE +
                "-----------------  BEM-VINDO AO SISTEMA DE EMPRESTIMO DE JOGOS -----------------" +
                CoresConsole.RESET
        );
        System.out.println(
                CoresConsole.BLUE +
                        "O que deseja fazer?" +
                        CoresConsole.RESET
        );

        printMenu(OpcoesMenu.HOME);
        Controlador.handleMenuHome();
    }

    public static void mostrarErroNaoLogado(String erro) {
        mostrarErro(erro);
        mostrarHome();
    }

    public static void mostrarErroLogado(String erro) {
        mostrarErro(erro);
        mostrarHomeLogado();
    }

    public static void mostrarCadastrarJogo() {
        mostraSeparadorDeTelas();
        Controlador.handleCadastroJogo();
        mostrarHomeLogado();
    }
    
    public static void mostrarCadastrarEmprestimo() {
        mostraSeparadorDeTelas();
        Controlador.handleCadastroEmprestimo();
        mostrarHomeLogado();
    }
    
    public static void mostrarListagemEmprestimo() {
        mostraSeparadorDeTelas();
        showEmprestimosUsuarioLogado();
        mostrarHomeLogado();
    }
    
    private static void showEmprestimosUsuarioLogado() {
    	
    	Usuario usuarioLogado = GerenciadorSessao.getSessao().getUsuarioLogado();
    	
    	List<Emprestimo> emprestimosGerais = EmprestimoBD.getInstance().getEmprestimos();

    	List<Emprestimo> emprestimosUsuario = emprestimosGerais
    										  .stream()
    										  .filter(emprestimo -> emprestimo
    												  				.getSolicitante()
    												  				.getEmail()
    												  				.equals(usuarioLogado.getEmail()))
    										  .collect(Collectors.toList());
    	
    	if(emprestimosUsuario.isEmpty()) {
    		System.out.println("Você não possui nenhum empréstimo cadastrado!");
    	}
    	
    	else {
    		System.out.printf("----------------------------------------%n");
    		System.out.printf("| Empréstimos cadastrados de %10s|%n", usuarioLogado.getNome());
    		System.out.printf("----------------------------------------%n");
    		System.out.printf("| Dono | Jogo solicitado | Data limite |%n");
    		System.out.printf("----------------------------------------%n");
    		
    		for(Emprestimo emprestimo : emprestimosUsuario) {
    			
    			String dataFormatada = "";
    				
    			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    			dataFormatada = formatter.format(emprestimo.getDataLimite());	
    			
	    	System.out.printf("| %-10s | %-10s | %-10s |%n", emprestimo.getDonoDoJogo().getNome(), 
	    																		emprestimo.getJogoEmprestado().getNome(),
	    																		dataFormatada
    		);
    		}

    		System.out.printf("----------------------------------------%n");
    	}
    }
    
}
