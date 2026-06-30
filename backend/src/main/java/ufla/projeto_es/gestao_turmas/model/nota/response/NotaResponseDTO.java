package ufla.projeto_es.gestao_turmas.model.nota.response;

import ufla.projeto_es.gestao_turmas.model.atividadeAvalitiva.response.AtividadeAvaliativaResponseDTO;
import ufla.projeto_es.gestao_turmas.model.matricula.response.MatriculaResponseDTO;

import java.math.BigDecimal;

public record NotaResponseDTO(Long id, MatriculaResponseDTO matricula, AtividadeAvaliativaResponseDTO atividadeAvaliativa,
                              BigDecimal valor) {
}
