package ufla.projeto_es.gestao_turmas.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ufla.projeto_es.gestao_turmas.exception.NotFoundException;
import ufla.projeto_es.gestao_turmas.mapper.FaltaMapper;
import ufla.projeto_es.gestao_turmas.model.falta.Falta;
import ufla.projeto_es.gestao_turmas.model.falta.request.CreateFaltaRequestDTO;
import ufla.projeto_es.gestao_turmas.model.falta.request.UpdateFaltaRequestDTO;
import ufla.projeto_es.gestao_turmas.model.horarioAula.HorarioAula;
import ufla.projeto_es.gestao_turmas.model.matricula.Matricula;
import ufla.projeto_es.gestao_turmas.model.turma.Turma;
import ufla.projeto_es.gestao_turmas.model.usuario.Usuario;
import ufla.projeto_es.gestao_turmas.repository.TurmaRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class FaltaService {

    private final TurmaRepository turmaRepository;
    private final FaltaMapper faltaMapper;

    @Transactional(readOnly = true)
    public List<Falta> consultar(Long turmaId, Long matriculaId, Usuario usuario) {
        Turma turma = turmaRepository.findByIdAndUsuario_Id(turmaId, usuario.getId()).orElseThrow(() -> new NotFoundException("Turma não encontrada para id: " + turmaId));

        Matricula matricula = turma.getMatriculas().stream().filter((e) -> Objects.equals(e.getId(), matriculaId)).findFirst().orElseThrow(() -> new NotFoundException("Matrícula não encontrada para id: " + matriculaId));

        return matricula.getFaltas();
    }

    @Transactional
    public void criar(Long turmaId, Long matriculaId, CreateFaltaRequestDTO requestDTO, Usuario usuario) {
        Turma turma = turmaRepository.findByIdAndUsuario_Id(turmaId, usuario.getId()).orElseThrow(() -> new NotFoundException("Turma não encontrada para id: " + turmaId));

        Matricula matricula = turma.getMatriculas().stream().filter((e) -> Objects.equals(e.getId(), matriculaId)).findFirst().orElseThrow(() -> new NotFoundException("Matrícula não encontrada para id: " + matriculaId));

        Falta falta = faltaMapper.createFaltaRequestDTOtoEntity(requestDTO);

        HorarioAula horarioAula = turma.getHorariosAula().stream().filter((e) -> Objects.equals(e.getId(), requestDTO.horarioAulaId())).findFirst().orElseThrow(() -> new NotFoundException("Horário de aula não encontrado para id: " + requestDTO.horarioAulaId()));

        falta.setHorarioAula(horarioAula);

        falta = matricula.addFalta(falta);

        turmaRepository.save(turma);
    }

    @Transactional
    public void update(Long turmaId, Long matriculaId, Long faltaId, UpdateFaltaRequestDTO requestDTO, Usuario usuario) {
        Turma turma = turmaRepository.findByIdAndUsuario_Id(turmaId, usuario.getId()).orElseThrow(() -> new NotFoundException("Turma não encontrada para id: " + turmaId));

        Matricula matricula = turma.getMatriculas().stream().filter((e) -> Objects.equals(e.getId(), matriculaId)).findFirst().orElseThrow(() -> new NotFoundException("Matrícula não encontrada para id: " + matriculaId));

        Falta falta = matricula.updateFalta(faltaId, requestDTO);

        turmaRepository.save(turma);
    }

    @Transactional
    public void deletar(Long turmaId, Long matriculaId, Long faltaId, Usuario usuario) {
        Turma turma = turmaRepository.findByIdAndUsuario_Id(turmaId, usuario.getId()).orElseThrow(() -> new NotFoundException("Turma não encontrada para id: " + turmaId));

        Matricula matricula = turma.getMatriculas().stream().filter((e) -> Objects.equals(e.getId(), matriculaId)).findFirst().orElseThrow(() -> new NotFoundException("Matrícula não encontrada para id: " + matriculaId));

        matricula.removeFalta(faltaId);
    }
}
