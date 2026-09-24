package dev.insannity.gestao_vagas.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.insannity.gestao_vagas.docs.Candidatura;
import dev.insannity.gestao_vagas.enums.StatusCandidatura;
import dev.insannity.gestao_vagas.services.CandidaturaService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/candidaturas")
@RequiredArgsConstructor
public class CandidaturaController {

    private final CandidaturaService candidaturaService;

    @GetMapping
    public String minhasCandidaturas(Model model, Principal principal) {
        List<Candidatura> candidaturas = candidaturaService.listarMinhasCandidaturas(principal.getName());
        model.addAttribute("candidaturas", candidaturas);
        return "candidaturas/index";
    }

    @PostMapping("/vaga/{vagaId}")
    public String candidatar(
            @PathVariable String vagaId,
            Principal principal,
            RedirectAttributes redirectAttributes) {

        candidaturaService.candidatar(principal.getName(), vagaId);
        redirectAttributes.addFlashAttribute("sucesso", "Candidatura enviada com sucesso! Acompanhe o processo em Minhas Candidaturas.");
        return "redirect:/vagas/" + vagaId;
    }

    @PostMapping("/{id}/cancelar")
    public String cancelar(
            @PathVariable String id,
            Principal principal,
            RedirectAttributes redirectAttributes) {

        candidaturaService.cancelarCandidatura(id, principal.getName());
        redirectAttributes.addFlashAttribute("sucesso", "Candidatura cancelada com sucesso.");
        return "redirect:/candidaturas";
    }

    @PostMapping("/{id}/status")
    public String atualizarStatus(
            @PathVariable String id,
            @RequestParam("status") StatusCandidatura status,
            @RequestParam("vagaId") String vagaId,
            RedirectAttributes redirectAttributes) {

        candidaturaService.atualizarStatus(id, status);
        redirectAttributes.addFlashAttribute("sucesso", "Status do candidato atualizado para " + status.getDescricao() + "!");
        return "redirect:/vagas/" + vagaId;
    }

}
