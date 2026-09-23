package dev.insannity.gestao_vagas.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import dev.insannity.gestao_vagas.docs.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    Optional<Usuario> findByEmail(String email);
}
