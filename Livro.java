package model;

import java.util.List;

public class Livro extends EntradaPDF {
    private String subtitulo;
    private String areaConhecimento;
    private int ano;
    private String editora;
    private int numeroPaginas;

    public Livro(String titulo, List<String> autores, String pathOriginal, String subtitulo,
                 String areaConhecimento, int ano, String editora, int numeroPaginas) {
        super(titulo, autores, pathOriginal);
        this.subtitulo = subtitulo;
        this.areaConhecimento = areaConhecimento;
        this.ano = ano;
        this.editora = editora;
        this.numeroPaginas = numeroPaginas;
    }

    @Override
    public void salvarNaBiblioteca(String pathBiblioteca) {
        System.out.println("Salvando livro em " + pathBiblioteca);
    }
}
