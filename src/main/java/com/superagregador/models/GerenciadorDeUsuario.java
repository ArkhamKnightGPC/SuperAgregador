package com.superagregador.models;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class GerenciadorDeUsuario {
    private static GerenciadorDeUsuario instancia;
    private static Map<Integer, Usuario> mapaDeUsuarios;
    private static Integer maxUId;

    private GerenciadorDeUsuario(int maxUId) {
        mapaDeUsuarios = new HashMap<>();
        GerenciadorDeUsuario.maxUId = maxUId;
    }

    public static GerenciadorDeUsuario getInstance(int maxUId) {
        if (instancia == null)
            instancia = new GerenciadorDeUsuario(maxUId);
        return instancia;
    }

    public Integer adicionarUsuario(Integer UId) {
        if (!mapaDeUsuarios.containsKey(UId))
            mapaDeUsuarios.put(UId, new Usuario(UId));
        else return adicionarNovoUsuario();
        return UId;
    }

    public Integer adicionarNovoUsuario (){
        mapaDeUsuarios.put(maxUId, new Usuario(maxUId));
        ++maxUId;
        System.out.println("teste");
        updateMaxUId();
        return maxUId - 1;
    }

    public Usuario getUsuario (int UId) throws UsuarioInexistente {
        if (mapaDeUsuarios.containsKey(UId))
            return mapaDeUsuarios.get(UId);
        else throw new UsuarioInexistente();
    }

    public boolean existeUsuario (int UId) {
        return mapaDeUsuarios.containsKey(UId);
    }

    private void updateMaxUId () {
        try {
            String file = new File("src/main/resources").getAbsolutePath() + "/" + "maxUId.txt";
            FileWriter fw = new FileWriter(file);
            fw.write(maxUId.toString());
            fw.close();    
        } catch (Exception e) {
            e.addSuppressed(e);
        }
    }

}