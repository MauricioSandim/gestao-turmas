package ufla.projeto_es.gestao_turmas.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ufla.projeto_es.gestao_turmas.exception.NotFoundException;
import ufla.projeto_es.gestao_turmas.model.matricula.Matricula;
import ufla.projeto_es.gestao_turmas.model.usuario.Usuario;
import ufla.projeto_es.gestao_turmas.repository.MatriculaRepository;
import ufla.projeto_es.gestao_turmas.repository.UsuarioRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AreaAlunoService {

    private final MatriculaRepository matriculaRepository;

    @Transactional(readOnly = true)
    public List<Matricula> findAll(Usuario usuario) {
        return matriculaRepository.findAllByAluno_Id(usuario.getId());
    }

    @Transactional(readOnly = true)
    public Matricula findById(Long id, Usuario usuario) {
        return matriculaRepository.findByIdAndAluno_Id(id, usuario.getId()).orElseThrow(() -> new NotFoundException("Matrícula não encontrada"));
    }
}
