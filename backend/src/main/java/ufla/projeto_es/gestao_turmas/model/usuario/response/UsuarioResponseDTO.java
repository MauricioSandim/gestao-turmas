package ufla.projeto_es.gestao_turmas.model.usuario.response;

import ufla.projeto_es.gestao_turmas.model.type.RoleEnum;

public record UsuarioResponseDTO(Long id, String nome, String email, String roleName) {
}
