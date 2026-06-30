package ufla.projeto_es.gestao_turmas.model.matricula.response;

import jakarta.validation.constraints.NotNull;
import ufla.projeto_es.gestao_turmas.model.turma.response.TurmaSummaryResponseDTO;
import ufla.projeto_es.gestao_turmas.model.usuario.response.UsuarioResponseDTO;

public record MatriculaResponseDTO(Long id, UsuarioResponseDTO aluno, TurmaSummaryResponseDTO turma) {
}
