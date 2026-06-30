package ufla.projeto_es.gestao_turmas.model.nota.response;

import ufla.projeto_es.gestao_turmas.model.atividadeAvalitiva.response.AtividadeAvaliativaResponseDTO;

import java.math.BigDecimal;

public record NotaReponseAlunoDTO(Long id, AtividadeAvaliativaResponseDTO atividadeAvaliativa, BigDecimal valor) {
}
