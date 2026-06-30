package ufla.projeto_es.gestao_turmas.model.nota;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ufla.projeto_es.gestao_turmas.model.atividadeAvalitiva.AtividadeAvaliativa;
import ufla.projeto_es.gestao_turmas.model.baseEntity.BaseEntity;
import ufla.projeto_es.gestao_turmas.model.matricula.Matricula;

import java.math.BigDecimal;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "nota")
public class Nota extends BaseEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "matricula_id", nullable = false)
    private Matricula matricula;

    @ManyToOne
    @JoinColumn(name = "atividade_avaliativa_id", nullable = false)
    private AtividadeAvaliativa atividadeAvaliativa;

    @Column(name = "nota_final", precision = 5, scale = 2)
    private BigDecimal valor;
}
