package com.superagregador.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import com.superagregador.models.Blog;
import com.superagregador.models.EditarBlog;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ControllerApplicationTests {
	private EditarBlog editaBlog;
	private static HashMap<Integer, Blog> blogs;
	
	@Test
	void contextLoads() {
	}

	@Test
	void adicionarBlogTest(){
		editaBlog = EditarBlog.inicializador();
		Blog blog = new Blog(EditarBlog.getMaxID(), "http://editoraglobo.globo.com/rss/", "Globo");
		editaBlog.adicionarBlog(blog);
		blogs = editaBlog.getMap();
		for(Blog blog2: blogs.values()){
			assertEquals("http://editoraglobo.globo.com/rss/", blog2.getUri());
			assertEquals("Globo", blog2.getNome());
		}
	}

	@Test
	void removerBlogTest(){
		editaBlog = EditarBlog.inicializador();
		Blog blog = new Blog(EditarBlog.getMaxID(), "http://editoraglobo.globo.com/rss/", "Globo");
		blogs = editaBlog.getMap();
		editaBlog.adicionarBlog(blog);
		editaBlog.removerBlog(EditarBlog.getMaxID()-1);
		for(Integer integer: blogs.keySet()){
			assertEquals(null, integer);
		}
	}
}
