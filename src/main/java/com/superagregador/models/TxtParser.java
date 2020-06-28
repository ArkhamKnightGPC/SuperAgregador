package com.superagregador.models;

import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

public class TxtParser extends ParserCreator {

    List<String[]> resultado;

    @Override
    public void lerArquivo(String arquivo) throws Exception {
        String file = new File("src/main/resources").getAbsolutePath() + "/" + arquivo;
        BufferedReader reader = new BufferedReader(new FileReader(file));
        resultado = new ArrayList<>();
        String linha = reader.readLine();
        while (linha != null) {
            String[] resultadoEmArray = new String[1];
            resultadoEmArray[0] = linha;
            resultado.add(resultadoEmArray);
            linha = reader.readLine();
        }
        reader.close();
    }

    @Override
    public List<String[]> retornarResultados() {
        return resultado;
    }

}
