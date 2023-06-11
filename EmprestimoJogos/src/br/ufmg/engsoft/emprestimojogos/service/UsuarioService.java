package br.ufmg.engsoft.emprestimojogos.service;

import br.ufmg.engsoft.emprestimojogos.domain.Jogo;
import br.ufmg.engsoft.emprestimojogos.domain.Usuario;
import br.ufmg.engsoft.emprestimojogos.repository.JogoBD;
import br.ufmg.engsoft.emprestimojogos.repository.UsuarioBD;

import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioService {
    private UsuarioBD usuarioBD;
    public UsuarioService() {
        usuarioBD = UsuarioBD.getInstance();
    }

    public void cadastrarUsuario(String email, String nome, String interesses, String senha){
        if(email == null || email == ""){
            throw new InputMismatchException("E-mail incorreto ou faltante");
        }

        if(nome == null || nome == ""){
            throw new InputMismatchException("Nome incorreto ou faltante");
        }

        if(senha == null || senha == ""){
            throw new InputMismatchException("Senha incorreta ou faltante");
        }

        usuarioBD.cadastrarUsuario(new Usuario(email, nome, interesses, senha));
    }
    
    public Usuario getUsuarioPorEmail(String email) {
    	List<Usuario> usuarios = UsuarioBD.getInstance().getUsuarios();
    	usuarios = usuarios.stream()
				.filter(usuario -> usuario.getEmail().equals(email))
				.collect(Collectors.toList());
    	
    	if(usuarios.isEmpty()) {
    		throw new InputMismatchException("Usuário não encontrado.");
    	}
    	
    	return usuarios.get(0);
    }
    
    public void verificarSeUsuarioPossuiJogoSelecionado(String nome, String email) {
    	List<Jogo> usuarioJogo = JogoBD.getInstance().getJogos();
    	usuarioJogo = usuarioJogo.stream()
    				.filter(jogo -> jogo.getNome().equals(nome) && jogo.getEmailDono().equals(email))
    				.collect(Collectors.toList());
    	
    	if(usuarioJogo.isEmpty()) {
    		throw new InputMismatchException("Este usuário não possui o jogo em questão.");
    	}
    }
}
