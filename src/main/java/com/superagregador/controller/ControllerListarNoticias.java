package com.superagregador.controller;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.superagregador.models.Blog;
import com.superagregador.models.EditarBlog;

@Controller
public class ControllerListarNoticias{
		
		@GetMapping("/lista")
		public String listarNoticias(Model model) throws Exception{
			
			HashMap<Integer, Blog> listaDeBlogs = EditarBlog.getMap();
			List<String> linksDeBlogs = new ArrayList<String>();//eh aqui que vamos colocar os links
			
			for(Map.Entry<Integer, Blog> entry : listaDeBlogs.entrySet()) {//vamos iterar por todos os blogs adicionados para pegar os links dos feeds
				Blog blog = entry.getValue();
				String url = blog.uri;
				XmlReader reader = new XmlReader(new URL(url));
				SyndFeed feed = new SyndFeedInput().build(reader);
				for (SyndEntry syndEntry : feed.getEntries()) {//vamos ver o conteudo do feed fornecido
					String linkDeBlog = syndEntry.getLink();
					linksDeBlogs.add(linkDeBlog);
				}
				model.addAttribute("linksDeBlogs", linksDeBlogs);//agora podemos manipular esses links com o thymeleaf
			}
			return "lista";
		}
}
