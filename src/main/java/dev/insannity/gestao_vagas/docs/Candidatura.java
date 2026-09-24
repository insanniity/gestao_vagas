package dev.insannity.gestao_vagas.docs;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import dev.insannity.gestao_vagas.enums.StatusCandidatura;
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
@Document("candidaturas")
@CompoundIndex(name = "usuario_vaga_idx", def = "{'usuarioId': 1, 'vagaId': 1}")
public class Candidatura extends Doc {

    String usuarioId;
    String usuarioNome;
    String usuarioEmail;
    String usuarioTelefone;
    String usuarioCargo;
    String usuarioLinkedin;
    String usuarioGithub;

    String vagaId;
    String vagaEmpresa;
    String vagaDescricao;
    String vagaLevel;

    @Builder.Default
    StatusCandidatura status = StatusCandidatura.RECEBIDA;

    @Builder.Default
    LocalDateTime dataCandidatura = LocalDateTime.now();

}
