public class Pessoa {
    private String nome;
    private String eMail;
    private String senha;

    public Pessoa(String nome) {
        this.nome = nome;
    }

    public String getPessoaNome() {
        return this.nome;
    }

    public void setPessoaNome(String nome) {
        this.nome = nome;
    }

}
