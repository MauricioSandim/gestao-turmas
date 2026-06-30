package ufla.projeto_es.gestao_turmas.model.turma;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ufla.projeto_es.gestao_turmas.exception.NotFoundException;
import ufla.projeto_es.gestao_turmas.model.baseEntity.OwnedEntity;
import ufla.projeto_es.gestao_turmas.model.horarioAula.HorarioAula;
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


    // HORARIO AULA
    @OneToMany(mappedBy = "turma", orphanRemoval = true, cascade = CascadeType.ALL)
    @OrderColumn(name = "position")
    private List<HorarioAula> horariosAula;

    public void addHorarioAula(HorarioAula horarioAula) {
        horariosAula.forEach((e) -> {
            if (e.getHora().equals(horarioAula.getHora()) && e.getDiaSemana() == horarioAula.getDiaSemana())
                throw new IllegalArgumentException("Horario aula já cadastrado para turma de id:" + this.getId());
        });

        horarioAula.setTurma(this);

        horariosAula.add(horarioAula);
    }

    public void removeHorarioAula(Long id) {
        if (!horariosAula.removeIf((e) -> Objects.equals(e.getId(), id))) {
            throw new NotFoundException("Horario aula com id: " + id + " não existente na turma com id: " + this.getId());
        }
    }

    public void updateHorarioAula(Long id, HorarioAula horarioAula) {
        horariosAula.forEach((e) -> {
            if (e.getHora().equals(horarioAula.getHora()) && e.getDiaSemana() == horarioAula.getDiaSemana())
                throw new IllegalArgumentException("Horario aula já cadastrado para turma de id:" + this.getId());
        });

        HorarioAula horarioAulaAtual = horariosAula.stream().filter((e) -> Objects.equals(e.getId(), id)).findFirst().orElseThrow(() -> new NotFoundException("Horario aula com id: " + id + " não existente na turma com id: " + this.getId()));

        horarioAulaAtual.setHora(horarioAula.getHora());
        horarioAulaAtual.setDiaSemana(horarioAula.getDiaSemana());
    }


    // MATRICULA
    @OneToMany(mappedBy = "turma", orphanRemoval = true, cascade = CascadeType.ALL)
    @OrderColumn(name = "position")
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
