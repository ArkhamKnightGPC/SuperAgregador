package com.superagregador.models;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AhoCorasickTest {

	String texto;
	ArrayList<String> padroes = new ArrayList<>();
	
	@BeforeEach
	void beforeEach() {
		padroes.clear();
	}
	
	@Test
	void test1() {
		texto = "O filme novo do Batman é bom demais, rapaz!";
		padroes.add("bom");
		AhoCorasick ahoCorasick = new AhoCorasick(texto, padroes);
		assertEquals(ahoCorasick.procurarNoTexto(), true);
	}
	
	@Test
	void test2(){
		texto = "Batman Vs Superman é muito melhor do que dizem por ai.";
		padroes.add("inutil");
		padroes.add("lho");
		AhoCorasick ahoCorasick = new AhoCorasick(texto, padroes);
		assertEquals(ahoCorasick.procurarNoTexto(), true);
	}
	
	@Test
	void test3() {
		texto = "Aqui não vai achar nada não.";
		padroes.add("Batman");
		padroes.add("Robin");
		padroes.add("Superman");
		AhoCorasick ahoCorasick = new AhoCorasick(texto, padroes);
		assertEquals(ahoCorasick.procurarNoTexto(), false);
	}
	
	@Test
	void test4() {
		texto = "Vamos ver se o bicho tá funcionando mesmo :) né rapaz.";
		padroes.add(":) né ");
		AhoCorasick ahoCorasick = new AhoCorasick(texto, padroes);
		assertEquals(ahoCorasick.procurarNoTexto(), true);
	}
}

