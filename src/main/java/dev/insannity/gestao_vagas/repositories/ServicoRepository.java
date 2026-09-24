package dev.insannity.gestao_vagas.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import dev.insannity.gestao_vagas.docs.Servico;
import dev.insannity.gestao_vagas.enums.CategoriaServico;

public interface ServicoRepository extends MongoRepository<Servico, String> {

    Optional<Servico> findByIdAndDeletadoIsNull(String id);

    Page<Servico> findByAtivoTrueAndDeletadoIsNull(Pageable pageable);

    Page<Servico> findByCategoriaAndAtivoTrueAndDeletadoIsNull(CategoriaServico categoria, Pageable pageable);

    @Query("{ 'ativo': true, 'deletado': null, $or: [ { 'titulo': { $regex: ?0, $options: 'i' } }, { 'descricao': { $regex: ?0, $options: 'i' } }, { 'tecnologias': { $regex: ?0, $options: 'i' } }, { 'usuarioNome': { $regex: ?0, $options: 'i' } } ] }")
    Page<Servico> findByTermo(String termo, Pageable pageable);

    @Query("{ 'categoria': ?1, 'ativo': true, 'deletado': null, $or: [ { 'titulo': { $regex: ?0, $options: 'i' } }, { 'descricao': { $regex: ?0, $options: 'i' } }, { 'tecnologias': { $regex: ?0, $options: 'i' } }, { 'usuarioNome': { $regex: ?0, $options: 'i' } } ] }")
    Page<Servico> findByTermoECategoria(String termo, CategoriaServico categoria, Pageable pageable);

    List<Servico> findByUsuarioIdAndDeletadoIsNullOrderByCriadoDesc(String usuarioId);

    long countByUsuarioIdAndDeletadoIsNull(String usuarioId);

}
