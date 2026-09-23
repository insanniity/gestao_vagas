package dev.insannity.gestao_vagas.config;

import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import dev.insannity.gestao_vagas.docs.Usuario;
import dev.insannity.gestao_vagas.docs.Vaga;
import dev.insannity.gestao_vagas.enums.Permissao;
import dev.insannity.gestao_vagas.repositories.UsuarioRepository;
import dev.insannity.gestao_vagas.repositories.VagaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitDB implements ApplicationRunner {

    private final UsuarioRepository usuarioRepository;
    private final VagaRepository vagaRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        usuarioRepository.deleteAll();
        Usuario usuario = Usuario.builder()
            .nome("Administrador")
            .ativo(true)
            .email("admin@insannity.dev")
            .permissao(Permissao.ADMIN)
            .senha(passwordEncoder.encode("admin123"))
            .build();
        usuarioRepository.save(usuario);
        log.info("Usuário inicial criado: {}", usuario.getEmail());

        vagaRepository.deleteAll();
        List<Vaga> vagas = List.of(
            Vaga.builder()
                .descricao("Vaga para Pessoa Desenvolvedora Java, que deseja trabalhar com Spring Boot 3.0 e microsserviços.")
                .beneficios("Gympass, Plano de Saúde, Day off no aniversário, Vale Alimentação e Refeição")
                .level("JUNIOR")
                .empresa("Java Jobs")
                .build(),
            Vaga.builder()
                .descricao("Vaga para Pessoa Desenvolvedora Java, com foco em manutenção e evolução de sistemas corporativos em Spring Boot e MongoDB.")
                .beneficios("Gympass, Plano de Saúde, Vale Alimentação e Refeição, Auxílio Home Office")
                .level("PLENO")
                .empresa("Java Jobs")
                .build(),
            Vaga.builder()
                .descricao("Arquiteto(a) de Software Java para desenhar e guiar a arquitetura de soluções em nuvem e mensageria distribuída.")
                .beneficios("Plano de Saúde Internacional, Seguro de Vida, PLR Semestral, Bônus de Contratação")
                .level("SENIOR")
                .empresa("TechCorp Solutions")
                .build()
        );
        vagaRepository.saveAll(vagas);
        log.info("{} vagas inseridas com sucesso.", vagas.size());
    }

}
