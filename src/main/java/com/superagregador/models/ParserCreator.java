package com.superagregador.models;

import java.util.Map;
import java.util.HashMap;

public abstract class ParserCreator implements Parser{
    private static Map <String, Parser> mapa;
    
    public static Parser criarParser(String tipo) {
        if (mapa == null) {
            mapa = new HashMap<>();
            mapa.put("txt", new TxtParser());
            mapa.put("xml", new XmlParser());
            mapa.put("json", new JsonParser());
        }
        return mapa.get(tipo.toLowerCase());    
    }
}