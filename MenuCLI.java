package ui;

import model.*;
import service.BibliotecaManager;
import service.PersistenceManager;

import java.util.*;

public class MenuCLI {
    private final Scanner scanner = new Scanner(System.in);
    private BibliotecaManager bibliotecaManager;

    public MenuCLI() {
        bibliotecaManager = new BibliotecaManager();
        bibliotecaManager.carregarOuCriarBiblioteca();
    }

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("\n=== Gerenciador de Biblioteca PDF ===");
            System.out.println("1. Adicionar entrada");
            System.out.println("2. Listar entradas");
            System.out.println("3. Deletar entrada");
            System.out.println("4. Criar nova biblioteca");
            System.out.println("5. Alternar biblioteca");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // consumir nova linha

            switch (opcao) {
                case 1 -> adicionarEntrada();
                case 2 -> listarEntradas();
                case 3 -> deletarEntrada();
                case 4 -> criarNovaBiblioteca();
                case 5 -> alternarBiblioteca();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void adicionarEntrada() {
        System.out.println("\nTipo de entrada: 1-Livro  2-Nota de Aula  3-Slide");
        int tipo = scanner.nextInt();
        scanner.nextLine(); // limpar entrada

        System.out.print("Título: ");
        String titulo = scanner.nextLine();

        System.out.print("Autores (separados por vírgula): ");
        List<String> autores = Arrays.asList(scanner.nextLine().split(","));

        System.out.print("Caminho do arquivo PDF: ");
        String pathPDF = scanner.nextLine();

        EntradaPDF entrada = null;

        switch (tipo) {
            case 1 -> {
                System.out.print("Subtítulo: ");
                String subtitulo = scanner.nextLine();
                System.out.print("Área do conhecimento: ");
                String area = scanner.nextLine();
                System.out.print("Ano de publicação: ");
                int ano = Integer.parseInt(scanner.nextLine());
                System.out.print("Editora (opcional): ");
                String editora = scanner.nextLine();
                System.out.print("Número de páginas (opcional): ");
                String numPag = scanner.nextLine();

                entrada = new Livro(titulo, autores, pathPDF, subtitulo, area, ano,
                        editora.isEmpty() ? null : editora,
                        numPag.isEmpty() ? 0 : Integer.parseInt(numPag));
            }
            case 2 -> {
                System.out.print("Subtítulo: ");
                String subtitulo = scanner.nextLine();
                System.out.print("Disciplina: ");
                String disciplina = scanner.nextLine();
                System.out.print("Instituição (opcional): ");
                String inst = scanner.nextLine();
                System.out.print("Número de páginas (opcional): ");
                String numPag = scanner.nextLine();

                entrada = new NotaDeAula(titulo, autores, pathPDF, subtitulo, disciplina,
                        inst.isEmpty() ? null : inst,
                        numPag.isEmpty() ? 0 : Integer.parseInt(numPag));
            }
            case 3 -> {
                System.out.print("Disciplina: ");
                String disciplina = scanner.nextLine();
                System.out.print("Instituição (opcional): ");
                String inst = scanner.nextLine();

                entrada = new Slide(titulo, autores, pathPDF, disciplina, inst.isEmpty() ? null : inst);
            }
            default -> System.out.println("Tipo inválido.");
        }

        if (entrada != null) {
            bibliotecaManager.adicionarEntrada(entrada);
        }
    }

    private void listarEntradas() {
        bibliotecaManager.listarEntradas();
    }

    private void deletarEntrada() {
        System.out.print("Digite o título da entrada a ser removida: ");
        String titulo = scanner.nextLine();
        bibliotecaManager.deletarEntradaPorTitulo(titulo);
    }

    private void criarNovaBiblioteca() {
        System.out.print("Digite o novo path da biblioteca: ");
        String novoPath = scanner.nextLine();
        bibliotecaManager.criarNovaBiblioteca(novoPath);
    }

    private void alternarBiblioteca() {
        System.out.print("Digite o path de uma biblioteca existente: ");
        String novoPath = scanner.nextLine();
        bibliotecaManager.carregarBibliotecaExistente(novoPath);
    }
}
