package com.superagregador.models;

import java.util.HashMap;

public class EditarBlog {
    private static HashMap<Integer, Blog> blogs;
    private static Integer id; 
    private static EditarBlog editarBlog;

    private EditarBlog () {
        
        blogs = new HashMap<>();
        id = 0;

    }

    public static EditarBlog inicializador() {
        if (editarBlog == null)
            editarBlog = new EditarBlog();
        return editarBlog;
    }

    public static Integer getMaxID() {
        return id;
    }

    public static HashMap<Integer, Blog> getMap() {
        return blogs;
    }

    public void adicionarBlog (Blog blog) {
        blogs.put(id, blog);
        ++id;
    }

    public void adicionarBlog (Blog blog, Integer id) {
        blogs.put(id, blog);
    }

    public void removerBlog (Integer blogID) {
        blogs.remove(blogID);
    } 
}