package com.superagregador.controller;

import java.net.URL;
import java.util.ArrayList;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

@Controller
public class ControllerListarNoticias{
		
		@GetMapping("/lista")
		public String listarNoticias(Model model, HttpServletRequest request) throws Exception{
			List<String> linksDeBlogs = new ArrayList<String>();//eh aqui que vamos colocar os links
			Cookie[] cookies = request.getCookies();

			for (Cookie cookie : cookies) {
				if (cookie.getName().equals( "uri" )){
					String url = cookie.getValue();
					XmlReader reader = new XmlReader(new URL(url));
				SyndFeed feed = new SyndFeedInput().build(reader);
				for (SyndEntry syndEntry : feed.getEntries()) {//vamos ver o conteudo do feed fornecido
					String linkDeBlog = syndEntry.getLink();
					linksDeBlogs.add(linkDeBlog);
				}
				model.addAttribute("linksDeBlogs", linksDeBlogs);
				}
			}
			return "lista";
		}
}
