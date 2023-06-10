package br.ufmg.engsoft.emprestimojogos.controller;

import br.ufmg.engsoft.emprestimojogos.domain.Usuario;
import br.ufmg.engsoft.emprestimojogos.repository.UsuarioBD;
import br.ufmg.engsoft.emprestimojogos.session.GerenciadorSessao;
import br.ufmg.engsoft.emprestimojogos.view.Tela;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Controlador {
    private static Formulario formulario = new Formulario();
    static Scanner scanner = new Scanner(System.in);


    public static void handleMenuHome() {
        int option = scanner.nextInt();

        Tela.mostraSeparadorDeTelas();

        if(option == 1){
            Tela.mostrarCadastro();
        } else if(option == 2) {
            Tela.mostrarLogin();
        } else {
        	System.exit(0);
        }
    }

    public static void handleCadastro() {
        try {
            formulario.formCadastro();
        } catch (InputMismatchException ex) {
            Tela.mostrarErroNaoLogado(ex.getMessage());
        }
        Tela.mostrarLogin();
    }

    public static void handleMenuHomeLogado() {
        int option = scanner.nextInt();
        		
        if(option == 1){
            Tela.mostrarCadastrarJogo();
        } else if (option == 2) {
        	Tela.mostrarCadastrarEmprestimo();
        } else if (option == 3) {
        	Tela.mostrarListagemEmprestimo();
        } else if (option == 4) {
        	System.exit(0);
        } else {
            Tela.mostrarErroLogado("Opção não reconhecida!");
        }
    }

    public static void handleCadastroJogo() {
        try {
            formulario.formCadastroJogo();
        } catch (InputMismatchException ex){
            Tela.mostrarErroLogado(ex.getMessage());
        }
    }
    
    public static void handleCadastroEmprestimo() {
        try {
            formulario.formCadastroEmprestimo();
        } catch (InputMismatchException ex){
            Tela.mostrarErroLogado(ex.getMessage());
        }
    }

    public static void handleLogin() {
        //Código temporário antes de ser feito a tarefa de login
    	//Lucas - agora pegando usuário a partir do banco de dados mockado (temporário tbm)
    	
    	Usuario usuario = UsuarioBD.getInstance().getUsuarios().get(1);
        GerenciadorSessao.getSessao().setUsuarioLogado(usuario);
        Tela.mostrarHomeLogado();
    }
}
