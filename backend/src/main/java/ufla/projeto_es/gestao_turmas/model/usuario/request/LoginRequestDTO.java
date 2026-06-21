package ufla.projeto_es.gestao_turmas.model.usuario.request;

import jakarta.validation.constraints.NotNull;

public record LoginRequestDTO(
        @NotNull()
        String email,
        @NotNull()
        String senha
) {}
