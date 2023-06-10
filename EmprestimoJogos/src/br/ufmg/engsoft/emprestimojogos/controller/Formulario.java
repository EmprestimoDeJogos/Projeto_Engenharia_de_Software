package br.ufmg.engsoft.emprestimojogos.controller;

import br.ufmg.engsoft.emprestimojogos.constants.CoresConsole;
import br.ufmg.engsoft.emprestimojogos.service.JogoService;
import br.ufmg.engsoft.emprestimojogos.service.UsuarioService;

import java.util.Scanner;

public class Formulario {

    private UsuarioService usuarioService;
    private JogoService jogoService;
    public Formulario() {
        usuarioService = new UsuarioService();
        jogoService = new JogoService();
    }

    static Scanner scanner = new Scanner(System.in);

    public void formCadastro(){
        System.out.println(CoresConsole.GREEN + "Informe os campos a seguir para cadastro: ");
        System.out.println("E-mail: ");
        String email = scanner.nextLine();
        System.out.println("Nome: ");
        String nome = scanner.nextLine();
        System.out.println("Interesses: ");
        String interesses = scanner.nextLine();
        System.out.println("Senha: " + CoresConsole.RESET);
        String senha = scanner.nextLine();

        usuarioService.cadastrarUsuario(email, nome, interesses, senha);
    }

    public void formCadastroJogo() {
        System.out.println(CoresConsole.GREEN + "Informe os campos a seguir para cadastro do jogo: ");
        System.out.println("Nome do jogo: ");
        String nomeJogo = scanner.nextLine();
        System.out.println("Descrição: ");
        String descricao = scanner.nextLine();
        System.out.println("Preco: ");
        double preco = Double.parseDouble(scanner.nextLine());

        jogoService.cadastrarJogo(nomeJogo, descricao, preco);
    }
}
