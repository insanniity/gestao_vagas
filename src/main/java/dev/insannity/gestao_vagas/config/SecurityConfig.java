package dev.insannity.gestao_vagas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity 
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/css/**", "/js/**", "/img/**").permitAll()
                        // Banco de Talentos e Atribuição de Candidatos: Somente ADMIN
                        .requestMatchers("/candidatos", "/candidatos/**").hasRole("ADMIN")
                        .requestMatchers("/candidaturas/atribuir").hasRole("ADMIN")
                        // Gestão de Vagas (Criar, Editar, Apagar): ADMIN e RECRUTADOR
                        .requestMatchers("/vagas/nova", "/vagas/*/editar", "/vagas/*/apagar").hasAnyRole("ADMIN", "RECRUTADOR")
                        // Alteração de Status de Candidatos na Vaga: ADMIN e RECRUTADOR
                        .requestMatchers("/candidaturas/*/status").hasAnyRole("ADMIN", "RECRUTADOR")
                        // Candidatar-se a vagas, Minhas Candidaturas e Cancelar: Apenas CANDIDATO
                        .requestMatchers("/candidaturas", "/candidaturas/vaga/**", "/candidaturas/*/cancelar").hasRole("CANDIDATO")
                        // Visualização de vagas e perfil pessoal: Qualquer usuário autenticado
                        .requestMatchers("/vagas", "/vagas/**", "/perfil", "/").authenticated()
                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/vagas")
                        .permitAll())
                .sessionManagement(session -> session
                        .maximumSessions(1)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .permitAll());
                        
        return http.build();
    }
}
