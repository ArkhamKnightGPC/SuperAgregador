package com.superagregador.controller;

import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.superagregador.view.ErrorHandler;

@Controller
public class ControllerDeErro  implements ErrorController {
    
    private HashMap<Integer, String> erros;
    
    ControllerDeErro() {
        ErrorHandler eh = new ErrorHandler();
        erros = eh.getErros();
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