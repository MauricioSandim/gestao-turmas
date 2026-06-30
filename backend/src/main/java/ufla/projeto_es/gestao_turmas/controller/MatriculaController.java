package ufla.projeto_es.gestao_turmas.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ufla.projeto_es.gestao_turmas.mapper.MatriculaMapper;
import ufla.projeto_es.gestao_turmas.mapper.UsuarioMapper;
import ufla.projeto_es.gestao_turmas.model.matricula.Matricula;
import ufla.projeto_es.gestao_turmas.model.matricula.response.MatriculaResponseDTO;
import ufla.projeto_es.gestao_turmas.model.usuario.Usuario;
import ufla.projeto_es.gestao_turmas.model.usuario.response.UsuarioResponseDTO;
import ufla.projeto_es.gestao_turmas.service.MatriculaService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/turmas")
@RequiredArgsConstructor
@Tag(name = "Matricula", description = "Endpoint para CRUD de turmas")
public class MatriculaController {

    private final MatriculaService matriculaService;
    private final MatriculaMapper matriculaMapper;
    private final UsuarioMapper usuarioMapper;

    @GetMapping("/{turmaId}/matricula")
    @Operation(summary = "Consultar matriculas em uma turma")
    public ResponseEntity<List<MatriculaResponseDTO>> consultar(@PathVariable(name = "turmaId") Long turmaId, @AuthenticationPrincipal Usuario usuario) {
        List<Matricula> alunos = matriculaService.consultar(turmaId, usuario);

        return ResponseEntity.ok(alunos.stream().map(matriculaMapper::toResponse).toList());
    }

    @PostMapping("/{turmaId}/matricula")
    @Operation(summary = "Cadastrar matricula em uma turma")
    public ResponseEntity<MatriculaResponseDTO> criar(@PathVariable(name = "turmaId") Long turmaId, @RequestParam Long alunoId, @AuthenticationPrincipal Usuario usuario) {
        Matricula matricula = matriculaService.criar(turmaId, alunoId, usuario);

        return ResponseEntity.ok(matriculaMapper.toResponse(matricula));
    }

    @DeleteMapping("/{turmaId}/matricula")
    @Operation(summary = "Deletar matricula em uma turma")
    public ResponseEntity<?> deletar(@PathVariable(name = "turmaId") Long turmaId, @RequestParam Long alunoId, @AuthenticationPrincipal Usuario usuario) {
        matriculaService.deletar(turmaId, alunoId, usuario);

        return ResponseEntity.noContent().build();
    }
}
