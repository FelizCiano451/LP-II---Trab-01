package service;

import model.EntradaPDF;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenceManager {
    private static final String CONFIG_FILE = "config.txt";
    private static final String BIBLIOTECA_FILE = "biblioteca.txt";

    public void salvarPathAtual(String path) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CONFIG_FILE))) {
            writer.write(path);
        } catch (IOException e) {
            System.err.println("Erro ao salvar config: " + e.getMessage());
        }
    }

    public String carregarPathAtual() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CONFIG_FILE))) {
            return reader.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    public void salvarBiblioteca(String pathBiblioteca, List<EntradaPDF> entradas) {
        File file = new File(pathBiblioteca, BIBLIOTECA_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (EntradaPDF e : entradas) {
                writer.write(e.toLinha());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar biblioteca: " + e.getMessage());
        }
    }

    public List<EntradaPDF> carregarBiblioteca(String pathBiblioteca) {
        List<EntradaPDF> entradas = new ArrayList<>();
        File file = new File(pathBiblioteca, BIBLIOTECA_FILE);
        if (!file.exists()) return entradas;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                EntradaPDF entrada = EntradaPDF.fromLinha(linha);
                if (entrada != null) {
                    entradas.add(entrada);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar biblioteca: " + e.getMessage());
        }

        return entradas;
    }

    public boolean bibliotecaExiste(String path) {
        File dir = new File(path);
        return dir.exists() && dir.isDirectory();
    }

    public void copiarArquivoParaBiblioteca(EntradaPDF entrada, String pathBiblioteca) {
        try {
            String autor = entrada.getAutores().isEmpty() ? "Desconhecido" : entrada.getAutores().get(0).trim();
            Path destinoDir = Paths.get(pathBiblioteca, autor);
            Files.createDirectories(destinoDir);

            Path origem = Paths.get(entrada.getPathPDF());
            Path destino = destinoDir.resolve(origem.getFileName());

            Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);
            entrada.setPathPDF(destino.toString());
        } catch (IOException e) {
            System.err.println("Erro ao copiar o arquivo PDF: " + e.getMessage());
        }
    }
}
