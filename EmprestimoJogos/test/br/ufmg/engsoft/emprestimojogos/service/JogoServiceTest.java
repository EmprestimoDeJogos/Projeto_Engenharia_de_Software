package br.ufmg.engsoft.emprestimojogos.service;

import br.ufmg.engsoft.emprestimojogos.domain.Jogo;
import br.ufmg.engsoft.emprestimojogos.domain.Usuario;
import br.ufmg.engsoft.emprestimojogos.repository.JogoBD;
import br.ufmg.engsoft.emprestimojogos.session.GerenciadorSessao;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class JogoServiceTest {

    private JogoBD jogoBD;
    private JogoService jogoService;

    private String emailSessao = "teste2@email.com";
    @BeforeEach
    void setUp() {
        jogoBD = JogoBD.getInstance();
        jogoService = new JogoService();
        GerenciadorSessao.getSessao().setUsuarioLogado(new Usuario(emailSessao, "Eduardo", "", "123"));
    }

    @AfterAll
    static void tearDown(){
        GerenciadorSessao.limparSessao();
    }

    @Test
    void cadastrarJogoSucesso() {
        String nomeJogo = "JogoTeste";
        String descricao = "DescricaoTeste";
        double preco = 10;

        jogoService.cadastrarJogo(nomeJogo, descricao, preco);

        Optional<Jogo> jogoCadastrado =
                jogoBD.getJogos().stream().filter(jogo -> jogo.getEmailDono().equals(emailSessao)).findFirst();

        Assertions.assertTrue(jogoCadastrado.isPresent());
        Assertions.assertEquals(nomeJogo, jogoCadastrado.get().getNome());
        Assertions.assertEquals(descricao, jogoCadastrado.get().getDescricao());
        Assertions.assertEquals(preco, jogoCadastrado.get().getPreco());
    }

    @Test
    void cadastrarJogoErroDescricaoVazia(){
        InputMismatchException  thrown = Assertions.assertThrows(
                InputMismatchException.class, () -> jogoService.cadastrarJogo("teste", "", 0));

        Assertions.assertEquals("Descricao incorreta ou faltante", thrown.getMessage());
    }

    @Test
    void cadastrarJogoErroNomeVazio(){
        InputMismatchException  thrown = Assertions.assertThrows(
                InputMismatchException.class, () -> jogoService.cadastrarJogo("", "teste", 0));

        Assertions.assertEquals("Nome incorreto ou faltante", thrown.getMessage());
    }

    @Test
    void cadastrarJogoPrecoNegativo(){
        InputMismatchException  thrown = Assertions.assertThrows(
                InputMismatchException.class, () -> jogoService.cadastrarJogo("teste", "teste", -1));

        Assertions.assertEquals("Preco incorreto, não pode ser negativo", thrown.getMessage());
    }

}