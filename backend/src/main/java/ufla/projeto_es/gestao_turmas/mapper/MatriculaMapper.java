package ufla.projeto_es.gestao_turmas.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ufla.projeto_es.gestao_turmas.model.matricula.Matricula;
import ufla.projeto_es.gestao_turmas.model.matricula.request.CreateMatriculaRequestDTO;
import ufla.projeto_es.gestao_turmas.model.matricula.request.UpdateMatriculaRequestDTO;
import ufla.projeto_es.gestao_turmas.model.matricula.response.MatriculaCompleteResponseDTO;
import ufla.projeto_es.gestao_turmas.model.matricula.response.MatriculaResponseDTO;
import ufla.projeto_es.gestao_turmas.model.turma.Turma;
import ufla.projeto_es.gestao_turmas.model.turma.request.CreateTurmaRequestDTO;
import ufla.projeto_es.gestao_turmas.model.turma.request.UpdateTurmaRequestDTO;
import ufla.projeto_es.gestao_turmas.model.turma.response.TurmaResponseDTO;
import ufla.projeto_es.gestao_turmas.model.turma.response.TurmaSummaryResponseDTO;

@Mapper(componentModel = "spring")
public interface MatriculaMapper {

    Matricula createMatriculaRequestDTOtoEntity(CreateMatriculaRequestDTO requestDTO);

    MatriculaResponseDTO toResponse(Matricula turma);

    MatriculaCompleteResponseDTO toCompleteResponse(Matricula turma);

}
