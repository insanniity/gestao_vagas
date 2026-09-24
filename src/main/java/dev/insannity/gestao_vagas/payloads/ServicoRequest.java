package dev.insannity.gestao_vagas.payloads;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import dev.insannity.gestao_vagas.docs.Servico;
import dev.insannity.gestao_vagas.docs.Usuario;
import dev.insannity.gestao_vagas.enums.CategoriaServico;
import dev.insannity.gestao_vagas.enums.TipoPrecoServico;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class ServicoRequest {

    @NotBlank(message = "O título do serviço é obrigatório.")
    @Size(min = 5, max = 120, message = "O título deve ter entre 5 e 120 caracteres.")
    String titulo;

    @NotBlank(message = "A descrição detalhada do serviço é obrigatória.")
    @Size(min = 10, max = 3000, message = "A descrição deve conter no mínimo 10 caracteres.")
    String descricao;

    @NotNull(message = "Selecione uma categoria para o serviço.")
    CategoriaServico categoria;

    @NotNull(message = "Selecione o modelo de precificação.")
    TipoPrecoServico tipoPreco;

    BigDecimal preco;

    @Size(max = 60, message = "O prazo estimado deve ter até 60 caracteres.")
    String prazoEstimado;

    String tecnologiasTexto;

    Boolean ativo;

    public List<String> obterListaTecnologias() {
        if (this.tecnologiasTexto == null || this.tecnologiasTexto.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.stream(this.tecnologiasTexto.split("[,;\\n]+"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .distinct()
                .collect(Collectors.toList());
    }

    public Servico toModel(Usuario usuario) {
        return Servico.builder()
                .titulo(this.titulo != null ? this.titulo.trim() : null)
                .descricao(this.descricao != null ? this.descricao.trim() : null)
                .categoria(this.categoria)
                .tipoPreco(this.tipoPreco)
                .preco(this.tipoPreco == TipoPrecoServico.A_COMBINAR ? null : this.preco)
                .prazoEstimado(this.prazoEstimado != null ? this.prazoEstimado.trim() : "A combinar")
                .tecnologias(obterListaTecnologias())
                .ativo(this.ativo != null ? this.ativo : true)
                .usuarioId(usuario.getId())
                .usuarioNome(usuario.getNome() != null ? usuario.getNome() : usuario.getEmail())
                .usuarioEmail(usuario.getEmail())
                .usuarioTelefone(usuario.getTelefone())
                .usuarioCargo(usuario.getCargo())
                .usuarioLinkedin(usuario.getLinkedin())
                .usuarioGithub(usuario.getGithub())
                .build();
    }

    public static ServicoRequest fromModel(Servico servico) {
        String tecnologias = servico.getTecnologias() != null ? String.join(", ", servico.getTecnologias()) : "";
        return ServicoRequest.builder()
                .titulo(servico.getTitulo())
                .descricao(servico.getDescricao())
                .categoria(servico.getCategoria())
                .tipoPreco(servico.getTipoPreco())
                .preco(servico.getPreco())
                .prazoEstimado(servico.getPrazoEstimado())
                .tecnologiasTexto(tecnologias)
                .ativo(servico.isAtivo())
                .build();
    }

}
