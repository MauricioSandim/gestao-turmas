package ufla.projeto_es.gestao_turmas.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ufla.projeto_es.gestao_turmas.exception.NotFoundException;
import ufla.projeto_es.gestao_turmas.exception.turma.TurmaComNomesIguaisException;
import ufla.projeto_es.gestao_turmas.mapper.TurmaMapper;
import ufla.projeto_es.gestao_turmas.model.turma.Turma;
import ufla.projeto_es.gestao_turmas.model.turma.request.CreateTurmaRequestDTO;
import ufla.projeto_es.gestao_turmas.model.turma.request.UpdateTurmaRequestDTO;
import ufla.projeto_es.gestao_turmas.model.type.RoleEnum;
import ufla.projeto_es.gestao_turmas.model.usuario.Usuario;
import ufla.projeto_es.gestao_turmas.repository.TurmaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TurmaService {

    private final TurmaRepository turmaRepository;
    private final TurmaMapper turmaMapper;

    @Transactional(readOnly = true)
    public List<Turma> findAllByUsuarioId(Usuario usuario) {
        log.info("Finding all turmas by usuario id {} {}", usuario.getId(), usuario.getUsername());

        return turmaRepository.findAllByUsuario_Id(usuario.getId());
    }

    @Transactional(readOnly = true)
    public Turma findById(Long id, Usuario usuario) {
        return turmaRepository.findByIdAndUsuario_Id(id, usuario.getId()).orElseThrow(() -> new NotFoundException("Turma não encontrada para id: " + id));
    }

    @Transactional(readOnly = true)
    public Turma findByNome(String nome, Usuario usuario) {
        return turmaRepository.findByNomeAndUsuario_Id(nome, usuario.getId()).orElseThrow(() -> new NotFoundException("Turma não encontrada para nome: " + nome));
    }

    @Transactional
    public Turma criar(CreateTurmaRequestDTO requestDTO, Usuario usuario) {
        if (!usuario.getRoleName().equals(RoleEnum.PROFESSOR.name())) {
            throw new AccessDeniedException("Acesso negado");
        }

        Turma turma = turmaMapper.createTurmaRequestDTOtoEntity(requestDTO);

        if(turmaRepository.existsByNomeAndUsuario_Id(turma.getNome(), usuario.getId())) {
            throw new TurmaComNomesIguaisException("Já existe uma turma com nome: "  + turma.getNome());
        }

        turma.setUsuario(usuario);

        return turmaRepository.save(turma);
    }

    @Transactional
    public Turma atualizar(Long id, UpdateTurmaRequestDTO requestDTO, Usuario usuario) {
        if (!usuario.getRoleName().equals(RoleEnum.PROFESSOR.name())) {
            throw new AccessDeniedException("Acesso negado");
        }
        Turma newTurma = turmaMapper.updateTurmaRequestDTOtoEntity(requestDTO);

        Turma turma = turmaRepository.findByIdAndUsuario_Id(id, usuario.getId()).orElseThrow(() -> new NotFoundException("Turma não encontrada para id: " + id));

        turmaMapper.copyProperties(newTurma, turma);

        return turmaRepository.save(turma);
    }

    @Transactional
    public void delete(Long id, Usuario usuario) {
        if (!usuario.getRoleName().equals(RoleEnum.PROFESSOR.name())) {
            throw new AccessDeniedException("Acesso negado");
        }

        Turma turma = turmaRepository.findByIdAndUsuario_Id(id, usuario.getId()).orElseThrow(() -> new NotFoundException("Turma não encontrada para id: " + id));

        if (!turma.getMatriculas().isEmpty())
            throw new IllegalArgumentException("NNão é possível excluir uma turma com matrículas associadas.");

        turmaRepository.delete(turma);
    }

}

