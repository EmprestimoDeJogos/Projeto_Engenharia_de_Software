package br.ufmg.engsoft.emprestimojogos.controller;

import br.ufmg.engsoft.emprestimojogos.exceptions.UsuarioInexistenteException;
import br.ufmg.engsoft.emprestimojogos.session.GerenciadorSessao;
import br.ufmg.engsoft.emprestimojogos.view.Tela;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Controlador {
    private static Formulario formulario = new Formulario();
    static Scanner scanner = new Scanner(System.in);

    private Controlador() {
    }

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
        		
        if(option == 1) {
            Tela.mostrarCadastrarJogo();
        } else if(option == 2) {
        	Tela.mostrarListagemJogo();
        } else if (option == 3) {
        	Tela.mostrarCadastrarEmprestimo();
        } else if (option == 4) {
            Tela.mostrarListagemEmprestimo();
        } else if (option == 5) {
            GerenciadorSessao.limparSessao();
            Tela.mostrarHome();
        } else if (option == 6) {
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
        try {
            formulario.formLogin();
        } catch (UsuarioInexistenteException ex){
            Tela.mostrarErroNaoLogado(ex.getMessage());
        }

        Tela.mostrarHomeLogado();
    }
}
