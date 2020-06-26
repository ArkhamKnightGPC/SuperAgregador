package com.superagregador.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import com.superagregador.models.AhoCorasick;
import com.superagregador.models.Blog;
import com.superagregador.models.EditarBlog;
import com.superagregador.models.GeradorWordCloud;
import com.superagregador.models.ManipuladorDeCookies;
import com.superagregador.models.XmlParser;
import com.superagregador.models.Noticia;

@SpringBootApplication(scanBasePackages = { "com.superagregador" })
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

	private ArrayList<Noticia> gerarNoticias() throws Exception {
		XmlParser xml = new XmlParser(); //Assim evita que após exclusão noticias permaneçam
		for (Integer id : EditarBlog.getMap().keySet()) {
			xml = new XmlParser(new URI(EditarBlog.getMap().get(id).getUri()));
		}
		return xml.getNoticias();
	}

	@GetMapping("/")
	public String home(Model model, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (EditarBlog.getMap().isEmpty()) {
			existemBlogs = false;
			if(cookies != null){
				existemBlogs = true;
				ManipuladorDeCookies.lerCookies(cookies);
			}
		} else{
			existemBlogs = true;
		}
		try {
			model.addAttribute("noticias", gerarNoticias());
		} catch (Exception e) {
			e.printStackTrace();
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
								Model model, HttpServletResponse response) {
							
		if (!nome.equals("") && !uri.equals("")) {
			Integer id = EditarBlog.getMaxID();
			blogs.adicionarBlog(new Blog (id, uri, nome));
			nomePadrao = "";
			uriPadrao = "";
			try {
				ManipuladorDeCookies.salvarCookies(nome, uri, id,response);
			} catch (Exception e){
				e.printStackTrace();
			}
		} else {
			nomePadrao = nome;
			uriPadrao = uri;
		}

		return "redirect:/";
	}

	@PostMapping("/RemoverFeed")
	public String removerSite(@RequestParam(value= "id", required = true) int id, HttpServletResponse response) {
		Cookie nomeCookie = new Cookie("nome", "");
		nomeCookie.setMaxAge(0);

		Cookie uriCookie = new Cookie("uri", "");
		uriCookie.setMaxAge(0);
		
		Cookie idCookie = new Cookie("id", Integer.toString(-1));
		idCookie.setMaxAge(0);

		response.addCookie(nomeCookie);
		response.addCookie(uriCookie);
		response.addCookie(idCookie);

		blogs.removerBlog(Integer.valueOf(id));
		return "redirect:/";
	}
	
	@RequestMapping("/expressoes")
	public String expressoes(Model model) throws Exception{
		//preciso pegar textos das noticias para pesquisar as expressoes
		String texto = "";
		ArrayList<String> padroes = new ArrayList<>();
		
		for (Integer id : EditarBlog.getMap().keySet()) {
			XmlParser xml = new XmlParser(new URI(EditarBlog.getMap().get(id).getUri()));
			ArrayList<Noticia> noticias = xml.getNoticias();
			for(int i=0; i<noticias.size(); i++) {
				texto = texto + noticias.get(i).getTitulo();
				texto = texto + noticias.get(i).getSubtitulo();
			}
			String[] palavrasDoTexto = texto.split(" ");
			for(int i=0; i<palavrasDoTexto.length; i++)
				padroes.add(palavrasDoTexto[i]);
		}
		AhoCorasick ahoCorasick = new AhoCorasick(texto, padroes);
		ArrayList<String> palavrasMaisFrequentes = ahoCorasick.padroesMaisFrequentes();
		
		//vamos usar essas palavras para gerar word cloud
		GeradorWordCloud geradorWordCloud = new GeradorWordCloud(palavrasMaisFrequentes);
		geradorWordCloud.gerarWordCloud();
		
		return "expressoes";
	}
}
