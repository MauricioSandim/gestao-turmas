package ufla.projeto_es.gestao_turmas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ufla.projeto_es.gestao_turmas.model.turma.Turma;

import java.util.List;
import java.util.Optional;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Long> {

    List<Turma> findAllByUsuario_Id(Long usuarioId);

    Optional<Turma> findByIdAndUsuario_Id(Long id, Long usuarioId);

    Optional<Turma> findByNomeAndUsuario_Id(String nome, Long usuarioId);

    boolean existsByNomeAndUsuario_Id(String nome, Long usuarioId);
}
