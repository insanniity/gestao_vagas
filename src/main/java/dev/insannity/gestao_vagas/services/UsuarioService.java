package dev.insannity.gestao_vagas.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import dev.insannity.gestao_vagas.docs.Usuario;
import dev.insannity.gestao_vagas.exceptions.RecursoNaoEncontradoException;
import dev.insannity.gestao_vagas.payloads.PerfilRequest;
import dev.insannity.gestao_vagas.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado para o e-mail: " + email));
    }

    public Usuario buscarPorId(String id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado para o id: " + id));
    }

    public List<Usuario> listarCandidatosDisponiveis(String query) {
        if (query != null && !query.trim().isEmpty()) {
            return usuarioRepository.findCandidatosDisponiveis(query.trim());
        }
        return usuarioRepository.findByDisponivelParaContratacaoTrueAndDeletadoIsNullOrderByNomeAsc();
    }

    public Usuario atualizarPerfil(String email, PerfilRequest request) {
        Usuario usuario = buscarPorEmail(email);

        usuario.setNome(request.getNome() != null ? request.getNome().trim() : null);
        usuario.setTelefone(request.getTelefone() != null ? request.getTelefone().trim() : null);
        usuario.setLocalizacao(request.getLocalizacao() != null ? request.getLocalizacao().trim() : null);
        usuario.setCargo(request.getCargo() != null ? request.getCargo().trim() : null);
        usuario.setResumo(request.getResumo() != null ? request.getResumo().trim() : null);
        usuario.setDisponivelParaContratacao(request.getDisponivelParaContratacao() != null ? request.getDisponivelParaContratacao() : true);
        usuario.setCompetencias(request.obterListaCompetencias());
        usuario.setLinkedin(request.getLinkedin() != null ? request.getLinkedin().trim() : null);
        usuario.setGithub(request.getGithub() != null ? request.getGithub().trim() : null);
        usuario.setAtualizado(LocalDateTime.now());

        return usuarioRepository.save(usuario);
    }

}
