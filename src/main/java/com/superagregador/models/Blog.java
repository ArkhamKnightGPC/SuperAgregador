package com.superagregador.models;

public class Blog {
	
	private int ID;
    public String uri;
    public String nome;
	//private String user;
    
    public Blog(int ID, String uri, String nome) {
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

    public int getID() {
        return ID;
    }

	public void setUri(String uri) {
		this.uri = uri;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    


}