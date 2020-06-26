package com.superagregador.models;

import java.util.ArrayList;

public class AhoCorasick {
	/* Vamos implementar o automato de Aho-Corasick para buscar em tempo linear os padroes no texto
	 * implementamos uma trie (arvore de prefixos) para armazenas os padroes e calculamos as transicoes e suffixLinks
	 * de maneira online conforme for necessario 
	 */
	private String texto;
	private ArrayList<String> padroes = new ArrayList<>();
	private int tamanhoTrie = 0;//numero de estados ja criados no automato
	private ArrayList<EstadoAhoCorasick> trie = new ArrayList<>();//lista de estados adicionados na trie
	
	public AhoCorasick(String texto, ArrayList<String> padroes){
		this.texto = texto;
		this.padroes = padroes;
		trie.add(new EstadoAhoCorasick(tamanhoTrie, -1, '$'));//adicionar raiz da trie como vertice inicial
		tamanhoTrie++;
		for(int i=0; i<padroes.size(); i++) {
			adicionarString(i, padroes.get(i));
		}
	}
	
	private void adicionarString(int idx, String padrao) {//adiciona padrao na trie
		int estadoAtual = 0;//comecamos na raiz e vamos descendo adicionando caractere por caractere
		for(int i=0; i<padrao.length(); i++) {
			char atual = padrao.charAt(i);
			if(trie.get(estadoAtual).filhos.containsKey(atual) == false) {//esse prefixo ainda nao existe na trie, preciso criar vertice novo
				trie.add(new EstadoAhoCorasick(tamanhoTrie, estadoAtual, atual));
				trie.get(estadoAtual).filhos.put(atual, tamanhoTrie);//crio aresta do pai para o filho
				tamanhoTrie++;
			}
			estadoAtual = trie.get(estadoAtual).filhos.get(atual);
		}
		trie.get(estadoAtual).fim = true;//marcamos vertice final
		trie.get(estadoAtual).idPadrao = idx;
	}
	
	private int getSuffixLink(int idVertice) {
		if(trie.get(idVertice).suffixLink != -1)return trie.get(idVertice).suffixLink;//ja calculei, so preciso retornar
		if(idVertice==0 || trie.get(idVertice).pai==0)
			trie.get(idVertice).suffixLink = 0;//caso base: se vertice eh raiz ou filho da raiz
		else
			trie.get(idVertice).suffixLink = getTransicao(getSuffixLink(trie.get(idVertice).pai), trie.get(idVertice).pch);
		return trie.get(idVertice).suffixLink;
	}
	
	private int getTransicao(int idVertice, char ch) {
		if(trie.get(idVertice).transicao.containsKey(ch))return trie.get(idVertice).transicao.get(ch);//ja calculei, so preciso retornar
		if(trie.get(idVertice).filhos.containsKey(ch))
			trie.get(idVertice).transicao.put(ch, trie.get(idVertice).filhos.get(ch));//caso base: encontrei a transicao desejada
		else
			trie.get(idVertice).transicao.put(ch, idVertice==0 ? 0 : getTransicao(getSuffixLink(idVertice), ch));//sigo suffixLink procurando a transicao
		return trie.get(idVertice).transicao.get(ch);
	}
	
	public ArrayList<String> padroesMaisFrequentes() {//retorna se algum padrao foi encontrado no texto ou nao
		ArrayList<String> retorno = new ArrayList<>();
		int estadoAtual = 0;
		for(int i=0; i<texto.length(); i++) {
			char ch = texto.charAt(i);
			estadoAtual = getTransicao(estadoAtual, ch);
			trie.get(estadoAtual).freq++;
		}
		for(int i=trie.size()-1; i>=0; i--) {
			int sufProp = getSuffixLink(i);
			trie.get(sufProp).freq += trie.get(i).freq;
		}
		for(int i=0; i<trie.size(); i++) {
			if(trie.get(i).fim == false)continue;
			String padrao = padroes.get(trie.get(i).idPadrao);
			if(trie.get(i).freq > 3 && padrao.length()>4)retorno.add(padrao);//se apareceu pelo menos 4 vezes tah valendo
		}
		return retorno;
	}
	public boolean procurarNoTexto() {
		boolean achei = false;
		int estadoAtual = 0;
		for(int i=0; i<texto.length(); i++) {
			char ch = texto.charAt(i);
			estadoAtual = getTransicao(estadoAtual, ch);
			if(trie.get(estadoAtual).fim)achei = true;
		}
		return achei;
	}
}

