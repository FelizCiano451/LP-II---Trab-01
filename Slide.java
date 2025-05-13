package model;

import java.util.List;
import java.util.Arrays;

public class Slide extends EntradaPDF {
    private String disciplina;
    private String instituicao;

    public Slide(String titulo, List<String> autores, String pathPDF,
                 String disciplina, String instituicao) {
        super(titulo, autores, pathPDF);
        this.disciplina = disciplina;
        this.instituicao = instituicao;
    }

    public static Slide fromLinha(String linha) {
        String[] partes = linha.split(";");
        String titulo = partes[1];
        List<String> autores = Arrays.asList(partes[2].split(","));
        String pathPDF = partes[3];
        String disciplina = partes[4];
        String instituicao = partes[5];
        return new Slide(titulo, autores, pathPDF, disciplina, instituicao);
    }

    @Override
    public String toLinha() {
        return String.join(";",
            "SLIDE",
            titulo,
            String.join(",", autores),
            pathPDF,
            disciplina,
            instituicao != null ? instituicao : ""
        );
    }

    @Override
    public String getTipo() {
        return "Slide";
    }
}
