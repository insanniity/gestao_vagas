package dev.insannity.gestao_vagas.repositories;
 
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import dev.insannity.gestao_vagas.docs.Vaga;

public interface VagaRepository extends MongoRepository<Vaga, String>{

    @Query("{ 'deletado': null, $or: [ { 'descricao': { $regex: ?0, $options: 'i' } }, { 'empresa': { $regex: ?0, $options: 'i' } }, { 'beneficios': { $regex: ?0, $options: 'i' } }, { 'level': { $regex: ?0, $options: 'i' } } ] }")
    Page<Vaga> findByTermo(String termo, Pageable pageable);

    @Query("{ 'deletado': null, $or: [ { 'descricao': { $regex: ?0, $options: 'i' } }, { 'empresa': { $regex: ?0, $options: 'i' } }, { 'beneficios': { $regex: ?0, $options: 'i' } }, { 'level': { $regex: ?0, $options: 'i' } } ] }")
    List<Vaga> findByTermo(String termo);

    Page<Vaga> findAllByDeletadoIsNull(Pageable pageable);

    List<Vaga> findAllByDeletadoIsNull();

    Optional<Vaga> findByIdAndDeletadoIsNull(String id);

}
