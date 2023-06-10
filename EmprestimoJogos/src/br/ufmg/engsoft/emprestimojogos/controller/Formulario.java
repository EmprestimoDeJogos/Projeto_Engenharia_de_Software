package br.ufmg.engsoft.emprestimojogos.controller;

import br.ufmg.engsoft.emprestimojogos.constants.CoresConsole;
import br.ufmg.engsoft.emprestimojogos.service.*;
import br.ufmg.engsoft.emprestimojogos.session.GerenciadorSessao;
import br.ufmg.engsoft.emprestimojogos.repository.*;
import br.ufmg.engsoft.emprestimojogos.domain.*;

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
    	
    	Usuario dono = getUsuarioPorEmail(emailDono);
    	Jogo jogo = getJogoPorNome(nomeJogo);
    	
    	verificarSeUsuarioPossuiJogoSelecionado(nomeJogo, emailDono);
    	
    	Date dataLimite = converterStringParaData(stringDataLimite);
    	
    	emprestimoService.cadastrarEmprestimo(dono, GerenciadorSessao.getSessao().getUsuarioLogado(), jogo, dataLimite);
    }
    
    private static Usuario getUsuarioPorEmail(String email) {
    	List<Usuario> usuarios = UsuarioBD.getInstance().getUsuarios();
    	usuarios = usuarios.stream()
				.filter(usuario -> usuario.getEmail().equals(email))
				.collect(Collectors.toList());
    	
    	if(usuarios.isEmpty()) {
    		throw new InputMismatchException("Usuário não encontrado.");
    	}
    	
    	return usuarios.get(0);
    	
    }
    
    private static Jogo getJogoPorNome(String nome) {
    	List<Jogo> jogos = JogoBD.getInstance().getJogos();
    	jogos = jogos.stream()
				.filter(jogo -> jogo.getNome().equals(nome))
				.collect(Collectors.toList());
    	
    	if(jogos.isEmpty()) {
    		throw new InputMismatchException("Jogo não encontrado.");
    	}
    	
    	return jogos.get(0);
    }
    
    private static void verificarSeUsuarioPossuiJogoSelecionado(String nome, String email) {
    	List<Jogo> usuarioJogo = JogoBD.getInstance().getJogos();
    	usuarioJogo = usuarioJogo.stream()
    				.filter(jogo -> jogo.getNome().equals(nome) && jogo.getEmailDono().equals(email))
    				.collect(Collectors.toList());
    	
    	if(usuarioJogo.isEmpty()) {
    		throw new InputMismatchException("Este usuário não possui o jogo em questão.");
    	}
    }
    
    private static Date converterStringParaData(String dateString) {
    	
    	try {
    		
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date dataLimite = formatter.parse(dateString);
            
            if(dataLimite == null) {
            	throw new InputMismatchException("Data inválida");
            }
            
            return dataLimite;
            
        } catch (ParseException e) {
            e.printStackTrace();
        }
    	
    	return null;
    }
}
