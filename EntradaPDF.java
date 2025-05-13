package model;

import java.util.List;

public abstract class EntradaPDF {
    protected String titulo;
    protected List<String> autores;
    protected String pathOriginal;

    public EntradaPDF(String titulo, List<String> autores, String pathOriginal) {
        this.titulo = titulo;
        this.autores = autores;
        this.pathOriginal = pathOriginal;
    }

    public abstract void salvarNaBiblioteca(String pathBiblioteca);

    public String getTitulo() {
        return titulo;
    }

    public List<String> getAutores() {
        return autores;
    }

    public String getPathOriginal() {
        return pathOriginal;
    }
}
