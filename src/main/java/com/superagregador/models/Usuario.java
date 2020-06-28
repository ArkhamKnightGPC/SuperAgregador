package com.superagregador.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Usuario {
    private Map<Integer, Blog> listaDeBlogs;
    private Integer maxId;
    Integer UId;

    Usuario (Integer UId)  {
        listaDeBlogs = new HashMap<>();
        maxId = 0;
        this.UId = UId;
    }

    public Map<Integer, Blog> getListaDeBlogs() {
        return listaDeBlogs;
    }

    public Set<Integer> getKeySet() {
        return listaDeBlogs.keySet();
    }

    public Blog getBlog(Integer id) {
        return listaDeBlogs.get(id);
    }

    public void adicionarBlog (Blog blog, Integer id) {
        listaDeBlogs.put(id, blog);
        if (maxId <= id)
            maxId = id +1;
    }

    public void removerBlog (Integer id) {
        listaDeBlogs.remove(id);
    }

    public Integer getUid () {
        return UId;
    }

    public Integer getMaxBlogId() {
        return maxId;
    }
}