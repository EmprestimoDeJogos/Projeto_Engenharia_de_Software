public class Jogo {

    private String nomeJogo;
    private String categoria;
    private int id;
    private Pessoa dono;

    public Jogo(String nomeJogo, String categoria, int id, Pessoa dono) {
        this.nomeJogo = nomeJogo;
        this.categoria = categoria;
        this.id = id;
        this.dono = dono;

    }

    public String getNomeJogo() {
        return this.nomeJogo;
    }

    public void setNome(String nomeJogo) {
        this.nomeJogo = nomeJogo;
    }

    public String getCategoria() {
        return this.categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeDono() {
        return this.dono.getPessoaNome();
    }

}
