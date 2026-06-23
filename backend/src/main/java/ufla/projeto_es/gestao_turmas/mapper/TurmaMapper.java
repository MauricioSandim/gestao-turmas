package ufla.projeto_es.gestao_turmas.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ufla.projeto_es.gestao_turmas.model.turma.Turma;
import ufla.projeto_es.gestao_turmas.model.turma.request.CreateTurmaRequestDTO;
import ufla.projeto_es.gestao_turmas.model.turma.request.UpdateTurmaRequestDTO;
import ufla.projeto_es.gestao_turmas.model.turma.response.TurmaResponseDTO;
import ufla.projeto_es.gestao_turmas.model.turma.response.TurmaSummaryResponseDTO;

@Mapper(componentModel = "spring")
public interface TurmaMapper {

    //requests
    Turma createTurmaRequestDTOtoEntity(CreateTurmaRequestDTO requestDTO);
    Turma updateTurmaRequestDTOtoEntity(UpdateTurmaRequestDTO requestDTO);

    // responses
    TurmaResponseDTO toResponse(Turma turma);
    TurmaSummaryResponseDTO toSummaryResponse(Turma turma);

    // copy
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lastModifiedAt", ignore = true)
    @Mapping(target = "version", ignore = true)

    @Mapping(target = "usuario",  ignore = true)
    void copyProperties(Turma newEntity, @MappingTarget Turma entity);
}
