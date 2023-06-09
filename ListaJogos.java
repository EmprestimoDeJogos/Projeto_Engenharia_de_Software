import java.io.*;
import java.util.ArrayList;

public class ListaJogos {
    private ArrayList<Jogo> jogos;
    private String arquivoBancoDados;

    public ListaJogos(String arquivoBancoDados) {
        this.arquivoBancoDados = arquivoBancoDados;
        jogos = new ArrayList<>();
    }

    public void adicionarJogo(Jogo jogo) {
        jogos.add(jogo);
    }

    public void visualizarJogos() {
        if (jogos.isEmpty()) {
            System.out.println("Nenhum jogo encontrado.");
            return;
        }

        System.out.println("Lista de Jogos:");
        for (Jogo jogo : jogos) {
            System.out.println("Nome: " + jogo.getNomeJogo());
            System.out.println("ID: " + jogo.getId());
            System.out.println("Nome do Dono: " + jogo.getNomeDono());
            System.out.println("--------------------------");
        }
    }

    public void salvarNoBancoDados() {
        try {
            FileOutputStream fileOut = new FileOutputStream(arquivoBancoDados);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(jogos);
            objectOut.close();
            fileOut.close();
            System.out.println("Os jogos foram salvos no banco de dados.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar no banco de dados: " + e.getMessage());
        }
    }

    public void carregarDoBancoDados() {
        try {
            FileInputStream fileIn = new FileInputStream(arquivoBancoDados);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            jogos = (ArrayList<Jogo>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            System.out.println("Os jogos foram carregados do banco de dados.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar do banco de dados: " + e.getMessage());
        }
    }
}