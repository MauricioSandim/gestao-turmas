package ufla.projeto_es.gestao_turmas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufla.projeto_es.gestao_turmas.model.matricula.Matricula;
import ufla.projeto_es.gestao_turmas.model.turma.Turma;
import ufla.projeto_es.gestao_turmas.model.usuario.Usuario;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
    boolean existsByTurmaAndAluno(Turma turma, Usuario aluno);
}
