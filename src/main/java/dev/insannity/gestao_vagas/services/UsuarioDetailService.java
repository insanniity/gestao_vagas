package dev.insannity.gestao_vagas.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.insannity.gestao_vagas.docs.Usuario;
import dev.insannity.gestao_vagas.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class UsuarioDetailService implements UserDetailsService{

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(username)
               .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        return usuario;
    }

}
