package dev.insannity.gestao_vagas.exceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ControllerExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public Object handleNotFound(RecursoNaoEncontradoException e, HttpServletRequest request) {
        log.warn("Recurso não encontrado ao acessar '{}': {}", request.getRequestURI(), e.getMessage());

        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError error = new StandardError(
            status.value(),
            status.getReasonPhrase(),
            e.getMessage(),
            request.getRequestURI()
        );
        ModelAndView modelAndView = new ModelAndView("errors/error");
        modelAndView.setStatus(status);
        modelAndView.addObject("error", error);
        return modelAndView;
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public Object handleAccessDenied(org.springframework.security.access.AccessDeniedException e, HttpServletRequest request) {
        log.warn("Acesso negado para '{}': {}", request.getRequestURI(), e.getMessage());

        HttpStatus status = HttpStatus.FORBIDDEN;
        StandardError error = new StandardError(
            status.value(),
            status.getReasonPhrase(),
            "Acesso negado para a operação solicitada.",
            request.getRequestURI()
        );
        ModelAndView modelAndView = new ModelAndView("errors/error");
        modelAndView.setStatus(status);
        modelAndView.addObject("error", error);
        return modelAndView;
    }

    @Order(Ordered.LOWEST_PRECEDENCE)
    @ExceptionHandler(Exception.class)
    public Object handleError(Exception e, HttpServletRequest request) {
        log.error("Erro inesperado capturado ao processar '{}': ", request.getRequestURI(), e);

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        StandardError error = new StandardError(
            status.value(),
            status.getReasonPhrase(),
            e.getMessage(),
            request.getRequestURI()
        );
        ModelAndView modelAndView = new ModelAndView("errors/error");
        modelAndView.setStatus(status);
        modelAndView.addObject("error", error);
        return modelAndView;
    }

}
