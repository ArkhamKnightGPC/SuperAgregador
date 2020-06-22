package com.superagregador.models;

import java.util.HashMap;

public class EstadoAhoCorasick {
	public int id;//identificador do vertice na trie
	public int pai;//vertice pai na trie
	public char pch;//rotulo na aresta que vem do pai
	public int suffixLink;//suffixLink aponta para sufixo proprio maximal presente na trie
	public HashMap<Character, Integer> filhos = new HashMap<>();//lista de arestas para vertices filhos
	public HashMap<Character, Integer> transicao = new HashMap<>();//transicoes do automato
	public boolean fim;//indica se estado eh final de algum padrao
	
	EstadoAhoCorasick(int id, int pai, char pch){
		this.id = id;
		this.pai = pai;
		this.pch = pch;
		suffixLink = -1;//indica que suffixLink desse estado nao foi calculado ainda
		fim = false;
	}
}

