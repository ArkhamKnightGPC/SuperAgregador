package com.superagregador.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@SpringBootApplication
@Controller
public class ControllerApplication {
	public static void main(String[] args) {
		SpringApplication.run(ControllerApplication.class, args);
	}

	@GetMapping("/")
	public String ola(@RequestParam(value = "nome", required=false, defaultValue = "Usuário Desconhecido") String nome,
	Model model) {
		model.addAttribute("nome", nome);
		return "index";
	}
}
