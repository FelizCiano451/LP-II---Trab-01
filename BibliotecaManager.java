package service;

import model.EntradaPDF;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BibliotecaManager {
    private List<EntradaPDF> entradas;
    private String pathBiblioteca;
    private final PersistenceManager persistenceManager;

    public BibliotecaManager() {
        this.persistenceManager = new PersistenceManager();
        this.pathBiblioteca = persistenceManager.carregarPathAtual();
        this.entradas = new ArrayList<>();

        if (pathBiblioteca != null && persistenceManager.bibliotecaExiste(pathBiblioteca)) {
            this.entradas = persistenceManager.carregarBiblioteca(pathBiblioteca);
        } else {
            System.out.println("Nenhuma biblioteca válida encontrada.");
        }
    }

    public void carregarOuCriarBiblioteca() {
        if (pathBiblioteca == null || !persistenceManager.bibliotecaExiste(pathBiblioteca)) {
            System.out.println("Criando nova biblioteca padrão em ./BibliotecaPDF");
            pathBiblioteca = "./BibliotecaPDF";
            criarNovaBiblioteca(pathBiblioteca);
        }
    }

    public void adicionarEntrada(EntradaPDF entrada) {
        entradas.add(entrada);
        persistenceManager.copiarArquivoParaBiblioteca(entrada, pathBiblioteca);
        persistenceManager.salvarBiblioteca(pathBiblioteca, entradas);
        System.out.println("Entrada adicionada com sucesso.");
    }

    public void listarEntradas() {
        if (entradas.isEmpty()) {
            System.out.println("A biblioteca está vazia.");
            return;
        }

        System.out.println("\n=== Entradas na Biblioteca ===");
        for (EntradaPDF entrada : entradas) {
            System.out.println(entrada);
            System.out.println("--------------------------");
        }
    }

    public void deletarEntradaPorTitulo(String titulo) {
        boolean removido = entradas.removeIf(e -> e.getTitulo().equalsIgnoreCase(titulo));
        if (removido) {
            persistenceManager.salvarBiblioteca(pathBiblioteca, entradas);
            System.out.println("Entrada removida.");
        } else {
            System.out.println("Nenhuma entrada com esse título encontrada.");
        }
    }

    public void criarNovaBiblioteca(String novoPath) {
        File dir = new File(novoPath);
        if (!dir.exists()) {
            dir.mkdirs();
            System.out.println("Nova biblioteca criada em: " + novoPath);
        }

        this.pathBiblioteca = novoPath;
        this.entradas = new ArrayList<>();
        persistenceManager.salvarPathAtual(novoPath);
        persistenceManager.salvarBiblioteca(novoPath, entradas);
    }

    public void carregarBibliotecaExistente(String path) {
        if (persistenceManager.bibliotecaExiste(path)) {
            this.pathBiblioteca = path;
            this.entradas = persistenceManager.carregarBiblioteca(path);
            persistenceManager.salvarPathAtual(path);
            System.out.println("Biblioteca carregada com sucesso.");
        } else {
            System.out.println("Biblioteca não encontrada nesse path.");
        }
    }
}
