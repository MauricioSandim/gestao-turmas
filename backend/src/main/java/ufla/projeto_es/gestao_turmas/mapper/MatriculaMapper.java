package ufla.projeto_es.gestao_turmas.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ufla.projeto_es.gestao_turmas.model.matricula.Matricula;
import ufla.projeto_es.gestao_turmas.model.matricula.request.CreateMatriculaRequestDTO;
import ufla.projeto_es.gestao_turmas.model.matricula.request.UpdateMatriculaRequestDTO;
import ufla.projeto_es.gestao_turmas.model.matricula.response.MatriculaResponseDTO;
import ufla.projeto_es.gestao_turmas.model.turma.Turma;
import ufla.projeto_es.gestao_turmas.model.turma.request.CreateTurmaRequestDTO;
import ufla.projeto_es.gestao_turmas.model.turma.request.UpdateTurmaRequestDTO;
import ufla.projeto_es.gestao_turmas.model.turma.response.TurmaResponseDTO;
import ufla.projeto_es.gestao_turmas.model.turma.response.TurmaSummaryResponseDTO;

@Mapper(componentModel = "spring")
public interface MatriculaMapper {

    //requests
    Matricula createMatriculaRequestDTOtoEntity(CreateMatriculaRequestDTO requestDTO);
//    Matricula updateMatriculaRequestDTOtoEntity(UpdateMatriculaRequestDTO requestDTO);

    // responses
    MatriculaResponseDTO toResponse(Matricula turma);

    // copy
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "lastModifiedAt", ignore = true)
//    @Mapping(target = "version", ignore = true)
//
//    @Mapping(target = "usuario",  ignore = true)
//    void copyProperties(Turma newEntity, @MappingTarget Turma entity);
}
