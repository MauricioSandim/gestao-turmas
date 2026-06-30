package ufla.projeto_es.gestao_turmas.model.horarioAula.request;

import jakarta.validation.constraints.NotNull;
import ufla.projeto_es.gestao_turmas.model.type.DiasEnum;

import java.time.LocalTime;

public record UpdateHorarioAulaRequestDTO(@NotNull DiasEnum diaSemana, @NotNull LocalTime hora) {
}
