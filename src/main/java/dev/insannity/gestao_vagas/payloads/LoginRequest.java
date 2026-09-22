package dev.insannity.gestao_vagas.payloads;

public record LoginRequest(String username, String password) {
    public LoginRequest() {
        this(null, null);
    }
}
