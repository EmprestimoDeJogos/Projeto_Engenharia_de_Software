package br.ufmg.engsoft.emprestimojogos.exceptions;

public class UsuarioInexistenteException extends RuntimeException {
    public UsuarioInexistenteException(String message) {
        super(message);
    }
}
