package ufla.projeto_es.gestao_turmas.model.roles;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ufla.projeto_es.gestao_turmas.model.baseEntity.BaseEntity;
import ufla.projeto_es.gestao_turmas.model.type.RoleEnum;

@Getter
@Setter
@Entity
@Table(name = "role")
public class Role extends BaseEntity<Long> {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private RoleEnum nome;
}
