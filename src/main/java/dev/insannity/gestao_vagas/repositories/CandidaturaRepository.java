package dev.insannity.gestao_vagas.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import dev.insannity.gestao_vagas.docs.Candidatura;

public interface CandidaturaRepository extends MongoRepository<Candidatura, String> {

    List<Candidatura> findByUsuarioIdAndDeletadoIsNullOrderByDataCandidaturaDesc(String usuarioId);

    List<Candidatura> findByVagaIdAndDeletadoIsNullOrderByDataCandidaturaDesc(String vagaId);

    Optional<Candidatura> findByUsuarioIdAndVagaIdAndDeletadoIsNull(String usuarioId, String vagaId);

    boolean existsByUsuarioIdAndVagaIdAndDeletadoIsNull(String usuarioId, String vagaId);

    long countByUsuarioIdAndDeletadoIsNull(String usuarioId);

    long countByVagaIdAndDeletadoIsNull(String vagaId);

}
