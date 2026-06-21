package ufla.projeto_es.gestao_turmas.mapper.generic;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import java.util.List;
import java.util.Set;

@Validated
public interface RequestToEntity<Entity, RequestDTO> {
    Entity requestToEntity(@Valid RequestDTO requestDTO);

    List<Entity> requestToEntity(List<@Valid RequestDTO> requestDTO);

    Set<Entity> requestToEntity(Set<@Valid RequestDTO> requestDTO);
}
