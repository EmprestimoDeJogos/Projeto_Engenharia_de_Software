package br.ufmg.engsoft.emprestimojogos.session;

public class GerenciadorSessao {
    private static Sessao sessao;

    public static Sessao getSessao(){
        if(sessao == null){
            sessao = new Sessao();
        }

        return sessao;
    }

    public static void limparSessao(){
        sessao = null;
    }
}
