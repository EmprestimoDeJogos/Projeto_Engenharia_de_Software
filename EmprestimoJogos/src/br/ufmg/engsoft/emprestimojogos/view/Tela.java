package br.ufmg.engsoft.emprestimojogos.view;

import java.util.List;
import java.util.stream.Collectors;

import br.ufmg.engsoft.emprestimojogos.constants.CoresConsole;
import br.ufmg.engsoft.emprestimojogos.constants.OpcoesMenu;
import br.ufmg.engsoft.emprestimojogos.controller.Controlador;
import br.ufmg.engsoft.emprestimojogos.session.GerenciadorSessao;
import br.ufmg.engsoft.emprestimojogos.domain.*;
import br.ufmg.engsoft.emprestimojogos.helper.Helper;
import br.ufmg.engsoft.emprestimojogos.repository.EmprestimoBD;
import br.ufmg.engsoft.emprestimojogos.service.EmprestimoService;
import br.ufmg.engsoft.emprestimojogos.service.JogoService;

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
        System.out.println("Cadastro de jogo realizado com sucesso.");
        mostrarHomeLogado();
    }
    
    public static void mostrarListagemJogo() {
        mostraSeparadorDeTelas();
        mostrarJogosUsuarioLogado();
        mostrarHomeLogado();
    }
    
    public static void mostrarCadastrarEmprestimo() {
        mostraSeparadorDeTelas();
        Controlador.handleCadastroEmprestimo();
        System.out.println("Cadastro de empréstimo realizado com sucesso.");
        mostrarHomeLogado();
    }
    
    public static void mostrarListagemEmprestimo() {
        mostraSeparadorDeTelas();
        mostrarEmprestimosUsuarioLogado();
        mostrarHomeLogado();
    }
    
	private static void mostrarJogosUsuarioLogado() {
	    	
	    JogoService jogoService = new JogoService();
	    	
	    Usuario usuarioLogado = GerenciadorSessao.getSessao().getUsuarioLogado();
	
	    List<Jogo> jogosUsuario = jogoService.listarJogosPorUsuario(usuarioLogado);
	    	
	    jogoService.imprimirListagemDeJogosPorUsuario(usuarioLogado, jogosUsuario);
	    }
    
    private static void mostrarEmprestimosUsuarioLogado() {
    	
    	EmprestimoService emprestimoService = new EmprestimoService();
    	
    	Usuario usuarioLogado = GerenciadorSessao.getSessao().getUsuarioLogado();

    	List<Emprestimo> emprestimosUsuario = emprestimoService.listarEmprestimosPorUsuario(usuarioLogado);
    	
    	emprestimoService.imprimirListagemDeEmprestimosPorUsuario(usuarioLogado, emprestimosUsuario);
    }
}
