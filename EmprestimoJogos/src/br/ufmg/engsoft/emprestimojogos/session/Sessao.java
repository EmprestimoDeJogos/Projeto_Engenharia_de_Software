package br.ufmg.engsoft.emprestimojogos.session;

import br.ufmg.engsoft.emprestimojogos.domain.Usuario;

public class Sessao {
    private Usuario usuarioLogado;

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }
}
