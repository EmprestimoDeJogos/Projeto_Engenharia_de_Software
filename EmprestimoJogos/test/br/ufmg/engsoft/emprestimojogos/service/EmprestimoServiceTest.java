package br.ufmg.engsoft.emprestimojogos.service;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.Optional;

import br.ufmg.engsoft.emprestimojogos.repository.JogoBD;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufmg.engsoft.emprestimojogos.domain.*;
import br.ufmg.engsoft.emprestimojogos.helper.Helper;
import br.ufmg.engsoft.emprestimojogos.repository.EmprestimoBD;
import br.ufmg.engsoft.emprestimojogos.session.GerenciadorSessao;

class EmprestimoServiceTest {
	
	private EmprestimoBD emprestimoBD;

    private JogoBD jogoBD;
	private EmprestimoService emprestimoService;
	private String emailSessao = "teste2@email.com";
    private String emailDono = "teste@email.com";

    private Jogo jogoSolicitado;
	@BeforeEach
    void setUp() {
        jogoBD = JogoBD.getInstance();
		emprestimoBD = EmprestimoBD.getInstance();
		emprestimoService = new EmprestimoService();
        GerenciadorSessao.getSessao().setUsuarioLogado(new Usuario(emailSessao, "Eduardo", "", "123"));
        jogoSolicitado = jogoBD.getJogos().stream().filter(jogo -> jogo.getEmailDono().equals(emailDono)).findFirst().get();
    }

    @AfterAll
    static void tearDown(){
        GerenciadorSessao.limparSessao();
    }
	
	@Test
    void cadastrarEmpréstimoSucesso() {
        Usuario solicitante = GerenciadorSessao.getSessao().getUsuarioLogado();
        Date dataLimite = Helper.parsearData("01/01/2024");

        emprestimoService.cadastrarEmprestimo(emailDono, jogoSolicitado.getNome(), "01/01/2024");

        Optional<Emprestimo> emprestimoCadastrado = emprestimoBD
        											.getEmprestimos()
        											.stream()
        											.filter(emprestimo -> emprestimo
        																  .getDonoDoJogo()
        																  .getEmail()
        																  .equals(emailDono))
        																  .findFirst();
									        		

        Assertions.assertTrue(emprestimoCadastrado.isPresent());
        Assertions.assertEquals(emailDono, emprestimoCadastrado.get().getDonoDoJogo().getEmail());
        Assertions.assertEquals(solicitante, emprestimoCadastrado.get().getSolicitante());
        Assertions.assertEquals(jogoSolicitado, emprestimoCadastrado.get().getJogoEmprestado());
        Assertions.assertEquals(dataLimite, emprestimoCadastrado.get().getDataLimite());
    }

	@Test
    void cadastrarEmprestimoErroEmailDonoVazio(){
        InputMismatchException  thrown = Assertions.assertThrows(
                InputMismatchException.class, () -> emprestimoService
                									.cadastrarEmprestimo
                                                            ("", jogoSolicitado.getNome(), "01/01/2024"));

        Assertions.assertEquals("Usuário dono do jogo não encontrado.", thrown.getMessage());
    }

	@Test
    void cadastrarEmprestimoErroJogoVazio(){
        InputMismatchException  thrown = Assertions.assertThrows(
                InputMismatchException.class, () -> emprestimoService
                									.cadastrarEmprestimo(emailDono, "", "01/01/2024"));

        Assertions.assertEquals("Nenhum jogo foi selecionado.", thrown.getMessage());
    }

	@Test
    void cadastrarEmprestimoErroDataVazia(){
        InputMismatchException  thrown = Assertions.assertThrows(
                InputMismatchException.class, () -> emprestimoService
                									.cadastrarEmprestimo(emailDono, jogoSolicitado.getNome(), ""));

        Assertions.assertEquals("A data limite não pode ser vazia", thrown.getMessage());
    }

	@Test
    void cadastrarEmprestimoErroDataInvalida(){
        InputMismatchException  thrown = Assertions.assertThrows(
                InputMismatchException.class, () -> emprestimoService
                									.cadastrarEmprestimo(emailDono,jogoSolicitado.getNome(), "01/06/2023"));

        Assertions.assertEquals("A data limite deve ser posterior à data atual.", thrown.getMessage());
    }
}
