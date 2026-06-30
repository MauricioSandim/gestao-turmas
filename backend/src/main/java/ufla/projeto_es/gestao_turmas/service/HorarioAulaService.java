package ufla.projeto_es.gestao_turmas.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ufla.projeto_es.gestao_turmas.exception.NotFoundException;
import ufla.projeto_es.gestao_turmas.mapper.HorarioAulaMapper;
import ufla.projeto_es.gestao_turmas.model.horarioAula.HorarioAula;
import ufla.projeto_es.gestao_turmas.model.horarioAula.request.CreateHorarioAulaRequestDTO;
import ufla.projeto_es.gestao_turmas.model.horarioAula.request.UpdateHorarioAulaRequestDTO;
import ufla.projeto_es.gestao_turmas.model.turma.Turma;
import ufla.projeto_es.gestao_turmas.model.usuario.Usuario;
import ufla.projeto_es.gestao_turmas.repository.TurmaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HorarioAulaService {
    private final TurmaRepository turmaRepository;
    private final HorarioAulaMapper horarioAulaMapper;

    @Transactional(readOnly = true)
    public List<HorarioAula> consultarHorarios(Long turmaId, Usuario usuario){
        Turma turma = turmaRepository.findByIdAndUsuario_Id(turmaId, usuario.getId()).orElseThrow(() -> new NotFoundException("Turma não encontrada para id: " + turmaId));

        return turma.getHorariosAula();
    }

    @Transactional
    public void criar(Long turmaId, CreateHorarioAulaRequestDTO requestDTO, Usuario usuario) {
        log.info("Criando horário de aula para a turma {}", turmaId);

        Turma turma = turmaRepository.findByIdAndUsuario_Id(turmaId, usuario.getId()).orElseThrow(() -> new NotFoundException("Turma não encontrada para id: " + turmaId));

        HorarioAula horarioAulaCriado = horarioAulaMapper.createHorarioAulaRequestDTOtoEntity(requestDTO);

        turma.addHorarioAula(horarioAulaCriado);

        turmaRepository.save(turma);
    }

    @Transactional
    public void delete(Long turmaId, Long horarioAulaId, Usuario usuario) {
        log.info("Deletando horário de aula para a turma {}", turmaId);

        Turma turma = turmaRepository.findByIdAndUsuario_Id(turmaId, usuario.getId()).orElseThrow(() -> new NotFoundException("Turma não encontrada para id: " + turmaId));

        turma.removeHorarioAula(horarioAulaId);

    }

    @Transactional
    public void update(Long turmaId, Long horarioAulaId, UpdateHorarioAulaRequestDTO requestDTO, Usuario usuario) {
        log.info("Atualizando horário de aula para a turma {}", turmaId);

        Turma turma = turmaRepository.findByIdAndUsuario_Id(turmaId, usuario.getId()).orElseThrow(() -> new NotFoundException("Turma não encontrada para id: " + turmaId));

        turma.updateHorarioAula(horarioAulaId, horarioAulaMapper.updateHorarioAulaRequestDTOtoEntity(requestDTO));
    }
}
