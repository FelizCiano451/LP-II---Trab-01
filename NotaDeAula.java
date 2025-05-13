package model;

import java.util.List;
import java.util.Arrays;

public class NotaDeAula extends EntradaPDF {
    private String subtitulo;
    private String disciplina;
    private String instituicao;
    private int numPaginas;

    public NotaDeAula(String titulo, List<String> autores, String pathPDF,
                      String subtitulo, String disciplina, String instituicao, int numPaginas) {
        super(titulo, autores, pathPDF);
        this.subtitulo = subtitulo;
        this.disciplina = disciplina;
        this.instituicao = instituicao;
        this.numPaginas = numPaginas;
    }

    public static NotaDeAula fromLinha(String linha) {
        String[] partes = linha.split(";");
        String titulo = partes[1];
        List<String> autores = Arrays.asList(partes[2].split(","));
        String pathPDF = partes[3];
        String subtitulo = partes[4];
        String disciplina = partes[5];
        String instituicao = partes[6];
        int paginas = Integer.parseInt(partes[7]);
        return new NotaDeAula(titulo, autores, pathPDF, subtitulo, disciplina, instituicao, paginas);
    }

    @Override
    public String toLinha() {
        return String.join(";",
            "NOTA",
            titulo,
            String.join(",", autores),
            pathPDF,
            subtitulo,
            disciplina,
            instituicao != null ? instituicao : "",
            String.valueOf(numPaginas)
        );
    }

    @Override
    public String getTipo() {
        return "Nota de Aula";
    }
}
