package ufla.projeto_es.gestao_turmas.model.atividadeAvalitiva;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ufla.projeto_es.gestao_turmas.model.baseEntity.BaseEntity;
import ufla.projeto_es.gestao_turmas.model.nota.Nota;
import ufla.projeto_es.gestao_turmas.model.turma.Turma;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "atividade_avaliativa")
public class AtividadeAvaliativa extends BaseEntity<Long> {

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "nota", precision = 5, scale = 2)
    private BigDecimal nota;

    @ManyToOne
    @JoinColumn(name = "turma_id", nullable = false)
    private Turma turma;

    @OneToMany(mappedBy = "atividadeAvaliativa")
    private List<Nota> notas;
}
