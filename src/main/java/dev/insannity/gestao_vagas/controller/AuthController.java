package dev.insannity.gestao_vagas.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.insannity.gestao_vagas.docs.Usuario;


@Controller
@RequestMapping("/")
public class AuthController {

    @GetMapping("/")
    public String index() {
        // se autenticado
        if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return "redirect:/perfil";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/perfil")
    public String perfil(Model model, @AuthenticationPrincipal Usuario usuario) {
        model.addAttribute("usuario", usuario);
        return "perfil";
    }

}
