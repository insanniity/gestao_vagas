package dev.insannity.gestao_vagas.docs;

import org.springframework.data.mongodb.core.mapping.Document;

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
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
@Document("vagas")
public class Vaga extends Doc {

    String descricao;
    String beneficios;
    String level;
    String empresa;

}
