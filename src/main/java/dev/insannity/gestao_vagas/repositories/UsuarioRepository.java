package dev.insannity.gestao_vagas.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import dev.insannity.gestao_vagas.docs.Usuario;
import dev.insannity.gestao_vagas.enums.Permissao;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {

    Optional<Usuario> findByEmail(String email);

    List<Usuario> findByPermissaoAndDisponivelParaContratacaoTrueAndDeletadoIsNullOrderByNomeAsc(Permissao permissao);

    List<Usuario> findByPermissaoAndDeletadoIsNullOrderByNomeAsc(Permissao permissao);

    @Query("{ 'permissao': 'CANDIDATO', 'disponivelParaContratacao': true, 'deletado': null, $or: [ { 'nome': { $regex: ?0, $options: 'i' } }, { 'cargo': { $regex: ?0, $options: 'i' } }, { 'localizacao': { $regex: ?0, $options: 'i' } }, { 'competencias': { $regex: ?0, $options: 'i' } } ] }")
    List<Usuario> findCandidatosDisponiveis(String termo);

}
