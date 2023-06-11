package br.ufmg.engsoft.emprestimojogos.service;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufmg.engsoft.emprestimojogos.domain.*;
import br.ufmg.engsoft.emprestimojogos.helper.Helper;
import br.ufmg.engsoft.emprestimojogos.repository.EmprestimoBD;
import br.ufmg.engsoft.emprestimojogos.session.GerenciadorSessao;

public class EmprestimoServiceTest {
	
	private EmprestimoBD emprestimoBD;
	private EmprestimoService emprestimoService;
	private String emailSessao = "teste@email.com";
	
	@BeforeEach
    void setUp() {
		emprestimoBD = EmprestimoBD.getInstance();
		emprestimoService = new EmprestimoService();
        GerenciadorSessao.getSessao().setUsuarioLogado(new Usuario(emailSessao, "Eduardo", "", "123"));
    }
	
	@Test
    void cadastrarEmpréstimoSucesso() {
		
        Usuario donoDoJogo = new Usuario("teste@email.com", "Lucas", "", "123");
        Usuario solicitante = new Usuario("teste2@email.com", "Lucas2", "", "123");
        Jogo jogoSolicitado = new Jogo("teste", "descricao", 99.99, donoDoJogo.getEmail());
        Date dataLimite = Helper.parsearData("01/01/2024");

        emprestimoService.cadastrarEmprestimo(donoDoJogo, solicitante, jogoSolicitado, dataLimite);

        Optional<Emprestimo> emprestimoCadastrado = emprestimoBD
        											.getEmprestimos()
        											.stream()
        											.filter(emprestimo -> emprestimo
        																  .getDonoDoJogo()
        																  .getEmail()
        																  .equals(emailSessao))
        																  .findFirst();
									        		

        Assertions.assertTrue(emprestimoCadastrado.isPresent());
        Assertions.assertEquals(donoDoJogo, emprestimoCadastrado.get().getDonoDoJogo());
        Assertions.assertEquals(solicitante, emprestimoCadastrado.get().getSolicitante());
        Assertions.assertEquals(jogoSolicitado, emprestimoCadastrado.get().getJogoEmprestado());
        Assertions.assertEquals(dataLimite, emprestimoCadastrado.get().getDataLimite());
    }
	
	@Test
    void cadastrarEmprestimoErroDonoVazio(){
		
		Usuario donoDoJogo = null;
        Usuario solicitante = new Usuario("teste2@email.com", "Lucas2", "", "123");
        Jogo jogoSolicitado = new Jogo("teste", "descricao", 99.99, "");
        Date dataLimite = Helper.parsearData("01/01/2024");
		
        InputMismatchException  thrown = Assertions.assertThrows(
                InputMismatchException.class, () -> emprestimoService
                									.cadastrarEmprestimo(donoDoJogo, solicitante, jogoSolicitado, dataLimite));

        Assertions.assertEquals("Usuário dono do jogo não encontrado.", thrown.getMessage());
    }
	
	@Test
    void cadastrarEmprestimoErroEmailDonoVazio(){
		
		Usuario donoDoJogo = new Usuario("", "Lucas", "", "123");
        Usuario solicitante = new Usuario("teste2@email.com", "Lucas2", "", "123");
        Jogo jogoSolicitado = new Jogo("teste", "descricao", 99.99, donoDoJogo.getEmail());
        Date dataLimite = Helper.parsearData("01/01/2024");
		
        InputMismatchException  thrown = Assertions.assertThrows(
                InputMismatchException.class, () -> emprestimoService
                									.cadastrarEmprestimo(donoDoJogo, solicitante, jogoSolicitado, dataLimite));

        Assertions.assertEquals("Email de usuário dono do jogo faltante.", thrown.getMessage());
    }
	
	@Test
    void cadastrarEmprestimoErroSolicitanteVazio(){
		
		Usuario donoDoJogo = new Usuario("teste@email.com", "Lucas", "", "123");
        Usuario solicitante = null;
        Jogo jogoSolicitado = new Jogo("teste", "descricao", 99.99, donoDoJogo.getEmail());
        Date dataLimite = Helper.parsearData("01/01/2024");
		
        InputMismatchException  thrown = Assertions.assertThrows(
                InputMismatchException.class, () -> emprestimoService
                									.cadastrarEmprestimo(donoDoJogo, solicitante, jogoSolicitado, dataLimite));

        Assertions.assertEquals("Usuário solicitante não encontrado.", thrown.getMessage());
    }
	
	@Test
    void cadastrarEmprestimoErroEmailSolicitanteVazio(){
		
		Usuario donoDoJogo = new Usuario("teste@email.com", "Lucas", "", "123");
		Usuario solicitante = new Usuario("", "Lucas2", "", "123");
        Jogo jogoSolicitado = new Jogo("teste", "descricao", 99.99, donoDoJogo.getEmail());
        Date dataLimite = Helper.parsearData("01/01/2024");
		
        InputMismatchException  thrown = Assertions.assertThrows(
                InputMismatchException.class, () -> emprestimoService
                									.cadastrarEmprestimo(donoDoJogo, solicitante, jogoSolicitado, dataLimite));

        Assertions.assertEquals("Email de usuário solicitante do jogo faltante.", thrown.getMessage());
    }
	
	@Test
    void cadastrarEmprestimoErroJogoVazio(){
		
		Usuario donoDoJogo = new Usuario("teste@email.com", "Lucas", "", "123");
        Usuario solicitante = new Usuario("teste2@email.com", "Lucas2", "", "123");
        Jogo jogoSolicitado = null;
        Date dataLimite = Helper.parsearData("01/01/2024");

		
        InputMismatchException  thrown = Assertions.assertThrows(
                InputMismatchException.class, () -> emprestimoService
                									.cadastrarEmprestimo(donoDoJogo, solicitante, jogoSolicitado, dataLimite));

        Assertions.assertEquals("Nenhum jogo foi selecionado.", thrown.getMessage());
    }
	
	@Test
    void cadastrarEmprestimoErroPrecoJogoInvalido(){
		
		Usuario donoDoJogo = new Usuario("teste@email.com", "Lucas", "", "123");
        Usuario solicitante = new Usuario("teste2@email.com", "Lucas2", "", "123");
        Jogo jogoSolicitado = new Jogo("teste", "descricao", -0.5, donoDoJogo.getEmail());
        Date dataLimite = Helper.parsearData("01/01/2024");

		
        InputMismatchException  thrown = Assertions.assertThrows(
                InputMismatchException.class, () -> emprestimoService
                									.cadastrarEmprestimo(donoDoJogo, solicitante, jogoSolicitado, dataLimite));

        Assertions.assertEquals("Preco incorreto, não pode ser negativo.", thrown.getMessage());
    }
	
	@Test
    void cadastrarEmprestimoErroDataVazia(){
		
		Usuario donoDoJogo = new Usuario("teste@email.com", "Lucas", "", "123");
        Usuario solicitante = new Usuario("teste2@email.com", "Lucas2", "", "123");
        Jogo jogoSolicitado = new Jogo("teste", "descricao", 99.99, donoDoJogo.getEmail());
        Date dataLimite = null;

		
        InputMismatchException  thrown = Assertions.assertThrows(
                InputMismatchException.class, () -> emprestimoService
                									.cadastrarEmprestimo(donoDoJogo, solicitante, jogoSolicitado, dataLimite));

        Assertions.assertEquals("A data limite deve ser posterior à data atual.", thrown.getMessage());
    }
	
	@Test
    void cadastrarEmprestimoErroDataInvalida(){
		
		Usuario donoDoJogo = new Usuario("teste@email.com", "Lucas", "", "123");
        Usuario solicitante = new Usuario("teste2@email.com", "Lucas2", "", "123");
        Jogo jogoSolicitado = new Jogo("teste", "descricao", 99.99, donoDoJogo.getEmail());
        Date dataLimite = Helper.parsearData("09/06/2023");

		
        InputMismatchException  thrown = Assertions.assertThrows(
                InputMismatchException.class, () -> emprestimoService
                									.cadastrarEmprestimo(donoDoJogo, solicitante, jogoSolicitado, dataLimite));

        Assertions.assertEquals("A data limite deve ser posterior à data atual.", thrown.getMessage());
    }
}
