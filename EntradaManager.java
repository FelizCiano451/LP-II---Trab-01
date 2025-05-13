package service;

import model.EntradaPDF;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EntradaManager {

    public static void salvarEntrada(EntradaPDF entrada, String pathBiblioteca) {
        if (entrada.getAutores().isEmpty()) {
            System.err.println("A entrada precisa de pelo menos um autor.");
            return;
        }

        String nomeAutor = entrada.getAutores().get(0).replaceAll("\\s+", "_");
        Path diretorioAutor = Paths.get(pathBiblioteca, nomeAutor);

        try {
            Files.createDirectories(diretorioAutor);
            File origem = new File(entrada.getPathOriginal());
            if (!origem.exists()) {
                System.err.println("Arquivo PDF de origem não encontrado.");
                return;
            }

            String nomeArquivo = entrada.getTitulo().replaceAll("[^a-zA-Z0-9\.\-]", "_") + ".pdf";
            Path destino = diretorioAutor.resolve(nomeArquivo);
            Files.copy(origem.toPath(), destino);

            System.out.println("Arquivo copiado para: " + destino.toString());

        } catch (IOException e) {
            System.err.println("Erro ao salvar entrada: " + e.getMessage());
        }
    }
}
