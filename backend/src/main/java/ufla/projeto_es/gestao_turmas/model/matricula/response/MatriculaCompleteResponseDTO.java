package ufla.projeto_es.gestao_turmas.model.matricula.response;

import ufla.projeto_es.gestao_turmas.model.falta.Falta;
import ufla.projeto_es.gestao_turmas.model.falta.response.FaltaAlunoResponseDTO;
import ufla.projeto_es.gestao_turmas.model.falta.response.FaltaResponseDTO;
import ufla.projeto_es.gestao_turmas.model.nota.Nota;
import ufla.projeto_es.gestao_turmas.model.nota.response.NotaReponseAlunoDTO;
import ufla.projeto_es.gestao_turmas.model.nota.response.NotaResponseDTO;
import ufla.projeto_es.gestao_turmas.model.turma.response.TurmaResponseDTO;

import java.util.List;

public record MatriculaCompleteResponseDTO(Long id, TurmaResponseDTO turma, List<FaltaAlunoResponseDTO> faltas, List<NotaReponseAlunoDTO> notas) {
}
