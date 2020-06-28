package com.superagregador.models;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Component
public class XmlParser extends ParserCreator {
    static final String TITLE = "title";
    static final String DESCRIPTION = "description";
    static final String LINK = "link";
    static final String ITEM = "item";
    static final String IMAGEM = "urlImage";
    static final String PUB_DATE = "pubDate";

    private XMLInputFactory inputFactory;
    private XMLEventReader eventReader;
    private InputStream in;
    private List<String[]> noticias;

    @Override
    public void lerArquivo(String arquivo) throws Exception {
        inputFactory = XMLInputFactory.newInstance();
        URL link = new URL(arquivo);
        in = link.openStream();
        eventReader = inputFactory.createXMLEventReader(in);
        noticias = new ArrayList<>();
        parseSite(); 
    }

    @Override
    public List<String[]> retornarResultados() {
        return noticias;
    }

    private String getDadosDoEvento(XMLEvent event, XMLEventReader eventReader) throws XMLStreamException {
        String retorno = "";
        event = eventReader.nextEvent();
        if (event instanceof Characters) {
            retorno = event.asCharacters().getData();
        }
        return retorno;
    }

    private void limparMapa(Map<String, String> mapa){
        mapa.put(TITLE, null);
        mapa.put(DESCRIPTION, null);
        mapa.put(PUB_DATE, null);
        mapa.put(LINK, null);
        mapa.put(IMAGEM, null);

    }

    private void parseSite() throws XMLStreamException {

        Map <String, String> prototipoItemConteudo = new HashMap<>(); //A ideia é um código mais limpo
        //Sei que não é o padrão prototype, mas acredito que a ideia foi bem inspirada nele e graças a isso
        //substitui um monte de if e else 
        limparMapa(prototipoItemConteudo);
        while (eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();
            if (event.isStartElement()) {
                String elemento = event.asStartElement().getName().getLocalPart();

                if (elemento.equals(ITEM))
                    event = eventReader.nextEvent();
                else
                    prototipoItemConteudo.put(elemento, getDadosDoEvento(event, eventReader));    
            }
            else if (event.isEndElement() && event.asEndElement().getName().getLocalPart() == (ITEM)) {
                
                String[] noticia = {
                    prototipoItemConteudo.get(TITLE), 
                    prototipoItemConteudo.get(DESCRIPTION),
                    prototipoItemConteudo.get(PUB_DATE), 
                    prototipoItemConteudo.get(LINK), 
                    prototipoItemConteudo.get(IMAGEM)
                };
                noticias.add(noticia);
                limparMapa(prototipoItemConteudo);
            }
        }
    }
}