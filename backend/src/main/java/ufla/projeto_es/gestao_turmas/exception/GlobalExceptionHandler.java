package ufla.projeto_es.gestao_turmas.exception;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> details = ex.getBindingResult().getFieldErrors().stream().map(error -> error.getField() + ": " + error.getDefaultMessage()).collect(Collectors.toList());

        ProblemDetail problemDetail = buildProblemDetail("Erro de validação", "Um ou mais campos são inválidos", request, HttpStatus.BAD_REQUEST);

        problemDetail.setProperty("errors", details);


        return ResponseEntity.badRequest().body(problemDetail);
    }

    private ProblemDetail buildProblemDetail(String title, String detail, HttpServletRequest request, HttpStatus status) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
        problemDetail.setTitle(title);
        problemDetail.setInstance(URI.create(request.getRequestURI()));

        return problemDetail;
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ProblemDetail> handleNotFoundException(NotFoundException ex, HttpServletRequest request) {
        ProblemDetail problemDetail = buildProblemDetail("Not Found", ex.getMessage(), request, HttpStatus.NOT_FOUND);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ProblemDetail> handleBusinessException(BusinessException ex, HttpServletRequest request) {
        ProblemDetail problemDetail = buildProblemDetail("Erro de negócio", ex.getMessage(), request, HttpStatus.UNPROCESSABLE_CONTENT);

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(problemDetail);
    }

    @ExceptionHandler(ValidationConflictException.class)
    public ResponseEntity<ProblemDetail> handleValidationConflictException(ValidationConflictException ex, HttpServletRequest request) {
        ProblemDetail problemDetail = buildProblemDetail("Conflict", ex.getMessage(), request, HttpStatus.CONFLICT);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail);
    }

//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<ProblemDetail> handleBadCredentialsException(BadCredentialsException ex, HttpServletRequest request) {
//        ProblemDetail problemDetail = buildProblemDetail("Unauthorized", "Email ou senha inválidos.", request, HttpStatus.UNAUTHORIZED);
//
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problemDetail);
//    }
//
//    @ExceptionHandler(DisabledException.class)
//    public ResponseEntity<ProblemDetail> handleDisabledException(DisabledException ex, HttpServletRequest request) {
//        ProblemDetail problemDetail = buildProblemDetail("Forbidden", "Usuário desabilitado.", request, HttpStatus.FORBIDDEN);
//
//        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(problemDetail);
//    }
//
//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<ProblemDetail> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
//        ProblemDetail problemDetail = buildProblemDetail("Forbidden", "Acesso negado. Você não possui permissão para este recurso.", request, HttpStatus.FORBIDDEN);
//
//        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(problemDetail);
//    }
//
//    @ExceptionHandler(AuthenticationException.class)
//    public ResponseEntity<ProblemDetail> handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) {
//        ProblemDetail problemDetail = buildProblemDetail("Unauthorized", "Falha na autenticação.", request, HttpStatus.UNAUTHORIZED);
//
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problemDetail);
//    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGenericException(Exception ex, HttpServletRequest request) {
        ProblemDetail problemDetail = buildProblemDetail("Internal Server Error", "Ocorreu um erro inesperado no servidor.", request, HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetail);
    }
}
