package ufla.projeto_es.gestao_turmas.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ufla.projeto_es.gestao_turmas.exception.NotFoundException;
import ufla.projeto_es.gestao_turmas.model.atividadeAvalitiva.AtividadeAvaliativa;
import ufla.projeto_es.gestao_turmas.model.matricula.Matricula;
import ufla.projeto_es.gestao_turmas.model.nota.Nota;
import ufla.projeto_es.gestao_turmas.model.turma.Turma;
import ufla.projeto_es.gestao_turmas.model.usuario.Usuario;
import ufla.projeto_es.gestao_turmas.repository.TurmaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotaService {
    private final TurmaRepository turmaRepository;

    @Transactional(readOnly = true)
    public List<Nota> consultar(Long turmaId, Long matriculaId, Usuario usuario){
        Turma turma = turmaRepository.findByIdAndUsuario_Id(turmaId, usuario.getId()).orElseThrow(() -> new NotFoundException("Turma não encontrada para id: " + turmaId));

        Matricula matricula = turma.getMatriculas().stream().filter((e) -> Objects.equals(e.getId(), matriculaId)).findFirst().orElseThrow(() -> new NotFoundException("Matrícula não encontrada para id: " + matriculaId));

        return matricula.getNotas();
    }

    @Transactional
    public void criar(Long turmaId, Long matriculaId, Long atividadeAvaliativaId, BigDecimal valor, Usuario usuario) {
        Turma turma = turmaRepository.findByIdAndUsuario_Id(turmaId, usuario.getId()).orElseThrow(() -> new NotFoundException("Turma não encontrada para id: " + turmaId));

        Matricula matricula = turma.getMatriculas().stream().filter((e) -> Objects.equals(e.getId(), matriculaId)).findFirst().orElseThrow(() -> new NotFoundException("Matrícula não encontrada para id: " + matriculaId));

        AtividadeAvaliativa atividadeAvaliativa = turma.getAtividadesAvaliativas().stream().filter((e) -> Objects.equals(e.getId(), atividadeAvaliativaId)).findFirst().orElseThrow(() -> new NotFoundException("Ativida avaliativa não encontrada para id: " + atividadeAvaliativaId));

        matricula.addNota(atividadeAvaliativa, valor);
    }

    @Transactional
    public void update(Long turmaId, Long matriculaId, Long id, BigDecimal valor, Usuario usuario) {
        Turma turma = turmaRepository.findByIdAndUsuario_Id(turmaId, usuario.getId()).orElseThrow(() -> new NotFoundException("Turma não encontrada para id: " + turmaId));
        Matricula matricula = turma.getMatriculas().stream().filter((e) -> Objects.equals(e.getId(), matriculaId)).findFirst().orElseThrow(() -> new NotFoundException("Matrícula não encontrada para id: " + matriculaId));

        matricula.updateNota(id, valor);
    }

    @Transactional
    public void delete(Long turmaId, Long matriculaId, Long id, Usuario usuario) {
        Turma turma = turmaRepository.findByIdAndUsuario_Id(turmaId, usuario.getId()).orElseThrow(() -> new NotFoundException("Turma não encontrada para id: " + turmaId));
        Matricula matricula = turma.getMatriculas().stream().filter((e) -> Objects.equals(e.getId(), matriculaId)).findFirst().orElseThrow(() -> new NotFoundException("Matrícula não encontrada para id: " + matriculaId));

        matricula.removeNota(id);
    }
}
