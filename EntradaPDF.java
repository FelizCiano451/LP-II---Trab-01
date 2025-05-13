package model;

import java.util.List;
import java.util.Arrays;

public abstract class EntradaPDF {
    protected String titulo;
    protected List<String> autores;
    protected String pathPDF;

    public EntradaPDF(String titulo, List<String> autores, String pathPDF) {
        this.titulo = titulo;
        this.autores = autores;
        this.pathPDF = pathPDF;
    }

    public String getTitulo() { return titulo; }
    public List<String> getAutores() { return autores; }
    public String getPathPDF() { return pathPDF; }
    public void setPathPDF(String novoPath) { this.pathPDF = novoPath; }

    public abstract String getTipo();
    public abstract String toLinha(); // linha para arquivo .txt

    public static EntradaPDF fromLinha(String linha) {
        String[] partes = linha.split(";");
        String tipo = partes[0];

        switch (tipo) {
            case "LIVRO":
                return Livro.fromLinha(linha);
            case "NOTA":
                return NotaDeAula.fromLinha(linha);
            case "SLIDE":
                return Slide.fromLinha(linha);
            default:
                return null;
        }
    }
}
