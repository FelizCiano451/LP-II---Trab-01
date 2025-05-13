package model;

import java.util.List;
import java.util.Arrays;

public class Livro extends EntradaPDF {
    private String subtitulo;
    private String areaConhecimento;
    private int anoPublicacao;
    private String editora;
    private int numPaginas;

    public Livro(String titulo, List<String> autores, String pathPDF,
                 String subtitulo, String areaConhecimento, int anoPublicacao,
                 String editora, int numPaginas) {
        super(titulo, autores, pathPDF);
        this.subtitulo = subtitulo;
        this.areaConhecimento = areaConhecimento;
        this.anoPublicacao = anoPublicacao;
        this.editora = editora;
        this.numPaginas = numPaginas;
    }

    public static Livro fromLinha(String linha) {
        String[] partes = linha.split(";");
        String titulo = partes[1];
        List<String> autores = Arrays.asList(partes[2].split(","));
        String pathPDF = partes[3];
        String subtitulo = partes[4];
        String area = partes[5];
        int ano = Integer.parseInt(partes[6]);
        String editora = partes[7];
        int paginas = Integer.parseInt(partes[8]);
        return new Livro(titulo, autores, pathPDF, subtitulo, area, ano, editora, paginas);
    }

    @Override
    public String toLinha() {
        return String.join(";",
            "LIVRO",
            titulo,
            String.join(",", autores),
            pathPDF,
            subtitulo,
            areaConhecimento,
            String.valueOf(anoPublicacao),
            editora != null ? editora : "",
            String.valueOf(numPaginas)
        );
    }

    @Override
    public String getTipo() {
        return "Livro";
    }
}

    @Override
    public void salvarNaBiblioteca(String pathBiblioteca) {
        System.out.println("Salvando livro em " + pathBiblioteca);
    }
}
