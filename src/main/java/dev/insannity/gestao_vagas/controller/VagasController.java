package dev.insannity.gestao_vagas.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dev.insannity.gestao_vagas.docs.Vaga;
import dev.insannity.gestao_vagas.services.VagaService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/vagas")
@RequiredArgsConstructor
public class VagasController {

    private final VagaService vagaService;

    @GetMapping("/")
    public String index(@RequestParam(name = "q", required = false) String query, Model model) {
        List<Vaga> vagas = vagaService.listarTodas(query);
        model.addAttribute("vagas", vagas);
        model.addAttribute("query", query != null ? query : "");
        return "vagas/index";
    }

}
