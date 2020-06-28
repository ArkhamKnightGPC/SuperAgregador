package com.superagregador.models;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import java.io.UnsupportedEncodingException;
import java.lang.Integer;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class ManipuladorDeCookies {
    private void escreverCookie(Cookie cookie, HttpServletResponse response) {
        cookie.setMaxAge(999999999);
        response.addCookie(cookie);
    }

    public void salvarUId (Integer id, HttpServletResponse response) {
        Cookie uid = new Cookie("UID", id.toString());
        escreverCookie(uid, response);
    }

    public void salvarCookie(Blog blog, HttpServletResponse response) throws Exception {
        Integer blogId = blog.getID();

        Cookie cookieNome = new Cookie("nome-" + blogId.toString(), URLEncoder.encode(blog.getNome(), "UTF-8"));
        Cookie cookieUri = new Cookie("uri-" + blogId.toString(), URLEncoder.encode(blog.getUri(), "UTF-8"));
        Cookie cookieId = new Cookie("id-" + blogId.toString(), blog.getID().toString());
        escreverCookie(cookieNome, response);
        escreverCookie(cookieUri, response);
        escreverCookie(cookieId, response);
    }

    public Map <Integer, String[]> lerCookies(Cookie cookies[]) throws UnsupportedEncodingException {
        Map <Integer, String[]> mapa = new HashMap<>();
        Map <String, Integer> posicaoDaKey = new HashMap<>();
        posicaoDaKey.put("nome", 0);
        posicaoDaKey.put("uri", 1);
        posicaoDaKey.put("id",2);

        for (Cookie cookie : cookies) {
            if (!cookie.getName().equals("UID")) {
                try{
                    String[] keyDoCookie = cookie.getName().split("-");
                    if (keyDoCookie.length == 2) // Caso o usuario tenha alterado a key do Cookie, ele será ignorado
                        if (intEmString(keyDoCookie[1])){
                            Integer id = Integer.valueOf(keyDoCookie[1]);
                            if (!mapa.containsKey(id))
                                mapa.put(id, new String[3]);

                            String[] strs = mapa.get(id);
                            if (posicaoDaKey.containsKey(keyDoCookie[0]))
                                strs[posicaoDaKey.get(keyDoCookie[0])] = URLDecoder.decode(cookie.getValue(), "UTF-8");
                        }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        return mapa;
    }

    public String getUId(Cookie[] cookies) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("UID") && intEmString(cookie.getValue()))
            //Sem riscos ao converter para integer
                return cookie.getValue();
        }
        return null;
    }

    private boolean intEmString (String str) {
        int length = str.length();
        
        if (str == null || length == 0 || (str.charAt(0) == '-' && length == 1))
            return false;
        
        for (int i = 0; i < length; i++)
            if ( str.charAt(i) < '0' || str.charAt(i) > '9')
                return false;
        
        return true;
    }

    public void removerCookieDeNoticia (Integer id, HttpServletResponse response) {
        Cookie[] cookies = new Cookie[3];
        cookies[0] = new Cookie("nome-" + id , "");
        cookies[1] = new Cookie("uri-" + id, "");
        cookies[2] = new Cookie("id-" + id, Integer.toString(-1));
        
        for (int i = 0; i < 3; i++) {
            cookies[i].setMaxAge(0);
            response.addCookie(cookies[i]);
        }      
    }
}