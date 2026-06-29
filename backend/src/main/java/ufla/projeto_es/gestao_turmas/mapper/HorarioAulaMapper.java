package ufla.projeto_es.gestao_turmas.mapper;

import org.mapstruct.Mapper;
import ufla.projeto_es.gestao_turmas.model.horarioAula.HorarioAula;
import ufla.projeto_es.gestao_turmas.model.horarioAula.request.CreateHorarioAulaRequestDTO;
import ufla.projeto_es.gestao_turmas.model.horarioAula.request.UpdateHorarioAulaRequestDTO;
import ufla.projeto_es.gestao_turmas.model.horarioAula.response.HorarioAulaResponseDTO;
import ufla.projeto_es.gestao_turmas.model.horarioAula.response.HorarioAulaSummaryResponseDTO;
import ufla.projeto_es.gestao_turmas.model.turma.Turma;
import ufla.projeto_es.gestao_turmas.model.turma.request.CreateTurmaRequestDTO;
import ufla.projeto_es.gestao_turmas.model.turma.request.UpdateTurmaRequestDTO;
import ufla.projeto_es.gestao_turmas.model.turma.response.TurmaResponseDTO;
import ufla.projeto_es.gestao_turmas.model.turma.response.TurmaSummaryResponseDTO;

@Mapper(componentModel = "spring")
public interface HorarioAulaMapper {
    HorarioAula createHorarioAulaRequestDTOtoEntity(CreateHorarioAulaRequestDTO requestDTO);

    HorarioAula updateHorarioAulaRequestDTOtoEntity(UpdateHorarioAulaRequestDTO requestDTO);

    HorarioAulaResponseDTO toResponse(HorarioAula horarioAula);

    HorarioAulaSummaryResponseDTO toSummaryResponse(HorarioAula horarioAula);
}
