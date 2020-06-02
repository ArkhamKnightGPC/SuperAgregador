package com.superagregador.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

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
	public String home(Model model) {
		if (EditarBlog.getMap().isEmpty()) {
			existemBlogs = false;
		} else existemBlogs = true;

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
								Model model) {
							
		if (!nome.equals("") && !uri.equals("")) {
			blogs.adicionarBlog(new Blog (EditarBlog.getMaxID(), uri, nome));
			nomePadrao = "";
			uriPadrao = "";
		} else {
			nomePadrao = nome;
			uriPadrao = uri;
		}

		return "redirect:/";
	}

	@PostMapping("/RemoverSite")
	public String removerSite(@RequestParam(value= "id", required = true) int id) {
		blogs.removerBlog((Integer) id);
		return "redirect:/";
	}

}
