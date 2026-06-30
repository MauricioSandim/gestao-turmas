package ufla.projeto_es.gestao_turmas.model.horarioAula;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ufla.projeto_es.gestao_turmas.model.baseEntity.BaseEntity;
import ufla.projeto_es.gestao_turmas.model.falta.Falta;
import ufla.projeto_es.gestao_turmas.model.turma.Turma;
import ufla.projeto_es.gestao_turmas.model.type.DiasEnum;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "horario_aula")
public class HorarioAula extends BaseEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "turma_id", nullable = false)
    private Turma turma;

    @Enumerated(EnumType.STRING)
    @Column(name = "dia_semana", nullable = false)
    private DiasEnum diaSemana;

    @Column(name = "hora", nullable = false)
    private LocalTime hora;

    @OneToMany(mappedBy = "horarioAula")
    private List<Falta> falta;
}
