package com.superagregador.controller;

import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ControllerDeErro  implements ErrorController {
    
    private HashMap<Integer, String> erros;

    ControllerDeErro () {
        erros = new HashMap<>();
        erros.put(400, "Requisição inválida");
        erros.put(401, "Não autorizado");
        erros.put(402, "Pagamento necessário");
        erros.put(403, "Proibido");
        erros.put(404, "Página não encontrada");
        erros.put(405, "Método não permitido");
        erros.put(406, "Não Aceitável");
        erros.put(407, "Autenticação de proxy necessária");
        erros.put(408, "Tempo de requisição esgotou (Timeout)");
        erros.put(409, "Conflito geral");
        erros.put(410, "Gone");
        erros.put(411, "comprimento necessário");
        erros.put(412, "Pré-condição falhou");
        erros.put(413, "Entidade de solicitação muito grande");
        erros.put(414, "Pedido-URI Too Long");
        erros.put(415, "Tipo de mídia não suportado");
        erros.put(416, "Solicitada de Faixa Não Satisfatória");
        erros.put(417, "Falha na expectativa");
        erros.put(418, "Eu sou um bule de chá");
        erros.put(422, "Entidade improcessável (WebDAV) (RFC 4918)");
        erros.put(423, "Fechado (WebDAV) (RFC 4918)");
        erros.put(424, "Falha de Dependência (WebDAV) (RFC 4918)");
        erros.put(425, "coleção não ordenadrequired a bean of type 'java.util.HashMap' that could not be found.a (RFC 3648)");
        erros.put(426, "Upgrade Obrigatório (RFC 2817)");
        erros.put(429, "pedidos em excesso");
        erros.put(450, "bloqueados pelo Controle de Pais do Windows");
        erros.put(499, "cliente fechou Pedido (utilizado em ERPs/VPSA)");
        erros.put(500, "Erro interno do servidor");
        erros.put(501, "Não implementado");
        erros.put(502, "Bad Gateway");
        erros.put(503, "Serviço indisponível");
        erros.put(504, "Gateway timeout");
        erros.put(505, "Versão HTTP não suportada");

    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            model.addAttribute("erro", statusCode.intValue());
            model.addAttribute("msgDeErro", erros.get(statusCode.intValue()));
        }
        
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

}