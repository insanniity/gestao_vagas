package dev.insannity.gestao_vagas.docs;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import dev.insannity.gestao_vagas.enums.CategoriaServico;
import dev.insannity.gestao_vagas.enums.TipoPrecoServico;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Document("servicos")
public class Servico extends Doc {

    String titulo;
    String descricao;
    CategoriaServico categoria;
    TipoPrecoServico tipoPreco;
    BigDecimal preco;
    String prazoEstimado;

    @Builder.Default
    List<String> tecnologias = new ArrayList<>();

    @Builder.Default
    Boolean ativo = true;

    // Dados do Prestador (Candidato)
    String usuarioId;
    String usuarioNome;
    String usuarioEmail;
    String usuarioTelefone;
    String usuarioCargo;
    String usuarioLinkedin;
    String usuarioGithub;

    public boolean isAtivo() {
        return Boolean.TRUE.equals(this.ativo);
    }

}
