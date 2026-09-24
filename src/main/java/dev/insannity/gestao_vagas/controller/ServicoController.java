package dev.insannity.gestao_vagas.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
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

import dev.insannity.gestao_vagas.docs.Servico;
import dev.insannity.gestao_vagas.docs.Usuario;
import dev.insannity.gestao_vagas.enums.CategoriaServico;
import dev.insannity.gestao_vagas.enums.Permissao;
import dev.insannity.gestao_vagas.enums.TipoPrecoServico;
import dev.insannity.gestao_vagas.payloads.ServicoRequest;
import dev.insannity.gestao_vagas.services.ServicoService;
import dev.insannity.gestao_vagas.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/servicos")
@RequiredArgsConstructor
public class ServicoController {

    private final ServicoService servicoService;
    private final UsuarioService usuarioService;

    @ModelAttribute("todasCategorias")
    public CategoriaServico[] todasCategorias() {
        return CategoriaServico.values();
    }

    @ModelAttribute("todosTiposPreco")
    public TipoPrecoServico[] todosTiposPreco() {
        return TipoPrecoServico.values();
    }

    @GetMapping({"", "/"})
    public String index(
            @RequestParam(value = "q", required = false) String query,
            @RequestParam(value = "categoria", required = false) CategoriaServico categoria,
            @PageableDefault(size = 9, sort = "criado", direction = Sort.Direction.DESC) Pageable pageable,
            Model model) {

        Page<Servico> servicosPage = servicoService.listarDisponiveis(query, categoria, pageable);
        model.addAttribute("servicosPage", servicosPage);
        model.addAttribute("servicos", servicosPage.getContent());
        model.addAttribute("query", query);
        model.addAttribute("categoriaSelecionada", categoria);

        return "servicos/index";
    }

    @GetMapping("/meus-servicos")
    @PreAuthorize("hasRole('CANDIDATO')")
    public String meusServicos(Model model, Principal principal) {
        List<Servico> meusServicos = servicoService.listarMeusServicos(principal.getName());
        model.addAttribute("servicos", meusServicos);
        return "servicos/meus-servicos";
    }

    @GetMapping("/novo")
    @PreAuthorize("hasRole('CANDIDATO')")
    public String formCadastro(Model model) {
        if (!model.containsAttribute("servicoRequest")) {
            model.addAttribute("servicoRequest", new ServicoRequest());
        }
        return "servicos/novo";
    }

    @PostMapping("/novo")
    @PreAuthorize("hasRole('CANDIDATO')")
    public String cadastrar(
            @Valid @ModelAttribute("servicoRequest") ServicoRequest servicoRequest,
            BindingResult bindingResult,
            Principal principal,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "servicos/novo";
        }

        Servico servico = servicoService.criar(principal.getName(), servicoRequest);
        redirectAttributes.addFlashAttribute("sucesso", "Serviço publicado com sucesso na vitrine profissional!");
        return "redirect:/servicos/" + servico.getId();
    }

    @GetMapping("/{id}")
    public String ver(@PathVariable String id, Model model, Principal principal) {
        Servico servico = servicoService.buscarPorId(id);
        model.addAttribute("servico", servico);

        boolean podeEditar = false;
        if (principal != null) {
            Usuario usuario = usuarioService.buscarPorEmail(principal.getName());
            boolean isAdmin = usuario.getPermissao() == Permissao.ADMIN;
            boolean isDono = servico.getUsuarioId() != null && servico.getUsuarioId().equals(usuario.getId());
            podeEditar = isAdmin || isDono;
        }
        model.addAttribute("podeEditar", podeEditar);

        return "servicos/detalhes";
    }

    @GetMapping("/{id}/editar")
    public String formEdicao(@PathVariable String id, Model model, Principal principal) {
        Servico servico = servicoService.buscarPorId(id);

        if (principal != null) {
            Usuario usuario = usuarioService.buscarPorEmail(principal.getName());
            boolean isAdmin = usuario.getPermissao() == Permissao.ADMIN;
            boolean isDono = servico.getUsuarioId() != null && servico.getUsuarioId().equals(usuario.getId());
            if (!isAdmin && !isDono) {
                return "redirect:/servicos/" + id;
            }
        }

        if (!model.containsAttribute("servicoRequest")) {
            model.addAttribute("servicoRequest", ServicoRequest.fromModel(servico));
        }
        model.addAttribute("servicoId", id);
        return "servicos/editar";
    }

    @PostMapping("/{id}/editar")
    public String atualizar(
            @PathVariable String id,
            @Valid @ModelAttribute("servicoRequest") ServicoRequest servicoRequest,
            BindingResult bindingResult,
            Principal principal,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("servicoId", id);
            return "servicos/editar";
        }

        servicoService.atualizar(id, principal.getName(), servicoRequest);
        redirectAttributes.addFlashAttribute("sucesso", "Serviço atualizado com sucesso!");
        return "redirect:/servicos/" + id;
    }

    @PostMapping("/{id}/toggle-status")
    public String toggleStatus(
            @PathVariable String id,
            Principal principal,
            RedirectAttributes redirectAttributes) {

        Servico servico = servicoService.toggleStatus(id, principal.getName());
        String msg = servico.isAtivo() ? "Serviço reativado e visível na vitrine!" : "Serviço pausado temporariamente.";
        redirectAttributes.addFlashAttribute("sucesso", msg);
        return "redirect:/servicos/" + id;
    }

    @PostMapping("/{id}/excluir")
    public String excluir(
            @PathVariable String id,
            Principal principal,
            RedirectAttributes redirectAttributes) {

        servicoService.excluir(id, principal.getName());
        redirectAttributes.addFlashAttribute("sucesso", "Serviço removido com sucesso.");
        return "redirect:/servicos";
    }

}
