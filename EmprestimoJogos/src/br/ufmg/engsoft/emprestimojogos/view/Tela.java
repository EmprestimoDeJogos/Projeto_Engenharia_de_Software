package br.ufmg.engsoft.emprestimojogos.view;

import br.ufmg.engsoft.emprestimojogos.constants.CoresConsole;
import br.ufmg.engsoft.emprestimojogos.constants.OpcoesMenu;
import br.ufmg.engsoft.emprestimojogos.controller.Controlador;
import br.ufmg.engsoft.emprestimojogos.session.GerenciadorSessao;

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
    }
}
