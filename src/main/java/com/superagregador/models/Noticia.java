package com.superagregador.models;

public class Noticia {
    private String manchete, subtitulo, data, link, imagem;

    Noticia (String manchete, String subtitulo, String data, String link, String imagem) {
        this.manchete = manchete;
        this.subtitulo = subtitulo;
        this.data = data;
        this.link = link;
        this.imagem = imagem;
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