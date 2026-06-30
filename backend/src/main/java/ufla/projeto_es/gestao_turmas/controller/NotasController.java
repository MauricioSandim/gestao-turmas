package ufla.projeto_es.gestao_turmas.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ufla.projeto_es.gestao_turmas.mapper.NotaMapper;
import ufla.projeto_es.gestao_turmas.model.nota.response.NotaResponseDTO;
import ufla.projeto_es.gestao_turmas.model.usuario.Usuario;
import ufla.projeto_es.gestao_turmas.service.NotaService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/turmas")
@RequiredArgsConstructor
@Tag(name = "Notas", description = "Endpoint para CRUD de turmas")
public class NotasController {
    private final NotaService notaService;
    private final NotaMapper notaMapper;

    @GetMapping("/{turmaId}/notas/{matriculaId}")
    @Operation(summary = "Busca notas por matrícula")
    public ResponseEntity<List<NotaResponseDTO>> consultarNotas(@PathVariable Long turmaId, @PathVariable Long matriculaId, @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(notaService.consultar(turmaId, matriculaId, usuario).stream().map(notaMapper::toResponse).toList());
    }

    @PostMapping("/{turmaId}/notas/{matriculaId}")
    @Operation(summary = "Cadastra notas por matrícula")
    public ResponseEntity<?> cadastrarNota(@PathVariable Long turmaId, @PathVariable Long matriculaId, @RequestParam("atividadeAvaliativaId") Long atividadeAvaliativaId, @RequestParam("valor") BigDecimal valor, @AuthenticationPrincipal Usuario usuario) {
        notaService.criar(turmaId, matriculaId, atividadeAvaliativaId, valor, usuario);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{turmaId}/notas/{matriculaId}/{id}")
    @Operation(summary = "Atualiza notas por matrícula")
    public ResponseEntity<?> atualizarNota(@PathVariable Long turmaId, @PathVariable Long matriculaId, @PathVariable Long id, @RequestParam("valor") BigDecimal valor, @AuthenticationPrincipal Usuario usuario) {
        notaService.update(turmaId, matriculaId, id, valor, usuario);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{turmaId}/notas/{matriculaId}/{id}")
    @Operation(summary = "Deleta notas por matrícula")
    public ResponseEntity<?> deletarNota(@PathVariable Long turmaId, @PathVariable Long matriculaId, @PathVariable Long id, @AuthenticationPrincipal Usuario usuario) {
        notaService.delete(turmaId, matriculaId, id, usuario);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
