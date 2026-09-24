package dev.insannity.gestao_vagas.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.insannity.gestao_vagas.docs.Vaga;
import dev.insannity.gestao_vagas.payloads.VagaRequest;
import dev.insannity.gestao_vagas.services.VagaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/vagas")
@RequiredArgsConstructor
public class VagasController {

    private final VagaService vagaService;

    @ModelAttribute("niveis")
    public List<String> niveis() {
        return List.of("ESTAGIO", "JUNIOR", "PLENO", "SENIOR", "ESPECIALISTA");
    }

    @GetMapping({"", "/"})
    public String index(
            @RequestParam(value = "q", required = false) String query,
            @PageableDefault(size = 10, sort = "criado", direction = Sort.Direction.DESC) Pageable pageable,
            Model model) {

        Page<Vaga> vagasPage = vagaService.listarTodas(query, pageable);
        model.addAttribute("vagasPage", vagasPage);
        model.addAttribute("vagas", vagasPage.getContent());
        model.addAttribute("query", query);
        return "vagas/index";
    }

    @GetMapping("/nova")
    public String formCadastro(Model model) {
        if (!model.containsAttribute("vagaRequest")) {
            model.addAttribute("vagaRequest", new VagaRequest());
        }
        return "vagas/nova";
    }

    @PostMapping("/nova")
    public String cadastrar(
            @Valid @ModelAttribute("vagaRequest") VagaRequest vagaRequest,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "vagas/nova";
        }

        vagaService.cadastrar(vagaRequest);
        redirectAttributes.addFlashAttribute("sucesso", "Vaga publicada com sucesso!");
        return "redirect:/vagas";
    }

    @GetMapping("/{id}")
    public String ver(@PathVariable String id, Model model) {
        Vaga vaga = vagaService.buscarPorId(id);
        model.addAttribute("vaga", vaga);
        return "vagas/detalhes";
    }

    @GetMapping("/{id}/editar")
    public String formEdicao(@PathVariable String id, Model model) {
        Vaga vaga = vagaService.buscarPorId(id);
        if (!model.containsAttribute("vagaRequest")) {
            model.addAttribute("vagaRequest", VagaRequest.fromModel(vaga));
        }
        model.addAttribute("vagaId", id);
        return "vagas/editar";
    }

    @PostMapping("/{id}/editar")
    public String atualizar(
            @PathVariable String id,
            @Valid @ModelAttribute("vagaRequest") VagaRequest vagaRequest,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("vagaId", id);
            return "vagas/editar";
        }

        vagaService.atualizar(id, vagaRequest);
        redirectAttributes.addFlashAttribute("sucesso", "Vaga atualizada com sucesso!");
        return "redirect:/vagas/" + id;
    }

    @PostMapping("/{id}/apagar")
    public String apagar(@PathVariable String id, RedirectAttributes redirectAttributes) {
        vagaService.apagar(id);
        redirectAttributes.addFlashAttribute("sucesso", "Vaga excluída com sucesso!");
        return "redirect:/vagas";
    }

}
