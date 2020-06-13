package com.superagregador.models;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.lang.Integer;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class ManipuladorDeCookies {
    private static EditarBlog blogs = EditarBlog.inicializador();

    public static void salvarCookies(String nome, String uri, Integer id, HttpServletResponse response) throws Exception {
        
        Cookie nomeCookie = new Cookie("nome", URLEncoder.encode(nome, "UTF-8"));
        Cookie uriCookie = new Cookie( "uri", URLEncoder.encode(uri, "UTF-8"));
        Cookie idCookie = new Cookie ( "id", id.toString());

        uriCookie.setMaxAge(999999999);
        nomeCookie.setMaxAge(999999999);
        idCookie.setMaxAge(999999999);
            
        response.addCookie(nomeCookie);
        response.addCookie(uriCookie);
        response.addCookie(idCookie);
        
    }

    public static void lerCookies(Cookie[] cookies) {
        for (int i = 0; i < cookies.length; i+= 3) {
            Blog blog = new Blog(-1, "", "");
            try {
                blog.setNome(URLDecoder.decode(cookies[i].getValue(),"UTF-8"));
                blog.setUri(URLDecoder.decode(cookies[i+1].getValue(),"UTF-8"));
                blog.setID(Integer.parseInt(cookies[i+2].getValue().toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            blogs.adicionarBlog(blog);
        }
    }
}