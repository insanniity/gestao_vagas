package dev.insannity.gestao_vagas.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import dev.insannity.gestao_vagas.docs.Vaga;

public interface VagaRepository extends MongoRepository<Vaga, String>{

    @Query("{ $or: [ { 'descricao': { $regex: ?0, $options: 'i' } }, { 'empresa': { $regex: ?0, $options: 'i' } }, { 'beneficios': { $regex: ?0, $options: 'i' } }, { 'level': { $regex: ?0, $options: 'i' } } ] }")
    List<Vaga> findByTermo(String termo);

}
