package ufla.projeto_es.gestao_turmas.model.falta.response;

import ufla.projeto_es.gestao_turmas.model.horarioAula.response.HorarioAulaResponseDTO;
import ufla.projeto_es.gestao_turmas.model.matricula.response.MatriculaResponseDTO;

import java.time.LocalDate;

public record FaltaAlunoResponseDTO(Long id, LocalDate data, HorarioAulaResponseDTO horarioAula) {
}
