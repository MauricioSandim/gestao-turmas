package ufla.projeto_es.gestao_turmas.model.falta.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UpdateFaltaRequestDTO(@NotNull LocalDate data) {
}
