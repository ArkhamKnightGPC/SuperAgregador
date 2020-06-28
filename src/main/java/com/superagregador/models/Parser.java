package com.superagregador.models;

import java.util.List;

public interface Parser {
    public void lerArquivo (String arquivo) throws Exception;
    public List<String[]> retornarResultados();
}