package ufla.projeto_es.gestao_turmas.model.horarioAula.request;

import jakarta.validation.constraints.NotNull;
import ufla.projeto_es.gestao_turmas.model.horarioAula.HorarioAula;
import ufla.projeto_es.gestao_turmas.model.type.DiasEnum;

import java.time.LocalTime;

public record CreateHorarioAulaRequestDTO (@NotNull DiasEnum diaSemana, @NotNull LocalTime hora) {
}
