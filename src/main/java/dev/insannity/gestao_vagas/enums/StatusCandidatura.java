package dev.insannity.gestao_vagas.enums;

import lombok.Getter;

@Getter
public enum StatusCandidatura {

    RECEBIDA("Recebida", "bg-indigo-500/10 text-indigo-400 border-indigo-500/30"),
    EM_TRIAGEM("Em Triagem", "bg-amber-500/10 text-amber-400 border-amber-500/30"),
    ENTREVISTA_TECNICA("Entrevista Técnica", "bg-blue-500/10 text-blue-400 border-blue-500/30"),
    APROVADO("Aprovado", "bg-emerald-500/10 text-emerald-400 border-emerald-500/30"),
    REPROVADO("Não Selecionado", "bg-rose-500/10 text-rose-400 border-rose-500/30");

    private final String descricao;
    private final String badgeClass;

    StatusCandidatura(String descricao, String badgeClass) {
        this.descricao = descricao;
        this.badgeClass = badgeClass;
    }

}
