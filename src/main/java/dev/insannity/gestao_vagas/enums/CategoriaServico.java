package dev.insannity.gestao_vagas.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoriaServico {

    DESENVOLVIMENTO_BACKEND("Desenvolvimento Back-end"),
    DESENVOLVIMENTO_FRONTEND("Desenvolvimento Front-end"),
    FULLSTACK("Desenvolvimento Full Stack"),
    DEVOPS_CLOUD("DevOps & Cloud"),
    ARQUITETURA_CONSULTORIA("Arquitetura & Consultoria"),
    MENTORIA_CODE_REVIEW("Mentoria & Code Review"),
    BANCO_DE_DADOS("Banco de Dados & Performance"),
    OUTRO("Outros Serviços");

    private final String descricao;

}
