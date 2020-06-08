package com.superagregador.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.superagregador.models.Blog;
import com.superagregador.models.EditarBlog;

@SpringBootApplication (scanBasePackages = { "com.superagregador"})
@Controller
public class ControllerApplication {
	
	private static EditarBlog blogs;
	private static String nomePadrao = "";
	private static String uriPadrao = "";
	private static boolean existemBlogs;
	
	public static void main(String[] args) {
		SpringApplication.run(ControllerApplication.class, args);
		blogs = EditarBlog.inicializador();
	}

	@GetMapping("/")
	public String home(Model model, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (EditarBlog.getMap().isEmpty()) {
			existemBlogs = false;
			if(cookies != null){
				for (int i =0; i < cookies.length/2; i++) {
					existemBlogs = true;
					Blog blog = new Blog (EditarBlog.getMaxID(), uriPadrao, nomePadrao);
					if (cookies[i].getName().equals( "nome" )){
						model.addAttribute("valorNome", cookies[i].getValue());
						blog.setNome(cookies[i].getValue());
					}
					if(i < cookies.length-1){
						if(cookies[i+1].getName().equals( "uri")){
							model.addAttribute("valorURI", cookies[i+1].getValue());
							blog.setUri(cookies[i+1].getValue());
						}
					}
					model.addAttribute("blogs", EditarBlog.getMap());
					model.addAttribute("existemBlogs", existemBlogs);
					blogs.adicionarBlog(blog);
				}
			}
		} else{
			existemBlogs = true;
		}

		model.addAttribute("blogs", EditarBlog.getMap());
		model.addAttribute("existemBlogs", existemBlogs);
		model.addAttribute("valorNome", nomePadrao);
		model.addAttribute("valorURI", uriPadrao);
		return "index";
	}

	@GetMapping("/sobre")
	public String sobre(Model model) {
		return "sobre";
	}

	@PostMapping("/AdicionarSite")
	public String adicionarSite(@RequestParam(value = "nome", required = true, defaultValue = "") String nome,
								@RequestParam(value = "uri", required = true, defaultValue = "") String uri,
								Model model, HttpServletRequest request, HttpServletResponse response) {
							
		if (!nome.equals("") && !uri.equals("")) {
			blogs.adicionarBlog(new Blog (EditarBlog.getMaxID(), uri, nome));
			Cookie nomeCookie = new Cookie("nome", nome);
			Cookie uriCookie = new Cookie("uri", uri);
			uriCookie.setMaxAge(60*60*24);
			nomeCookie.setMaxAge(60*60*24);
			response.addCookie(nomeCookie);
			response.addCookie(uriCookie);
			nomePadrao = "";
			uriPadrao = "";
		} else {
			nomePadrao = nome;
			uriPadrao = uri;
		}

		return "redirect:/";
	}

	@PostMapping("/RemoverSite")
	public String removerSite(@RequestParam(value= "id", required = true) int id, HttpServletResponse response) {
		Cookie nomeCookie = new Cookie("nome", EditarBlog.getMap().get(id).getNome());
		nomeCookie.setMaxAge(0);

		Cookie uriCookie = new Cookie("uri", EditarBlog.getMap().get(id).getUri());
		uriCookie.setMaxAge(0);
		response.addCookie(nomeCookie);
		response.addCookie(uriCookie);
		blogs.removerBlog((Integer) id);
		return "redirect:/";
	}

}
