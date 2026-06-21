package ufla.projeto_es.gestao_turmas.model.baseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ufla.projeto_es.gestao_turmas.model.usuario.Usuario;

import java.io.Serializable;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class OwnedEntity<T extends Serializable> extends BaseEntity<T>{
    private Usuario usuario;
}
