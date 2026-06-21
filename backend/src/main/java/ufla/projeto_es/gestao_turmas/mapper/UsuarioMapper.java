package ufla.projeto_es.gestao_turmas.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ufla.projeto_es.gestao_turmas.model.usuario.Usuario;
import ufla.projeto_es.gestao_turmas.model.usuario.request.UsuarioRequestDTO;
import ufla.projeto_es.gestao_turmas.model.usuario.response.UsuarioResponseDTO;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "senha", ignore = true)
    @Mapping(target = "role", ignore = true)
    Usuario toEntity(UsuarioRequestDTO request);

    UsuarioResponseDTO toResponse(Usuario usuario);
}
