package dev.insannity.gestao_vagas.payloads;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import dev.insannity.gestao_vagas.docs.Usuario;
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
public class PerfilRequest {

    @NotBlank(message = "O nome completo é obrigatório.")
    @Size(min = 2, max = 100, message = "O nome deve conter entre 2 e 100 caracteres.")
    String nome;

    @Size(max = 30, message = "O telefone não pode exceder 30 caracteres.")
    String telefone;

    @Size(max = 100, message = "A localização não pode exceder 100 caracteres.")
    String localizacao;

    @Size(max = 100, message = "O cargo não pode exceder 100 caracteres.")
    String cargo;

    @Size(max = 2000, message = "O resumo profissional não pode exceder 2000 caracteres.")
    String resumo;

    Boolean disponivelParaContratacao;

    String competencias;

    @Size(max = 200, message = "O link do LinkedIn não pode exceder 200 caracteres.")
    String linkedin;

    @Size(max = 200, message = "O link do GitHub não pode exceder 200 caracteres.")
    String github;

    public static PerfilRequest fromModel(Usuario usuario) {
        String comps = usuario.getCompetencias() != null 
                ? String.join(", ", usuario.getCompetencias()) 
                : "";

        return PerfilRequest.builder()
                .nome(usuario.getNome())
                .telefone(usuario.getTelefone())
                .localizacao(usuario.getLocalizacao())
                .cargo(usuario.getCargo())
                .resumo(usuario.getResumo())
                .disponivelParaContratacao(usuario.getDisponivelParaContratacao() != null ? usuario.getDisponivelParaContratacao() : true)
                .competencias(comps)
                .linkedin(usuario.getLinkedin())
                .github(usuario.getGithub())
                .build();
    }

    public List<String> obterListaCompetencias() {
        if (this.competencias == null || this.competencias.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.stream(this.competencias.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .distinct()
                .collect(Collectors.toList());
    }

}
