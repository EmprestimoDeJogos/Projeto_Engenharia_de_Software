package br.ufmg.engsoft.emprestimojogos.service;

import br.ufmg.engsoft.emprestimojogos.domain.Usuario;
import br.ufmg.engsoft.emprestimojogos.repository.UsuarioBD;

import java.util.InputMismatchException;

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
}
