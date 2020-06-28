package com.superagregador.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.superagregador.models.Blog;
import com.superagregador.models.GerenciadorDeUsuario;
import com.superagregador.models.Usuario;
import com.superagregador.models.UsuarioInexistente;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ControllerApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void adicionarBlogTest() {
		GerenciadorDeUsuario gu = GerenciadorDeUsuario.getInstance(0);
		Integer usuarioId = gu.adicionarNovoUsuario();
		Usuario usuario;
		try {
			usuario = gu.getUsuario(usuarioId);
			Integer blogId = usuario.getMaxBlogId();
			Blog blog = new Blog(blogId, "http://editoraglobo.globo.com/rss/", "Globo");
			usuario.adicionarBlog(blog, blog.getID());
			
			assertEquals("http://editoraglobo.globo.com/rss/", usuario.getBlog(blogId).getUri());
			assertEquals("Globo", usuario.getBlog(blogId).getNome());
		} catch (UsuarioInexistente e) {
			e.printStackTrace();
		}
		
	}

	@Test
	void removerBlogTest(){
		GerenciadorDeUsuario gu = GerenciadorDeUsuario.getInstance(0);
		Integer usuarioId = gu.adicionarNovoUsuario();
		Usuario usuario;
		try {
			usuario = gu.getUsuario(usuarioId);
			Integer blogId = usuario.getMaxBlogId();
			Blog blog = new Blog(blogId, "http://editoraglobo.globo.com/rss/", "Globo");
			usuario.adicionarBlog(blog, blog.getID());
			usuario.removerBlog(blogId);
			
			assertEquals(null, usuario.getBlog(blogId));
			assertEquals(null, usuario.getBlog(blogId));
		} catch (UsuarioInexistente e) {
			e.printStackTrace();
		}
	}
}
