package ufla.projeto_es.gestao_turmas.model.horarioAula.response;

import ufla.projeto_es.gestao_turmas.model.type.DiasEnum;

import java.time.LocalTime;

public record HorarioAulaResponseDTO(Long id, DiasEnum dia, LocalTime hora) {
}
