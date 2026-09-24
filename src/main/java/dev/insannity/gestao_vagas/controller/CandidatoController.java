package dev.insannity.gestao_vagas.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dev.insannity.gestao_vagas.docs.Usuario;
import dev.insannity.gestao_vagas.services.UsuarioService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/candidatos")
@RequiredArgsConstructor
public class CandidatoController {

    private final UsuarioService usuarioService;

    @GetMapping
    public String index(@RequestParam(value = "q", required = false) String query, Model model) {
        List<Usuario> candidatos = usuarioService.listarCandidatosDisponiveis(query);
        model.addAttribute("candidatos", candidatos);
        model.addAttribute("query", query);
        return "candidatos/index";
    }

    @GetMapping("/{id}")
    public String detalhes(@PathVariable String id, Model model) {
        Usuario candidato = usuarioService.buscarPorId(id);
        model.addAttribute("candidato", candidato);
        return "candidatos/detalhes";
    }

}
