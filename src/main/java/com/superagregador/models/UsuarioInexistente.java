package com.superagregador.models;

public class UsuarioInexistente extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 4359780609461068067L;

    UsuarioInexistente() {
        super("Usuário não encontrado");
    }
}
