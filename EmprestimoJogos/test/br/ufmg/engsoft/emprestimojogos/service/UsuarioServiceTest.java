package br.ufmg.engsoft.emprestimojogos.service;

import br.ufmg.engsoft.emprestimojogos.domain.Usuario;
import br.ufmg.engsoft.emprestimojogos.repository.UsuarioBD;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.InputMismatchException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioServiceTest {

    private UsuarioService usuarioService;
    private UsuarioBD usuarioBD;
    @BeforeEach
    void setUp() {
        usuarioService = new UsuarioService();
        usuarioBD = UsuarioBD.getInstance();
    }

    @Test
    void cadastrarUsuarioSucesso() {
        String nome = "TesteNome";
        String email = "emailteste@mail.com";
        String interesses = "TesteInteresses";
        String senha = "123";

        usuarioService.cadastrarUsuario(email, nome, interesses, senha);

        Optional<Usuario> usuarioCadastrado =
                usuarioBD.getUsuarios().stream()
                        .filter(usuario -> usuario.getEmail().equals(email)).findFirst();

        Assertions.assertTrue(usuarioCadastrado.isPresent());
        Assertions.assertEquals(nome, usuarioCadastrado.get().getNome());
        Assertions.assertEquals(email, usuarioCadastrado.get().getEmail());
        Assertions.assertEquals(interesses, usuarioCadastrado.get().getInteresses());
        Assertions.assertEquals(senha, usuarioCadastrado.get().getSenha());
    }

    @Test
    void cadastroUsuarioEmailVazio(){
        InputMismatchException thrown = Assertions.assertThrows(
                InputMismatchException.class, () -> usuarioService.cadastrarUsuario("", "teste", "teste", "teste"));

        Assertions.assertEquals("E-mail incorreto ou faltante", thrown.getMessage());
    }

    @Test
    void cadastroUsuarioNomeVazio(){
        InputMismatchException thrown = Assertions.assertThrows(
                InputMismatchException.class, () -> usuarioService.cadastrarUsuario("teste", "", "teste", "teste"));

        Assertions.assertEquals("Nome incorreto ou faltante", thrown.getMessage());
    }

    @Test
    void cadastroUsuarioSenhaVazia(){
        InputMismatchException thrown = Assertions.assertThrows(
                InputMismatchException.class, () -> usuarioService.cadastrarUsuario("teste", "teste", "teste", ""));

        Assertions.assertEquals("Senha incorreta ou faltante", thrown.getMessage());
    }
}