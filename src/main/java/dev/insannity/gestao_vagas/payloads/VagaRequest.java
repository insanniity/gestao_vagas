package dev.insannity.gestao_vagas.payloads;

import dev.insannity.gestao_vagas.docs.Vaga;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VagaRequest {

    @NotBlank(message = "O nome da empresa é obrigatório.")
    @Size(min = 2, max = 100, message = "O nome da empresa deve ter entre 2 e 100 caracteres.")
    String empresa;

    @NotBlank(message = "O nível de experiência é obrigatório.")
    String level;

    @NotBlank(message = "A descrição da oportunidade é obrigatória.")
    @Size(min = 10, max = 2000, message = "A descrição deve conter no mínimo 10 caracteres.")
    String descricao;

    @NotBlank(message = "Informe os principais benefícios oferecidos.")
    @Size(max = 1000, message = "Os benefícios não podem exceder 1000 caracteres.")
    String beneficios;

    public Vaga toModel() {
        return Vaga.builder()
                .empresa(this.empresa != null ? this.empresa.trim() : null)
                .level(this.level != null ? this.level.trim().toUpperCase() : null)
                .descricao(this.descricao != null ? this.descricao.trim() : null)
                .beneficios(this.beneficios != null ? this.beneficios.trim() : null)
                .build();
    }

    public static VagaRequest fromModel(Vaga vaga) {
        return VagaRequest.builder()
                .empresa(vaga.getEmpresa())
                .level(vaga.getLevel())
                .descricao(vaga.getDescricao())
                .beneficios(vaga.getBeneficios())
                .build();
    }
}
