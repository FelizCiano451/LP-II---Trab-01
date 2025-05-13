package model;

import java.util.List;

public class NotaDeAula extends EntradaPDF {
    private String subtitulo;
    private String disciplina;
    private String instituicao;
    private int numeroPaginas;

    public NotaDeAula(String titulo, List<String> autores, String pathOriginal, String subtitulo,
                      String disciplina, String instituicao, int numeroPaginas) {
        super(titulo, autores, pathOriginal);
        this.subtitulo = subtitulo;
        this.disciplina = disciplina;
        this.instituicao = instituicao;
        this.numeroPaginas = numeroPaginas;
    }

    @Override
    public void salvarNaBiblioteca(String pathBiblioteca) {
        System.out.println("Salvando nota de aula em " + pathBiblioteca);
    }
}
