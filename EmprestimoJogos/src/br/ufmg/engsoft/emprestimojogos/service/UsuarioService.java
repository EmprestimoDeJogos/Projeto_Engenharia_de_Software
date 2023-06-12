package br.ufmg.engsoft.emprestimojogos.service;

import br.ufmg.engsoft.emprestimojogos.domain.Jogo;
import br.ufmg.engsoft.emprestimojogos.domain.Usuario;
import br.ufmg.engsoft.emprestimojogos.exceptions.UsuarioInexistenteException;
import br.ufmg.engsoft.emprestimojogos.repository.JogoBD;
import br.ufmg.engsoft.emprestimojogos.repository.UsuarioBD;
import br.ufmg.engsoft.emprestimojogos.session.GerenciadorSessao;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UsuarioService {
    private UsuarioBD usuarioBD;

    public UsuarioService() {
        usuarioBD = UsuarioBD.getInstance();
    }

    public void cadastrarUsuario(String email, String nome, String interesses, String senha) {
        if (email == null || email == "") {
            throw new InputMismatchException("E-mail incorreto ou faltante");
        }

        if (nome == null || nome == "") {
            throw new InputMismatchException("Nome incorreto ou faltante");
        }

        if (senha == null || senha == "") {
            throw new InputMismatchException("Senha incorreta ou faltante");
        }

        usuarioBD.cadastrarUsuario(new Usuario(email, nome, interesses, senha));
    }

    public Usuario getUsuarioPorEmail(String email) {
        List<Usuario> usuarios = UsuarioBD.getInstance().getUsuarios();
        usuarios = usuarios.stream()
                .filter(usuario -> usuario.getEmail().equals(email))
                .toList();

        if (usuarios.isEmpty()) {
            throw new InputMismatchException("Usuário não encontrado.");
        }

        return usuarios.get(0);
    }

    public void verificarSeUsuarioPossuiJogoSelecionado(String nome, String email) {
        List<Jogo> usuarioJogo = JogoBD.getInstance().getJogos();
        usuarioJogo = usuarioJogo.stream()
                .filter(jogo -> jogo.getNome().equals(nome) && jogo.getEmailDono().equals(email))
                .toList();

        if (usuarioJogo.isEmpty()) {
            throw new InputMismatchException("Este usuário não possui o jogo em questão.");
        }
    }

    public void fazerLogin(String email, String senha) {
        if (email == null || email == "") {
            throw new InputMismatchException("E-mail incorreto ou faltante");
        }

        if (senha == null || senha == "") {
            throw new InputMismatchException("Senha incorreta ou faltante");
        }

        Optional<Usuario> logado = usuarioBD.getUsuarios().stream().filter(usuario -> usuario.getEmail().equals(email))
                .findFirst();

        if (logado.isPresent() && logado.get().getSenha().equals(senha)) {
            GerenciadorSessao.getSessao().setUsuarioLogado(logado.get());
        } else {
            throw new UsuarioInexistenteException("O e-mail e senha fornecidos não existem para nenhum usuário");
        }
    }
}
