package ufla.projeto_es.gestao_turmas.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ufla.projeto_es.gestao_turmas.mapper.UsuarioMapper;
import ufla.projeto_es.gestao_turmas.model.type.RoleEnum;
import ufla.projeto_es.gestao_turmas.model.usuario.Usuario;
import ufla.projeto_es.gestao_turmas.model.usuario.response.UsuarioResponseDTO;
import ufla.projeto_es.gestao_turmas.service.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuario")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-jwt")
@Tag(name = "Usuarios", description = "Endpoint para visualização de usuarios")
public class UsuarioController {

    private final UsuarioMapper usuarioMapper;
    private final UsuarioService usuarioService;

    @GetMapping
    @Operation(summary = "Listar usuários", description = "Retorna lista paginada de usuários com filtros opcionais")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuariosPorRole(@RequestParam RoleEnum role){
        return ResponseEntity.ok(usuarioService.findAllByRole(role).stream().map(usuarioMapper::toResponse).toList());
    }

    @GetMapping("/me")
    @Operation(summary = "Mostrar informações do usuário logado")
    public ResponseEntity<UsuarioResponseDTO> getMe(@AuthenticationPrincipal Usuario usuario){
        return ResponseEntity.ok(usuarioMapper.toResponse(usuario));
    }
}
