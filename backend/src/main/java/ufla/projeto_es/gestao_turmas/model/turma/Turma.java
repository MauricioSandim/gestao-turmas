package ufla.projeto_es.gestao_turmas.model.turma;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ufla.projeto_es.gestao_turmas.model.baseEntity.OwnedEntity;
import ufla.projeto_es.gestao_turmas.model.usuario.Usuario;

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
}
