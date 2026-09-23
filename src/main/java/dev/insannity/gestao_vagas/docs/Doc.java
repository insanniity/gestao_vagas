package dev.insannity.gestao_vagas.docs;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class Doc {

    @Id
    @EqualsAndHashCode.Include
    String id;

    @CreatedDate  
    LocalDateTime criado = LocalDateTime.now();
    @LastModifiedDate
    LocalDateTime atualizado = LocalDateTime.now();
    @Setter(AccessLevel.PRIVATE)
    LocalDateTime deletado;

    public void deletar() {
        this.deletado = LocalDateTime.now();
    }

}
