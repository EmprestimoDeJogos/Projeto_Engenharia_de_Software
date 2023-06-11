package br.ufmg.engsoft.emprestimojogos.controller;

import br.ufmg.engsoft.emprestimojogos.constants.CoresConsole;
import br.ufmg.engsoft.emprestimojogos.service.*;
import br.ufmg.engsoft.emprestimojogos.session.GerenciadorSessao;
import br.ufmg.engsoft.emprestimojogos.repository.*;
import br.ufmg.engsoft.emprestimojogos.domain.*;
import br.ufmg.engsoft.emprestimojogos.helper.Helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Formulario {

    private UsuarioService usuarioService;
    private JogoService jogoService;
    private EmprestimoService emprestimoService;
    
    public Formulario() {
        usuarioService = new UsuarioService();
        jogoService = new JogoService();
        emprestimoService = new EmprestimoService();
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
    
    public void formCadastroEmprestimo() {
    	System.out.println(CoresConsole.GREEN + "Informe os campos a seguir para cadastro do empréstimo: ");
    	System.out.println("Nome do jogo: ");
    	String nomeJogo = scanner.nextLine();
    	System.out.println("Email do dono: ");
    	String emailDono = scanner.nextLine();
    	System.out.println("Data limite proposta: ");
    	String stringDataLimite = scanner.nextLine();
    	
    	Usuario dono = usuarioService.getUsuarioPorEmail(emailDono);
    	Jogo jogo = jogoService.getJogoPorNome(nomeJogo);
    	Date dataLimite = Helper.converterStringParaData(stringDataLimite);
    	
    	usuarioService.verificarSeUsuarioPossuiJogoSelecionado(nomeJogo, emailDono);
    	
    	emprestimoService.cadastrarEmprestimo(dono, GerenciadorSessao.getSessao().getUsuarioLogado(), jogo, dataLimite);
    }
}
