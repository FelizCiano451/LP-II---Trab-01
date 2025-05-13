package service;

import model.EntradaPDF;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BibliotecaManager {
    private String pathBiblioteca;
    private List<EntradaPDF> entradas;
    private PersistenceManager persistence;

    public BibliotecaManager(PersistenceManager persistence) {
        this.persistence = persistence;
        this.pathBiblioteca = persistence.carregarPathAtual();
        this.entradas = new ArrayList<>();

        if (pathBiblioteca != null && persistence.bibliotecaExiste(pathBiblioteca)) {
            this.entradas = persistence.carregarBiblioteca(pathBiblioteca);
        } else {
            this.pathBiblioteca = null;
        }
    }

    public boolean bibliotecaCarregada() {
        return pathBiblioteca != null;
    }

    public void criarNovaBiblioteca(String novoPath) {
        this.pathBiblioteca = novoPath;
        this.entradas = new ArrayList<>();
        persistence.salvarPathAtual(novoPath);
        persistence.salvarBiblioteca(novoPath, entradas);
    }

    public void alternarBiblioteca(String novoPath) {
        if (persistence.bibliotecaExiste(novoPath)) {
            this.pathBiblioteca = novoPath;
            this.entradas = persistence.carregarBiblioteca(novoPath);
            persistence.salvarPathAtual(novoPath);
        } else {
            System.out.println("Biblioteca inexistente.");
        }
    }

    public void adicionarEntrada(EntradaPDF entrada) {
        persistence.copiarArquivoParaBiblioteca(entrada, pathBiblioteca);
        entradas.add(entrada);
        persistence.salvarBiblioteca(pathBiblioteca, entradas);
    }

    public void deletarEntrada(String titulo) {
        entradas.removeIf(e -> e.getTitulo().equalsIgnoreCase(titulo));
        persistence.salvarBiblioteca(pathBiblioteca, entradas);
    }

    public void editarEntrada(String tituloAntigo, EntradaPDF novaEntrada) {
        deletarEntrada(tituloAntigo);
        adicionarEntrada(novaEntrada);
    }

    public List<EntradaPDF> buscarPorTitulo(String termo) {
        return entradas.stream()
                .filter(e -> e.getTitulo().toLowerCase().contains(termo.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<EntradaPDF> listarTodas() {
        return new ArrayList<>(entradas);
    }

    public void deletarBibliotecaAtual() {
        if (pathBiblioteca != null) {
            entradas.clear();
            persistence.salvarBiblioteca(pathBiblioteca, entradas);
        }
    }

    public String getPathBiblioteca() {
        return pathBiblioteca;
    }
}
