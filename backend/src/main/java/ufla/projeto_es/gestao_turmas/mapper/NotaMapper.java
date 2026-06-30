package ufla.projeto_es.gestao_turmas.mapper;

import org.mapstruct.Mapper;
import ufla.projeto_es.gestao_turmas.model.nota.Nota;
import ufla.projeto_es.gestao_turmas.model.nota.response.NotaResponseDTO;
import ufla.projeto_es.gestao_turmas.model.turma.Turma;
import ufla.projeto_es.gestao_turmas.model.turma.response.TurmaResponseDTO;

@Mapper(componentModel = "spring")
public interface NotaMapper {
    NotaResponseDTO toResponse(Nota nota);

}

