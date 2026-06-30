package ufla.projeto_es.gestao_turmas.model.matricula;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ufla.projeto_es.gestao_turmas.exception.NotFoundException;
import ufla.projeto_es.gestao_turmas.model.baseEntity.BaseEntity;
import ufla.projeto_es.gestao_turmas.model.falta.Falta;
import ufla.projeto_es.gestao_turmas.model.falta.request.UpdateFaltaRequestDTO;
import ufla.projeto_es.gestao_turmas.model.horarioAula.HorarioAula;
import ufla.projeto_es.gestao_turmas.model.turma.Turma;
import ufla.projeto_es.gestao_turmas.model.usuario.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "matricula")
public class Matricula extends BaseEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "aluno_id", nullable = false)
    private Usuario aluno;

    @ManyToOne
    @JoinColumn(name = "turma_id", nullable = false)
    private Turma turma;

    @OneToMany(mappedBy = "matricula", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name = "position")
    private final List<Falta> faltas = new ArrayList<>();

    public Falta addFalta(Falta falta) {
        faltas.forEach((e) -> {
            if (e.getHorarioAula().equals(falta.getHorarioAula()) && e.getData().equals(falta.getData()))
                throw new IllegalArgumentException("Falta já cadastrada para aluno de id  " + this.aluno.getId() + ", no horario aula de id " + falta.getHorarioAula().getId() + " na data " + falta.getData());
        });

        falta.setMatricula(this);

        faltas.add(falta);

        return falta;
    }

    public void removeFalta(Long id) {
        if (!faltas.removeIf((e) -> Objects.equals(e.getId(), id))) {
            throw new NotFoundException("Falta com id " + id + " não existente");
        }
    }

    public Falta updateFalta(Long id, UpdateFaltaRequestDTO falta) {
        Falta faltaAtual = faltas.stream().filter((e) -> Objects.equals(e.getId(), id)).findFirst().orElseThrow(() -> new NotFoundException("Falta com id " + id + " não existente"));

        faltaAtual.setData(falta.data());

        return faltaAtual;
    }
}
