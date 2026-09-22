package dev.insannity.gestao_vagas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.insannity.gestao_vagas.payloads.LoginRequest;
import io.micrometer.common.util.StringUtils;


@Controller
@RequestMapping("/")
public class AuthController {

    @GetMapping({"", "/", "/login"})
    public String login(Model model) {
        if (!model.containsAttribute("loginRequest")) {
            model.addAttribute("loginRequest", new LoginRequest("teste@insannity.dev", "123456"));
        }
        return "login";
    }

    @GetMapping("/perfil")
    public String perfil(Model model) {
        return "perfil";
    }

    @PostMapping({"/login", "/", ""})
    public String login(RedirectAttributes redirectAttributes, LoginRequest loginRequest) {
        if(StringUtils.isBlank(loginRequest.username()) || StringUtils.isBlank(loginRequest.password())) {
            redirectAttributes.addFlashAttribute("error", "Usuário ou senha inválidos.");
            redirectAttributes.addFlashAttribute("loginRequest", loginRequest);
            return "redirect:/";
        }
        if(loginRequest.username().equals("teste@insannity.dev") && !loginRequest.password().equals("123456")) {
            redirectAttributes.addFlashAttribute("error", "Usuário ou senha inválidos.");
            redirectAttributes.addFlashAttribute("loginRequest", loginRequest);
            return "redirect:/";
        }
        redirectAttributes.addFlashAttribute("error", "Usuário ou senha inválidos.");
        redirectAttributes.addFlashAttribute("loginRequest", loginRequest);
        return "redirect:/perfil";
    }

}
