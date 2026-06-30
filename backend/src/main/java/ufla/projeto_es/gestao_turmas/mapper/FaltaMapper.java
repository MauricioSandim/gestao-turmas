package ufla.projeto_es.gestao_turmas.mapper;

import org.mapstruct.Mapper;
import ufla.projeto_es.gestao_turmas.model.falta.Falta;
import ufla.projeto_es.gestao_turmas.model.falta.request.CreateFaltaRequestDTO;
import ufla.projeto_es.gestao_turmas.model.falta.response.FaltaResponseDTO;


@Mapper(componentModel = "spring")
public interface FaltaMapper {
    Falta createFaltaRequestDTOtoEntity(CreateFaltaRequestDTO requestDTO);

    Falta updateFaltaRequestDTOtoEntity(CreateFaltaRequestDTO requestDTO);

    FaltaResponseDTO toResponseDTO(Falta entity);
}
