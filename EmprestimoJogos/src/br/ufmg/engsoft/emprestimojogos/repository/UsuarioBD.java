package br.ufmg.engsoft.emprestimojogos.repository;

import br.ufmg.engsoft.emprestimojogos.domain.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioBD {
    private static UsuarioBD instance;
    private List<Usuario> usuariosDB = new ArrayList<Usuario>();

    private UsuarioBD() {
        this.usuariosDB
                .addAll(
                        List.of(
                                new Usuario("admin@email.com", "Admin", "Todos", "admin")
                        )
                );
    }

    public static UsuarioBD getInstance() {
        if (instance == null) {
            instance = new UsuarioBD();
        }
        return instance;
    }

    public void cadastrarUsuario(Usuario usuario){
        usuariosDB.add(usuario);
    }

    public List<Usuario> getUsuarios(){
        return usuariosDB;
    }
}
