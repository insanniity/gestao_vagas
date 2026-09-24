package dev.insannity.gestao_vagas.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.insannity.gestao_vagas.docs.Candidatura;
import dev.insannity.gestao_vagas.docs.Usuario;
import dev.insannity.gestao_vagas.payloads.PerfilRequest;
import dev.insannity.gestao_vagas.services.UsuarioService;
import dev.insannity.gestao_vagas.services.CandidaturaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;
    private final CandidaturaService candidaturaService;

    @GetMapping("/")
    public String index() {
        // se autenticado
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return "redirect:/perfil";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/perfil")
    public String perfil(Model model, Principal principal) {
        Usuario usuario = usuarioService.buscarPorEmail(principal.getName());
        model.addAttribute("usuario", usuario);
        if (!model.containsAttribute("perfilRequest")) {
            model.addAttribute("perfilRequest", PerfilRequest.fromModel(usuario));
        }

        List<Candidatura> candidaturas = candidaturaService.listarMinhasCandidaturas(principal.getName());
        model.addAttribute("candidaturas", candidaturas);

        return "perfil";
    }

    @PostMapping("/perfil")
    public String atualizarPerfil(
            @Valid @ModelAttribute("perfilRequest") PerfilRequest perfilRequest,
            BindingResult bindingResult,
            Principal principal,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            Usuario usuario = usuarioService.buscarPorEmail(principal.getName());
            model.addAttribute("usuario", usuario);
            return "perfil";
        }

        usuarioService.atualizarPerfil(principal.getName(), perfilRequest);
        redirectAttributes.addFlashAttribute("sucesso", "Perfil atualizado com sucesso!");
        return "redirect:/perfil";
    }

}
