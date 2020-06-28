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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.superagregador.models.AhoCorasick;
import com.superagregador.models.Blog;
import com.superagregador.models.Usuario;
import com.superagregador.models.UsuarioInexistente;
import com.superagregador.models.GeradorWordCloud;
import com.superagregador.models.GerenciadorDeUsuario;
import com.superagregador.models.ManipuladorDeCookies;
import com.superagregador.models.Noticia;
import com.superagregador.models.Parser;
import com.superagregador.models.ParserCreator;

@SpringBootApplication(scanBasePackages = { "com.superagregador.*" })
@Controller
public class ControllerApplication {

	private static GerenciadorDeUsuario gerenciadorDeUsuarios;
	private static String nomePadrao = "";
	private static String uriPadrao = "";
	private static Parser leitor;
	

	public static void main(String[] args) {
		SpringApplication.run(ControllerApplication.class, args);
		Integer maxUId = getMaxUId();
		gerenciadorDeUsuarios = GerenciadorDeUsuario.getInstance(maxUId);
	}

	private static Integer getMaxUId () {
		leitor = ParserCreator.criarParser("txt");
		try { 
			leitor.lerArquivo("maxUId.txt");
			return Integer.valueOf(leitor.retornarResultados().get(0)[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	private Usuario getUsuarioDaSessao(Cookie[] cookies, HttpServletResponse response) throws UsuarioInexistente {
		Integer id;
		ManipuladorDeCookies mc = new ManipuladorDeCookies();
		if (cookies == null || mc.getUId(cookies) == null) {
			id = gerenciadorDeUsuarios.adicionarNovoUsuario();
		} else {
			id = Integer.valueOf(mc.getUId(cookies));
			if (gerenciadorDeUsuarios.existeUsuario(id))
				return gerenciadorDeUsuarios.getUsuario(id);
			id = gerenciadorDeUsuarios.adicionarUsuario(id);
		}	
		mc.salvarUId(id, response);
		return gerenciadorDeUsuarios.getUsuario(id);
		
	}

	private List<Noticia> gerarNoticias(Usuario usuario) throws Exception {
		leitor = ParserCreator.criarParser("xml");
		Iterator<Integer> iterator = usuario.getKeySet().iterator();
		Blog blog;
		List<Noticia> noticias = new ArrayList<>();
		while (iterator.hasNext()) {
			blog = usuario.getBlog(iterator.next());
			leitor.lerArquivo(blog.getUri());			
			for (String[] resultado : leitor.retornarResultados()) {
				noticias.add(new Noticia(resultado));
			}
		}
		return noticias;
	}

	@GetMapping("/")
	public String home(Model model, HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		ManipuladorDeCookies mc = new ManipuladorDeCookies();
		Map <Integer, String[]> conteudoDosCookies; 
		boolean existemBlogs = false;
		Usuario usuario;

		try {
			usuario = getUsuarioDaSessao(cookies, response);
			
			if(cookies != null){
				conteudoDosCookies = mc.lerCookies(cookies);
				for (Integer i : conteudoDosCookies.keySet()) {
					String[] blog = conteudoDosCookies.get(i);
					if (blog[2] != null && blog[1] != null && blog[0] != null) //Verifica se é válido
						usuario.adicionarBlog(new Blog(Integer.valueOf(blog[2]), blog[1], blog[0]), i);
				}
			}

			existemBlogs = (usuario.getListaDeBlogs().isEmpty()) ? false : true;
			model.addAttribute("noticias", gerarNoticias(usuario));
			model.addAttribute("blogs", usuario.getListaDeBlogs());
			
			//--------------vamos pegar o necessario para exibir a word cloud tambem
			String textoWordCloud = "";
			List<String> padroesWordCloud = new ArrayList<>();
			List<Noticia> noticias = gerarNoticias(usuario);
			for(int i=0; i<noticias.size(); i++) {
				textoWordCloud = textoWordCloud + noticias.get(i).getTitulo();
				textoWordCloud = textoWordCloud + noticias.get(i).getSubtitulo();
			}
			
			String[] palavrasDoTexto = textoWordCloud.split(" ");
			for(int i=0; i<palavrasDoTexto.length; i++)
				padroesWordCloud.add(palavrasDoTexto[i]);
			AhoCorasick ahoCorasick = new AhoCorasick(textoWordCloud, padroesWordCloud);
			List<String> palavrasMaisFrequentes = ahoCorasick.padroesMaisFrequentes();
			GeradorWordCloud geradorWordCloud = new GeradorWordCloud(palavrasMaisFrequentes, usuario.getUid());
			geradorWordCloud.gerarWordCloud();
			model.addAttribute("localWordCloud", geradorWordCloud.linkImagem);
			//----------------------------------------
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
		Usuario usuario;
		Cookie[] cookies = request.getCookies();
		ManipuladorDeCookies mc = new ManipuladorDeCookies();
		try {
			usuario = getUsuarioDaSessao(cookies, response);
			
			if (!nome.equals("") && !uri.equals("")) {
				
				Integer id = usuario.getMaxBlogId();
				usuario.adicionarBlog(new Blog (id, uri, nome), id);
				nomePadrao = "";
				uriPadrao = "";

				mc.salvarCookie(usuario.getBlog(id),response);
			} else {
				nomePadrao = nome;
				uriPadrao = uri;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/";
	}

	@PostMapping("/RemoverFeed")
	public String removerSite(@RequestParam(value= "id", required = true) int id,
							HttpServletRequest request,
							HttpServletResponse response) {
		Usuario usuario;
		Cookie[] cookies = request.getCookies();
		ManipuladorDeCookies mc = new ManipuladorDeCookies();
		try {
			usuario = getUsuarioDaSessao(cookies, response);
			mc.removerCookieDeNoticia(id, response);
			usuario.removerBlog(id);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}
	
}
