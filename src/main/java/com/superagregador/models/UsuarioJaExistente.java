package com.superagregador.models;

public class UsuarioJaExistente extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 7114224083796220141L;

    UsuarioJaExistente () {
        super("O Usuário já foi cadastrado");
    }
}
