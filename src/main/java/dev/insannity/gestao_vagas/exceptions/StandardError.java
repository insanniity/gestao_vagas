package dev.insannity.gestao_vagas.exceptions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class StandardError {

    @Setter(AccessLevel.PRIVATE)
    private String time = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    private Integer status;
    private String error;
    private String message;
    private String path;

    public StandardError(Integer status, String error, String message, String path) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

}
