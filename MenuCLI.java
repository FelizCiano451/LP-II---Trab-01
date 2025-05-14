package ui;

import model.*;
import service.BibliotecaManager;
import service.PersistenceManager;

import java.util.*;

public class MenuCLI {
    private Scanner scanner;
    private BibliotecaManager bibliotecaManager;

    public MenuCLI() {
        scanner = new Scanner(System.in);
        bibliotecaManager = new BibliotecaManager(new PersistenceManager());
    }

    public void exibirMenu() {
        System.out.println("=== GERENCIADOR DE BIBLIOTECA PDF ===");

        if (!bibliotecaManager.bibliotecaCarregada()) {
            System.out.print("Digite o caminho para criar uma nova biblioteca: ");
            String path = scanner.nextLine();
            bibliotecaManager.criarNovaBiblioteca(path);
            System.out.println("Biblioteca criada com sucesso.");
        }

        int opcao = -1;
        do {
            System.out.println("\n[1] Adicionar entrada");
            System.out.println("[2] Listar entradas");
            System.out.println("[3] Buscar entrada por título");
            System.out.println("[4] Editar entrada");
            System.out.println("[5] Deletar entrada");
            System.out.println("[6] Criar nova biblioteca");
            System.out.println("[7] Alternar biblioteca");
            System.out.println("[8] Deletar biblioteca atual");
            System.out.println("[0] Sair");
            System.out.print("Escolha: ");
            opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1 -> adicionarEntrada();
                case 2 -> listarEntradas();
                case 3 -> buscarEntrada();
                case 4 -> editarEntrada();
                case 5 -> deletarEntrada();
                case 6 -> criarNovaBiblioteca();
                case 7 -> alternarBiblioteca();
                case 8 -> deletarBiblioteca();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void adicionarEntrada() {
        System.out.println("Qual tipo de entrada deseja adicionar?");
        System.out.println("[1] Livro | [2] Nota de Aula | [3] Slide");
        int tipo = Integer.parseInt(scanner.nextLine());

        System.out.print("Título: ");
        String titulo = scanner.nextLine();

        System.out.print("Autores (separados por vírgula): ");
        List<String> autores = Arrays.asList(scanner.nextLine().split(","));

        System.out.print("Caminho do arquivo PDF: ");
        String pathPDF = scanner.nextLine();

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
                int paginas = Integer.parseInt(scanner.nextLine());

                Livro livro = new Livro(titulo, autores, pathPDF, subtitulo, area, ano, editora, paginas);
                bibliotecaManager.adicionarEntrada(livro);
            }
            case 2 -> {
                System.out.print("Subtítulo: ");
                String subtitulo = scanner.nextLine();

                System.out.print("Disciplina: ");
                String disciplina = scanner.nextLine();

                System.out.print("Instituição (opcional): ");
                String instituicao = scanner.nextLine();

                System.out.print("Número de páginas (opcional): ");
                int paginas = Integer.parseInt(scanner.nextLine());

                NotaDeAula nota = new NotaDeAula(titulo, autores, pathPDF, subtitulo, disciplina, instituicao, paginas);
                bibliotecaManager.adicionarEntrada(nota);
            }
            case 3 -> {
                System.out.print("Disciplina: ");
                String disciplina = scanner.nextLine();

                System.out.print("Instituição (opcional): ");
                String instituicao = scanner.nextLine();

                Slide slide = new Slide(titulo, autores, pathPDF, disciplina, instituicao);
                bibliotecaManager.adicionarEntrada(slide);
            }
            default -> System.out.println("Tipo inválido.");
        }
    }

    private void listarEntradas() {
        List<EntradaPDF> entradas = bibliotecaManager.listarTodas();
        if (entradas.isEmpty()) {
            System.out.println("Nenhuma entrada encontrada.");
        } else {
            for (EntradaPDF e : entradas) {
                System.out.println("[" + e.getTipo() + "] " + e.getTitulo() + " - " + e.getAutores());
            }
        }
    }

    private void buscarEntrada() {
        System.out.print("Digite o título ou parte do título: ");
        String termo = scanner.nextLine();

        List<EntradaPDF> resultados = bibliotecaManager.buscarPorTitulo(termo);
        if (resultados.isEmpty()) {
            System.out.println("Nenhuma entrada encontrada.");
        } else {
            for (EntradaPDF e : resultados) {
                System.out.println("[" + e.getTipo() + "] " + e.getTitulo() + " - " + e.getAutores());
            }
        }
    }

    private void editarEntrada() {
        System.out.print("Digite o título da entrada que deseja editar: ");
        String tituloAntigo = scanner.nextLine();
        deletarEntrada(tituloAntigo);
        System.out.println("Informe os novos dados:");
        adicionarEntrada();
    }

    private void deletarEntrada() {
        System.out.print("Digite o título da entrada que deseja deletar: ");
        String titulo = scanner.nextLine();
        bibliotecaManager.deletarEntrada(titulo);
        System.out.println("Entrada removida.");
    }

    private void deletarEntrada(String titulo) {
        bibliotecaManager.deletarEntrada(titulo);
    }

    private void criarNovaBiblioteca() {
        System.out.print("Digite o caminho da nova biblioteca: ");
        String path = scanner.nextLine();
        bibliotecaManager.criarNovaBiblioteca(path);
        System.out.println("Biblioteca criada.");
    }

    private void alternarBiblioteca() {
        System.out.print("Digite o caminho da biblioteca existente: ");
        String path = scanner.nextLine();
        bibliotecaManager.alternarBiblioteca(path);
    }

    private void deletarBiblioteca() {
        bibliotecaManager.deletarBibliotecaAtual();
        System.out.println("Biblioteca apagada (entradas removidas).");
    }
}
