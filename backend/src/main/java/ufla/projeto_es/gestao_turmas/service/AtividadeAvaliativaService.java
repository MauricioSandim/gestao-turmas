package ufla.projeto_es.gestao_turmas.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ufla.projeto_es.gestao_turmas.exception.NotFoundException;
import ufla.projeto_es.gestao_turmas.mapper.AtividadeAvaliativaMapper;
import ufla.projeto_es.gestao_turmas.model.atividadeAvalitiva.AtividadeAvaliativa;
import ufla.projeto_es.gestao_turmas.model.atividadeAvalitiva.request.CreateAtividadeAvaliativaRequestDTO;
import ufla.projeto_es.gestao_turmas.model.atividadeAvalitiva.request.UpdateAtividadeAvaliativaRequestDTO;
import ufla.projeto_es.gestao_turmas.model.turma.Turma;
import ufla.projeto_es.gestao_turmas.model.usuario.Usuario;
import ufla.projeto_es.gestao_turmas.repository.TurmaRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AtividadeAvaliativaService {
    private final TurmaRepository turmaRepository;
    private final AtividadeAvaliativaMapper atividadeAvaliativaMapper;

    @Transactional(readOnly = true)
    public List<AtividadeAvaliativa> consultar(Long turmaId, Usuario usuario) {
        Turma turma = turmaRepository.findByIdAndUsuario_Id(turmaId, usuario.getId()).orElseThrow(() -> new NotFoundException("Turma não encontrada para id: " + turmaId));

        return turma.getAtividadesAvaliativas();
    }

    @Transactional
    public void criar(Long turmaId, CreateAtividadeAvaliativaRequestDTO requestDTO, Usuario usuario) {
        Turma turma = turmaRepository.findByIdAndUsuario_Id(turmaId, usuario.getId()).orElseThrow(() -> new NotFoundException("Turma não encontrada para id: " + turmaId));

        validaacaoNota(requestDTO.nota());

        turma.addAtividadeAvaliativa(atividadeAvaliativaMapper.createAtividadeAvaliativaRequestDTOtoEntity(requestDTO));
        turmaRepository.save(turma);
    }

    @Transactional
    public void update(Long turmaId, Long id, UpdateAtividadeAvaliativaRequestDTO requestDTO, Usuario usuario) {
        Turma turma = turmaRepository.findByIdAndUsuario_Id(turmaId, usuario.getId()).orElseThrow(() -> new NotFoundException("Turma não encontrada para id: " + turmaId));

        validaacaoNota(requestDTO.nota());

        turma.updateAtividadeAvaliativa(id, requestDTO);
        turmaRepository.save(turma);
    }

    @Transactional
    public void delete(Long turmaId, Long id, Usuario usuario) {
        Turma turma = turmaRepository.findByIdAndUsuario_Id(turmaId, usuario.getId()).orElseThrow(() -> new NotFoundException("Turma não encontrada para id: " + turmaId));

        turma.removeAtividadeAvaliativa(id);

        turmaRepository.save(turma);
    }

    private void validaacaoNota(BigDecimal nota) {
        if (nota.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Nota não pode ter um valor menor ou igual a zero");
    }
}
