package ufla.projeto_es.gestao_turmas.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ufla.projeto_es.gestao_turmas.exception.NotFoundException;
import ufla.projeto_es.gestao_turmas.mapper.TurmaMapper;
import ufla.projeto_es.gestao_turmas.model.matricula.Matricula;
import ufla.projeto_es.gestao_turmas.model.turma.Turma;
import ufla.projeto_es.gestao_turmas.model.type.RoleEnum;
import ufla.projeto_es.gestao_turmas.model.usuario.Usuario;
import ufla.projeto_es.gestao_turmas.repository.TurmaRepository;
import ufla.projeto_es.gestao_turmas.repository.UsuarioRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MatriculaService {
    private final TurmaRepository turmaRepository;
    private final UsuarioRepository usuarioRepository;
    private final TurmaMapper turmaMapper;

    @Transactional(readOnly = true)
    public List<Matricula> consultar(Long turmaId, Usuario usuario) {
        return turmaRepository.findByIdAndUsuario_Id(turmaId, usuario.getId()).orElseThrow(() -> new NotFoundException("Turma não encontrada para id: " + turmaId)).getMatriculas();
    }

    @Transactional
    public Matricula criar(Long turmaId, Long alunoId, Usuario usuario) {
        Turma turma = turmaRepository.findByIdAndUsuario_Id(turmaId, usuario.getId()).orElseThrow(() -> new NotFoundException("Turma não encontrada para id: " + turmaId));

        Usuario aluno = usuarioRepository.findByIdAndRoleNome(alunoId, RoleEnum.ALUNO).orElseThrow(() -> new NotFoundException("Aluno não encontrada para id: " + turmaId));

        Matricula matricula = turma.addAluno(aluno);

        turmaRepository.save(turma);

        return matricula;
    }

    @Transactional
    public void deletar(Long turmaId, Long alunoId, Usuario usuario) {
        Turma turma = turmaRepository.findByIdAndUsuario_Id(turmaId, usuario.getId()).orElseThrow(() -> new NotFoundException("Turma não encontrada para id: " + turmaId));

        Usuario aluno = usuarioRepository.findByIdAndRoleNome(alunoId, RoleEnum.ALUNO).orElseThrow(() -> new NotFoundException("Aluno não encontrada para id: " + alunoId));

        // precisa por validação para notas e faltas

        turma.removeAluno(aluno);
    }

}
