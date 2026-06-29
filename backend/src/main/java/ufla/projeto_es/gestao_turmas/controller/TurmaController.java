package ufla.projeto_es.gestao_turmas.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ufla.projeto_es.gestao_turmas.mapper.TurmaMapper;
import ufla.projeto_es.gestao_turmas.model.turma.Turma;
import ufla.projeto_es.gestao_turmas.model.turma.request.CreateTurmaRequestDTO;
import ufla.projeto_es.gestao_turmas.model.turma.request.UpdateTurmaRequestDTO;
import ufla.projeto_es.gestao_turmas.model.turma.response.TurmaResponseDTO;
import ufla.projeto_es.gestao_turmas.model.turma.response.TurmaSummaryResponseDTO;
import ufla.projeto_es.gestao_turmas.model.usuario.Usuario;
import ufla.projeto_es.gestao_turmas.service.TurmaService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/turmas")
@RequiredArgsConstructor
@Tag(name = "Turmas", description = "Endpoint para CRUD de turmas")
public class TurmaController {

    private final TurmaService turmaService;
    private final TurmaMapper turmaMapper;

    @GetMapping
    @Operation(summary = "Consultar turmas de um professor")
    public ResponseEntity<List<TurmaSummaryResponseDTO>> getTurmas(@AuthenticationPrincipal Usuario usuario) {
        List<Turma> turmas = turmaService.findAllByUsuarioId(usuario);

        return ResponseEntity.ok(turmas.stream().map(turmaMapper::toSummaryResponse).toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar uma turma de um professor")
    public ResponseEntity<TurmaResponseDTO> getTurma(@PathVariable Long id, @AuthenticationPrincipal Usuario usuario) {
        Turma turma = turmaService.findById(id, usuario);

        return ResponseEntity.ok(turmaMapper.toResponse(turma));
    }

    @PostMapping
    @Operation(summary = "Criar turma para um professor")
    public ResponseEntity<TurmaResponseDTO> criarTurma(@RequestBody CreateTurmaRequestDTO requestDTO, @AuthenticationPrincipal Usuario usuario) {
        Turma turma = turmaService.criar(requestDTO, usuario);

        return ResponseEntity.ok(turmaMapper.toResponse(turma));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modificar turma para um professor")
    public ResponseEntity<TurmaResponseDTO> atualizarTurma(@PathVariable Long id, @RequestBody UpdateTurmaRequestDTO requestDTO, @AuthenticationPrincipal Usuario usuario) {
        Turma turma = turmaService.atualizar(id, requestDTO, usuario);

        return ResponseEntity.ok(turmaMapper.toResponse(turma));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Criar turma para um professor")
    public ResponseEntity<TurmaResponseDTO> deleteTurma(@PathVariable Long id, @AuthenticationPrincipal Usuario usuario) {
        turmaService.delete(id, usuario);

        return ResponseEntity.noContent().build();
    }
}
