package dev.insannity.gestao_vagas.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dev.insannity.gestao_vagas.docs.Vaga;
import dev.insannity.gestao_vagas.exceptions.RecursoNaoEncontradoException;
import dev.insannity.gestao_vagas.payloads.VagaRequest;
import dev.insannity.gestao_vagas.repositories.VagaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class VagaService {

    private final VagaRepository vagaRepository;

    public Page<Vaga> listarTodas(String query, Pageable pageable) {
        if (query != null && !query.trim().isEmpty()) {
            return vagaRepository.findByTermo(query.trim(), pageable);
        }
        return vagaRepository.findAllByDeletadoIsNull(pageable);
    }

    public List<Vaga> listarTodas(String query) {
        if (query != null && !query.trim().isEmpty()) {
            return vagaRepository.findByTermo(query.trim());
        }
        return vagaRepository.findAllByDeletadoIsNull();
    }

    public Vaga buscarPorId(String id) {
        return vagaRepository.findByIdAndDeletadoIsNull(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Vaga não encontrada para o id: " + id));
    }

    public Vaga cadastrar(VagaRequest request) {
        Vaga vaga = request.toModel();
        return vagaRepository.save(vaga);
    }

    public Vaga atualizar(String id, VagaRequest request) {
        Vaga vaga = buscarPorId(id);
        vaga.setEmpresa(request.getEmpresa() != null ? request.getEmpresa().trim() : null);
        vaga.setLevel(request.getLevel() != null ? request.getLevel().trim().toUpperCase() : null);
        vaga.setDescricao(request.getDescricao() != null ? request.getDescricao().trim() : null);
        vaga.setBeneficios(request.getBeneficios() != null ? request.getBeneficios().trim() : null);
        if (request.getEncerrada() != null) {
            vaga.setEncerrada(request.getEncerrada());
        }
        vaga.setAtualizado(LocalDateTime.now());
        return vagaRepository.save(vaga);
    }

    public void encerrar(String id) {
        Vaga vaga = buscarPorId(id);
        vaga.encerrar();
        vagaRepository.save(vaga);
    }

    public void reabrir(String id) {
        Vaga vaga = buscarPorId(id);
        vaga.reabrir();
        vagaRepository.save(vaga);
    }

    public void apagar(String id) {
        Vaga vaga = buscarPorId(id);
        vaga.deletar();
        vagaRepository.save(vaga);
    }

}

