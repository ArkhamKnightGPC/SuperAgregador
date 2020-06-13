package com.superagregador.models;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

import com.rometools.rome.feed.rss.Description;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;

public class XmlParser {
    static final String TITLE = "title";
    static final String DESCRIPTION = "description";
    static final String LINK = "link";
    static final String ITEM = "item";
    static final String IMAGEM = "urlImage";
    static final String PUB_DATE = "pubDate";

    private XMLInputFactory inputFactory;
    private XMLEventReader eventReader;
    private InputStream in;
    private static ArrayList<Noticia> noticias = new ArrayList<>();
    
    public XmlParser (URI link) throws Exception {
        inputFactory = XMLInputFactory.newInstance();
        in = link.toURL().openStream();
        eventReader = inputFactory.createXMLEventReader(in);
        parseSite();
    }

    public ArrayList<Noticia> getNoticias() {
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

    private void parseSite() throws XMLStreamException {
        Noticia noticia = null;
        
        String manchete ="", subtitulo="", data="", link = null, imagem = null;
        
        while (eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();
            if (event.isStartElement()) {
                
                String elemento = event.asStartElement().getName().getLocalPart();
                
                switch(elemento) {
                    case ITEM:
                        event = eventReader.nextEvent();
                        break;
                    
                    case TITLE:
                        manchete = getDadosDoEvento(event, eventReader);
                        break;
                    
                    case DESCRIPTION:
                        subtitulo = getDadosDoEvento(event, eventReader);
                        break;

                    case LINK:
                        link = getDadosDoEvento(event, eventReader);
                        break;
                    
                    case IMAGEM:
                        imagem = getDadosDoEvento(event, eventReader);
                        break;
                    
                    case PUB_DATE:
                        data = getDadosDoEvento(event, eventReader);
                        break;
                    
                    default:
                        break;
                        
                }
            } else if (event.isEndElement() && event.asEndElement().getName().getLocalPart() == (ITEM)) {
                noticia = new Noticia(manchete, subtitulo, data, link, imagem);
                noticias.add(noticia);

                manchete = "";
                subtitulo = "";
                data = "";
                link = null;
                imagem = null;

            }
        }
    }

}