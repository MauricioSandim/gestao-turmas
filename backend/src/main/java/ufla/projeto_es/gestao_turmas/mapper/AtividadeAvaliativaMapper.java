package ufla.projeto_es.gestao_turmas.mapper;

import org.mapstruct.Mapper;
import ufla.projeto_es.gestao_turmas.model.atividadeAvalitiva.AtividadeAvaliativa;
import ufla.projeto_es.gestao_turmas.model.atividadeAvalitiva.request.CreateAtividadeAvaliativaRequestDTO;
import ufla.projeto_es.gestao_turmas.model.atividadeAvalitiva.request.UpdateAtividadeAvaliativaRequestDTO;
import ufla.projeto_es.gestao_turmas.model.atividadeAvalitiva.response.AtividadeAvaliativaResponseDTO;
import ufla.projeto_es.gestao_turmas.model.falta.Falta;
import ufla.projeto_es.gestao_turmas.model.falta.request.CreateFaltaRequestDTO;
import ufla.projeto_es.gestao_turmas.model.falta.response.FaltaResponseDTO;

@Mapper(componentModel = "spring")
public interface AtividadeAvaliativaMapper {


    AtividadeAvaliativa createAtividadeAvaliativaRequestDTOtoEntity(CreateAtividadeAvaliativaRequestDTO requestDTO);

    AtividadeAvaliativa updateAtividadeAvaliativaRequestDTOtoEntity(UpdateAtividadeAvaliativaRequestDTO requestDTO);

    AtividadeAvaliativaResponseDTO toResponseDTO(AtividadeAvaliativa entity);
}
