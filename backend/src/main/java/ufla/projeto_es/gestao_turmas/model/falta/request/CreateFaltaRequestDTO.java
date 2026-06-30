package ufla.projeto_es.gestao_turmas.model.falta.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateFaltaRequestDTO(@NotNull LocalDate data, @NotNull Long horarioAulaId) {
}
