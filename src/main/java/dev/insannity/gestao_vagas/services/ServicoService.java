package dev.insannity.gestao_vagas.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import dev.insannity.gestao_vagas.docs.Servico;
import dev.insannity.gestao_vagas.docs.Usuario;
import dev.insannity.gestao_vagas.enums.CategoriaServico;
import dev.insannity.gestao_vagas.enums.Permissao;
import dev.insannity.gestao_vagas.enums.TipoPrecoServico;
import dev.insannity.gestao_vagas.exceptions.RecursoNaoEncontradoException;
import dev.insannity.gestao_vagas.payloads.ServicoRequest;
import dev.insannity.gestao_vagas.repositories.ServicoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicoService {

    private final ServicoRepository servicoRepository;
    private final UsuarioService usuarioService;

    public Page<Servico> listarDisponiveis(String query, CategoriaServico categoria, Pageable pageable) {
        boolean temQuery = query != null && !query.trim().isEmpty();
        boolean temCategoria = categoria != null;

        if (temQuery && temCategoria) {
            return servicoRepository.findByTermoECategoria(query.trim(), categoria, pageable);
        } else if (temQuery) {
            return servicoRepository.findByTermo(query.trim(), pageable);
        } else if (temCategoria) {
            return servicoRepository.findByCategoriaAndAtivoTrueAndDeletadoIsNull(categoria, pageable);
        }
        return servicoRepository.findByAtivoTrueAndDeletadoIsNull(pageable);
    }

    public List<Servico> listarMeusServicos(String emailUsuario) {
        Usuario usuario = usuarioService.buscarPorEmail(emailUsuario);
        return servicoRepository.findByUsuarioIdAndDeletadoIsNullOrderByCriadoDesc(usuario.getId());
    }

    public Servico buscarPorId(String id) {
        return servicoRepository.findByIdAndDeletadoIsNull(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Serviço não encontrado para o id: " + id));
    }

    public Servico criar(String emailUsuario, ServicoRequest request) {
        Usuario usuario = usuarioService.buscarPorEmail(emailUsuario);

        if (usuario.getPermissao() != Permissao.CANDIDATO && usuario.getPermissao() != Permissao.ADMIN) {
            throw new AccessDeniedException("Apenas candidatos podem anunciar serviços profissionais na plataforma.");
        }

        Servico servico = request.toModel(usuario);
        return servicoRepository.save(servico);
    }

    public Servico atualizar(String id, String emailUsuario, ServicoRequest request) {
        Servico servico = buscarPorId(id);
        Usuario usuario = usuarioService.buscarPorEmail(emailUsuario);

        validarPropriedadeOuAdmin(servico, usuario);

        servico.setTitulo(request.getTitulo() != null ? request.getTitulo().trim() : null);
        servico.setDescricao(request.getDescricao() != null ? request.getDescricao().trim() : null);
        servico.setCategoria(request.getCategoria());
        servico.setTipoPreco(request.getTipoPreco());
        servico.setPreco(request.getTipoPreco() == TipoPrecoServico.A_COMBINAR ? null : request.getPreco());
        servico.setPrazoEstimado(request.getPrazoEstimado() != null ? request.getPrazoEstimado().trim() : "A combinar");
        servico.setTecnologias(request.obterListaTecnologias());
        if (request.getAtivo() != null) {
            servico.setAtivo(request.getAtivo());
        }
        servico.setAtualizado(LocalDateTime.now());

        return servicoRepository.save(servico);
    }

    public Servico toggleStatus(String id, String emailUsuario) {
        Servico servico = buscarPorId(id);
        Usuario usuario = usuarioService.buscarPorEmail(emailUsuario);

        validarPropriedadeOuAdmin(servico, usuario);

        servico.setAtivo(!servico.isAtivo());
        servico.setAtualizado(LocalDateTime.now());
        return servicoRepository.save(servico);
    }

    public void excluir(String id, String emailUsuario) {
        Servico servico = buscarPorId(id);
        Usuario usuario = usuarioService.buscarPorEmail(emailUsuario);

        validarPropriedadeOuAdmin(servico, usuario);

        servico.deletar();
        servicoRepository.save(servico);
    }

    private void validarPropriedadeOuAdmin(Servico servico, Usuario usuario) {
        boolean isAdmin = usuario.getPermissao() == Permissao.ADMIN;
        boolean isProprietario = servico.getUsuarioId() != null && servico.getUsuarioId().equals(usuario.getId());

        if (!isAdmin && !isProprietario) {
            throw new AccessDeniedException("Você não tem autorização para gerenciar este serviço.");
        }
    }

}
