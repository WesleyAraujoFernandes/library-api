package io.github.cursodsousa.libraryapi.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.github.cursodsousa.libraryapi.security.CustomAuthentication;

@Controller
public class LoginViewController {
    @GetMapping("/login")
    public String paginaLogin() {
        return "login"; //setViewName()
    }

    @GetMapping("/")
    @ResponseBody
    public String paginaHome(Authentication authentication) {
        if (authentication instanceof CustomAuthentication customAuth) {
            System.out.println(customAuth.getUsuario());
        }
        return "Olá " + authentication.getName();
    }
}
