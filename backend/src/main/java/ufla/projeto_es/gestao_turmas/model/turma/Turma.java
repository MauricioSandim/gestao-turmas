package ufla.projeto_es.gestao_turmas.model.turma;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ufla.projeto_es.gestao_turmas.exception.NotFoundException;
import ufla.projeto_es.gestao_turmas.model.baseEntity.OwnedEntity;
import ufla.projeto_es.gestao_turmas.model.matricula.Matricula;
import ufla.projeto_es.gestao_turmas.model.type.RoleEnum;
import ufla.projeto_es.gestao_turmas.model.usuario.Usuario;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "turma")
public class Turma extends OwnedEntity<Long> {

    @Column(name = "nome", length = 255, nullable = false)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "turma",orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Matricula> matriculas;

    public List<Usuario> getAlunos() {
        return matriculas.stream().map(Matricula::getAluno).toList();
    }

    public Matricula addAluno(Usuario aluno) {
        if (!aluno.getRoleName().equals(RoleEnum.ALUNO.name())) {
            throw new RuntimeException();
        }

        Matricula matricula = new Matricula(aluno, this);

        matriculas.add(matricula);

        return matricula;
    }

    public void removeAluno(Usuario aluno) {
        if (!matriculas.removeIf((e) -> Objects.equals(e.getAluno().getId(), aluno.getId()))) {
            throw new NotFoundException("Aluno com id: " + aluno.getId() + " não matriculado na turma com id: " + this.getId());
        }

    }
}
