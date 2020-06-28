package com.superagregador.models;


public class Noticia {
    private String manchete = null, subtitulo = null, data = null;
    private String link = null, imagem = null;

    public Noticia (String manchete, String subtitulo, String data, String link, String imagem) {
        this.manchete = manchete;
        this.subtitulo = subtitulo;
        this.data = data;
        this.link = link;
        this.imagem = imagem;
    }

    public Noticia (String[] conteudo) {
        if (conteudo.length >= 5) {
            manchete = conteudo[0];
            subtitulo = conteudo[1];
            data = conteudo[2];
            link = conteudo[3];
            imagem = conteudo[4];
        }
    }

    public String getTitulo() {
        return manchete;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public String getData() {
        return data;
    }

    public String getLink() {
        return link;
    }

    public String getImagem() {
        return imagem;
    }

}