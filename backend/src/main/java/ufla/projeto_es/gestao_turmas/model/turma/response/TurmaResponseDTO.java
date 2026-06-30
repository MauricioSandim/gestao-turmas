package ufla.projeto_es.gestao_turmas.model.turma.response;

import ufla.projeto_es.gestao_turmas.model.atividadeAvalitiva.response.AtividadeAvaliativaResponseDTO;
import ufla.projeto_es.gestao_turmas.model.horarioAula.response.HorarioAulaResponseDTO;

import java.util.List;

public record TurmaResponseDTO(Long id, String nome, List<HorarioAulaResponseDTO> horariosAula,
                               List<AtividadeAvaliativaResponseDTO> atividadesAvaliativas) {

}
