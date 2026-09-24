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
                .empresa("Java Jobs")
                .level("JUNIOR")
                .descricao("Desenvolvedor(a) Java Júnior focado em desenvolvimento de APIs RESTful utilizando Spring Boot 3, Maven e PostgreSQL.")
                .beneficios("Gympass, Plano de Saúde, Day off no aniversário, Vale Alimentação e Refeição")
                .build(),
            Vaga.builder()
                .empresa("Java Jobs")
                .level("PLENO")
                .descricao("Pessoa Desenvolvedora Java Pleno com experiência em microsserviços, Spring Cloud, mensageria com Apache Kafka e MongoDB.")
                .beneficios("Gympass, Plano de Saúde Nacional, VR/VA de R$ 1.500, Auxílio Home Office")
                .build(),
            Vaga.builder()
                .empresa("TechCorp Solutions")
                .level("SENIOR")
                .descricao("Arquiteto(a) de Software Java Sênior para liderar modernização de sistemas legados para arquitetura de microsserviços e nuvem AWS.")
                .beneficios("Plano de Saúde Internacional, Seguro de Vida, PLR Semestral, Bônus de Contratação, Horário Flexível")
                .build(),
            Vaga.builder()
                .empresa("Nubank")
                .level("PLENO")
                .descricao("Engenheiro(a) de Software Back-end com foco em sistemas de alta disponibilidade, baixa latência e concorrência distribuída em Java e Clojure.")
                .beneficios("NuCare (apoio psicológico), Vale Refeição/Alimentação flexível, Gympass Platinum, Stock Options")
                .build(),
            Vaga.builder()
                .empresa("Mercado Livre")
                .level("SENIOR")
                .descricao("Senior Backend Developer especializado no ecossistema Spring Boot, Redis e Kubernetes para suporte ao tráfego massivo do Mercado Pago.")
                .beneficios("Previdência Privada, Subsídio para Estudos de Pós-graduação, Vale Transporte/Combustível, Convênio Farmácia")
                .build(),
            Vaga.builder()
                .empresa("Itaú Unibanco")
                .level("ESPECIALISTA")
                .descricao("Especialista em Engenharia de Software Java com foco em computação distribuída, resiliência financeira, Cloud AWS e observabilidade Datadog.")
                .beneficios("PLR atrativa de até 3 salários, Vale Refeição/Alimentação, Plano Odontológico Bradesco, Auxílio Creche")
                .build(),
            Vaga.builder()
                .empresa("PicPay")
                .level("JUNIOR")
                .descricao("Desenvolvedor(a) Back-end Java Júnior para apoiar a squad de cartões e pagamentos instantâneos PIX, com testes unitários em JUnit 5 e Mockito.")
                .beneficios("Cartão Caju multiflexível, Seguro Saúde SulAmérica, Convênio SESC, Licença Parental Estendida")
                .build(),
            Vaga.builder()
                .empresa("Stone")
                .level("PLENO")
                .descricao("Engenheiro(a) de Software Java Pleno com sólidos conhecimentos em Clean Architecture, Domain-Driven Design (DDD) e mensageria RabbitMQ.")
                .beneficios("Vale Alimentação e Refeição flexível, Plano de Saúde e Odontológico, Auxílio Creche, Seguro de Vida")
                .build(),
            Vaga.builder()
                .empresa("iFood")
                .level("SENIOR")
                .descricao("Desenvolvedor(a) Java Sênior para atuar na plataforma de logística em tempo real, integrando microsserviços via gRPC e event-driven architecture.")
                .beneficios("iFood Refeição flexível no app, Auxílio Educação anual, Plano de Saúde e Dental sem coparticipação, Gympass")
                .build(),
            Vaga.builder()
                .empresa("Zup Innovation")
                .level("ESTAGIO")
                .descricao("Programa de Estágio em Tecnologia focado em desenvolvimento Java, POO, estruturas de dados, Git e boas práticas de engenharia de software.")
                .beneficios("Bolsa auxílio competitiva, Vale Refeição, Seguro de Vida em Grupo, Trilha de Capacitação e Mentoria Técnica")
                .build(),
            Vaga.builder()
                .empresa("CI&T")
                .level("PLENO")
                .descricao("Full Stack Developer (Java 21 + React / TypeScript) para projetos globais de transformação digital no setor de telecomunicações.")
                .beneficios("Programa de idiomas (inglês/espanhol gratuito), TotalPass, VR/VA, Assistência Médica e Odontológica Amil")
                .build(),
            Vaga.builder()
                .empresa("Totvs")
                .level("SENIOR")
                .descricao("Analista Desenvolvedor(a) Java Sênior para evolução do ERP corporativo em nuvem, tuning de banco de dados e otimização de queries MongoDB e SQL.")
                .beneficios("Auxílio Farmácia, Previdência Privada, Vale Refeição, Convênio com Universidades e Escolas de Idiomas")
                .build(),
            Vaga.builder()
                .empresa("Ambev Tech")
                .level("JUNIOR")
                .descricao("Desenvolvedor(a) Java Júnior para desenvolvimento de serviços no aplicativo B2B Bees, utilizando Spring Data, Docker e testes automatizados.")
                .beneficios("Desconto em produtos da Cervejaria, VR/VA Sodexo, Gympass, Plano de Saúde Unimed Nacional, Bônus Anual")
                .build(),
            Vaga.builder()
                .empresa("Globo")
                .level("PLENO")
                .descricao("Engenheiro(a) de Software Pleno para sustentação e criação de APIs de alta performance no ecossistema Globoplay e ge.globo.")
                .beneficios("Globoplay grátis com canais ao vivo, Participação nos Lucros (PLR), Vale Transporte/Fretado, Ticket Alimentação")
                .build(),
            Vaga.builder()
                .empresa("Creditas")
                .level("SENIOR")
                .descricao("Tech Lead / Staff Java Engineer para guiar padrões técnicos e arquitetura na esteira de concessão de crédito imobiliário e auto.")
                .beneficios("Crédito consignado com taxa preferencial, Seguro de Saúde Bradesco Top, Zenklub para saúde mental, Caju flexível")
                .build(),
            Vaga.builder()
                .empresa("QuintoAndar")
                .level("PLENO")
                .descricao("Software Engineer focado em integrações de back-end em Java/Kotlin com serviços gerenciados na nuvem GCP e arquitetura orientada a eventos.")
                .beneficios("Auxílio Home Office mensal, Caju flexível de R$ 1.600, Gympass, Plano de Saúde SulAmérica, Auxílio Creche")
                .build(),
            Vaga.builder()
                .empresa("PagBank")
                .level("JUNIOR")
                .descricao("Pessoa Desenvolvedora Júnior para atuar na squad de segurança bancária e prevenção a fraudes em Java e Spring Security.")
                .beneficios("Vale Alimentação e Refeição, Plano de Saúde e Odontológico, TotalPass, PLR semestral vinculada a metas")
                .build(),
            Vaga.builder()
                .empresa("Thoughtworks")
                .level("ESPECIALISTA")
                .descricao("Consultor(a) Técnico Especialista em Java, Continuous Delivery, Arquitetura Evolutiva e práticas ágeis de engenharia de software (TDD/XP).")
                .beneficios("Orçamento anual para conferências e certificações, 30 dias de licença parental, Plano de Saúde Premium, VR flexível")
                .build(),
            Vaga.builder()
                .empresa("Stefanini")
                .level("ESTAGIO")
                .descricao("Estágio em Desenvolvimento de Software com Java e Spring Boot para atuação em projetos de grandes clientes bancários e governamentais.")
                .beneficios("Bolsa auxílio, Vale Transporte, Seguro de Vida, Acesso gratuito à plataforma Alura e Udemy Business")
                .build(),
            Vaga.builder()
                .empresa("Insannity Dev")
                .level("ESPECIALISTA")
                .descricao("Lead Architect para desenho e construção da nova plataforma SaaS de gestão e recrutamento de desenvolvedores em Spring Boot 4 e MongoDB.")
                .beneficios("Contratação PJ/CLT flexível, Bônus por entregas, Equipamento Apple/Dell fornecido pela empresa, Horários 100% livres")
                .build()
        );
        vagaRepository.saveAll(vagas);
        log.info("{} vagas inseridas com sucesso.", vagas.size());
    }

}
