package ufla.projeto_es.gestao_turmas.model.horarioAula.response;

import ufla.projeto_es.gestao_turmas.model.type.DiasEnum;

import java.time.LocalTime;

public record HorarioAulaSummaryResponseDTO (DiasEnum dia, LocalTime hora){
}
