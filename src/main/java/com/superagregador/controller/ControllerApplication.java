package com.superagregador.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@SpringBootApplication (scanBasePackages = { "com.superagregador"})
@Controller
public class ControllerApplication {
	public static void main(String[] args) {
		SpringApplication.run(ControllerApplication.class, args);
	}

	@GetMapping("/")
	public String home(@RequestParam(value = "nome", required=false, defaultValue = "Usuário Desconhecido") String nome,
	Model model) {
		model.addAttribute("nome", nome);
		return "index";
	}

	@GetMapping("/sobre")
	public String sobre(Model model) {
		return "sobre";
	}

	@PostMapping("/AdicionarSite")
	public String adicionarSite(@RequestParam(value = "url", required = true, defaultValue = "") String URL, Model model) {
		model.addAttribute("valorURL", URL);
		return "index";
	}
}
