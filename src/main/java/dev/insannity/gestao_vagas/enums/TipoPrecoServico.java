package dev.insannity.gestao_vagas.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TipoPrecoServico {

    POR_HORA("Por Hora"),
    POR_PROJETO("Por Projeto / Entrega"),
    A_COMBINAR("A Combinar");

    private final String descricao;

}
