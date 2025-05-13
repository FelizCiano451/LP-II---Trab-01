package model;

import java.util.List;

public class Slide extends EntradaPDF {
    private String disciplina;
    private String instituicao;

    public Slide(String titulo, List<String> autores, String pathOriginal,
                 String disciplina, String instituicao) {
        super(titulo, autores, pathOriginal);
        this.disciplina = disciplina;
        this.instituicao = instituicao;
    }

    @Override
    public void salvarNaBiblioteca(String pathBiblioteca) {
        System.out.println("Salvando slide em " + pathBiblioteca);
    }
}
