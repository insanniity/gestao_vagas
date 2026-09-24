package dev.insannity.gestao_vagas.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.insannity.gestao_vagas.docs.Candidatura;
import dev.insannity.gestao_vagas.docs.Usuario;
import dev.insannity.gestao_vagas.docs.Vaga;
import dev.insannity.gestao_vagas.enums.StatusCandidatura;
import dev.insannity.gestao_vagas.exceptions.RecursoNaoEncontradoException;
import dev.insannity.gestao_vagas.repositories.CandidaturaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CandidaturaService {

    private final CandidaturaRepository candidaturaRepository;
    private final UsuarioService usuarioService;
    private final VagaService vagaService;

    public Candidatura candidatar(String emailUsuario, String vagaId) {
        Usuario usuario = usuarioService.buscarPorEmail(emailUsuario);
        Vaga vaga = vagaService.buscarPorId(vagaId);

        Optional<Candidatura> existente = candidaturaRepository.findByUsuarioIdAndVagaIdAndDeletadoIsNull(usuario.getId(), vagaId);
        if (existente.isPresent()) {
            return existente.get();
        }

        Candidatura candidatura = Candidatura.builder()
                .usuarioId(usuario.getId())
                .usuarioNome(usuario.getNome() != null ? usuario.getNome() : usuario.getEmail())
                .usuarioEmail(usuario.getEmail())
                .usuarioTelefone(usuario.getTelefone())
                .usuarioCargo(usuario.getCargo())
                .usuarioLinkedin(usuario.getLinkedin())
                .usuarioGithub(usuario.getGithub())
                .vagaId(vaga.getId())
                .vagaEmpresa(vaga.getEmpresa())
                .vagaDescricao(vaga.getDescricao())
                .vagaLevel(vaga.getLevel())
                .status(StatusCandidatura.RECEBIDA)
                .dataCandidatura(LocalDateTime.now())
                .build();

        return candidaturaRepository.save(candidatura);
    }

    public List<Candidatura> listarMinhasCandidaturas(String emailUsuario) {
        Usuario usuario = usuarioService.buscarPorEmail(emailUsuario);
        return candidaturaRepository.findByUsuarioIdAndDeletadoIsNullOrderByDataCandidaturaDesc(usuario.getId());
    }

    public List<Candidatura> listarCandidatosDaVaga(String vagaId) {
        return candidaturaRepository.findByVagaIdAndDeletadoIsNullOrderByDataCandidaturaDesc(vagaId);
    }

    public boolean jaCandidatado(String usuarioId, String vagaId) {
        return candidaturaRepository.existsByUsuarioIdAndVagaIdAndDeletadoIsNull(usuarioId, vagaId);
    }

    public Optional<Candidatura> obterCandidatura(String usuarioId, String vagaId) {
        return candidaturaRepository.findByUsuarioIdAndVagaIdAndDeletadoIsNull(usuarioId, vagaId);
    }

    public Candidatura atualizarStatus(String candidaturaId, StatusCandidatura novoStatus) {
        Candidatura candidatura = candidaturaRepository.findById(candidaturaId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Candidatura não encontrada: " + candidaturaId));
        candidatura.setStatus(novoStatus);
        candidatura.setAtualizado(LocalDateTime.now());
        return candidaturaRepository.save(candidatura);
    }

    public void cancelarCandidatura(String candidaturaId, String emailUsuario) {
        Usuario usuario = usuarioService.buscarPorEmail(emailUsuario);
        Candidatura candidatura = candidaturaRepository.findById(candidaturaId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Candidatura não encontrada: " + candidaturaId));

        if (!candidatura.getUsuarioId().equals(usuario.getId())) {
            throw new org.springframework.security.access.AccessDeniedException("Você não tem permissão para cancelar esta candidatura.");
        }

        candidatura.deletar();
        candidaturaRepository.save(candidatura);
    }

    public long contarCandidaturasUsuario(String usuarioId) {
        return candidaturaRepository.countByUsuarioIdAndDeletadoIsNull(usuarioId);
    }

    public Candidatura atribuirCandidatoAVaga(String usuarioId, String vagaId) {
        Usuario usuario = usuarioService.buscarPorId(usuarioId);
        Vaga vaga = vagaService.buscarPorId(vagaId);

        Optional<Candidatura> existente = candidaturaRepository.findByUsuarioIdAndVagaIdAndDeletadoIsNull(usuario.getId(), vagaId);
        if (existente.isPresent()) {
            return existente.get();
        }

        Candidatura candidatura = Candidatura.builder()
                .usuarioId(usuario.getId())
                .usuarioNome(usuario.getNome() != null ? usuario.getNome() : usuario.getEmail())
                .usuarioEmail(usuario.getEmail())
                .usuarioTelefone(usuario.getTelefone())
                .usuarioCargo(usuario.getCargo())
                .usuarioLinkedin(usuario.getLinkedin())
                .usuarioGithub(usuario.getGithub())
                .vagaId(vaga.getId())
                .vagaEmpresa(vaga.getEmpresa())
                .vagaDescricao(vaga.getDescricao())
                .vagaLevel(vaga.getLevel())
                .status(StatusCandidatura.RECEBIDA)
                .dataCandidatura(LocalDateTime.now())
                .build();

        return candidaturaRepository.save(candidatura);
    }

}
