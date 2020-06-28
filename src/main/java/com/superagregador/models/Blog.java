package com.superagregador.models;

public class Blog {
	
	private Integer ID;
    public String uri;
    public String nome;
	//private String user;
    
    public Blog(Integer ID, String uri, String nome) {
        this.ID = ID;
        this.uri = uri;
        this.nome = nome;
    }
    
	public String getUri() {
		return uri;
    }
    
    public String getNome() {
        return nome;
    }

    public Integer getID() {
        return ID;
    }
}