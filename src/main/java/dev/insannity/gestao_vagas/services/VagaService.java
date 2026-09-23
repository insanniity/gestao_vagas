package dev.insannity.gestao_vagas.services;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.insannity.gestao_vagas.docs.Vaga;
import dev.insannity.gestao_vagas.repositories.VagaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class VagaService {

    private final VagaRepository vagaRepository;

    public List<Vaga> listarTodas(String query) {
        if (query != null && !query.trim().isEmpty()) {
            return vagaRepository.findByTermo(query.trim());
        }
        return vagaRepository.findAll();
    }

}
