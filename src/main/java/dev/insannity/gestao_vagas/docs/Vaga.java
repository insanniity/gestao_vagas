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

    @Builder.Default
    Boolean encerrada = false;

    public boolean isEncerrada() {
        return Boolean.TRUE.equals(this.encerrada);
    }

    public void encerrar() {
        this.encerrada = true;
        setAtualizado(java.time.LocalDateTime.now());
    }

    public void reabrir() {
        this.encerrada = false;
        setAtualizado(java.time.LocalDateTime.now());
    }

}
