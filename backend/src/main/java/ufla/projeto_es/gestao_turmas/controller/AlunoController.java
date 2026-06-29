package ufla.projeto_es.gestao_turmas.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ufla.projeto_es.gestao_turmas.mapper.UsuarioMapper;
import ufla.projeto_es.gestao_turmas.model.usuario.Usuario;
import ufla.projeto_es.gestao_turmas.model.usuario.response.UsuarioResponseDTO;
import ufla.projeto_es.gestao_turmas.service.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/aluno")
@RequiredArgsConstructor
@Tag(name = "Alunos", description = "Endpoint para consulta de alunos")
public class AlunoController {
    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @GetMapping
    @Operation(summary = "Consultar todos os alunos")
    public ResponseEntity<List<UsuarioResponseDTO>> consultarTodosAlunos(){
        List<Usuario> alunos = usuarioService.findAllAlunos();

        return ResponseEntity.ok(alunos.stream().map(usuarioMapper::toResponse).toList());
    }

}
