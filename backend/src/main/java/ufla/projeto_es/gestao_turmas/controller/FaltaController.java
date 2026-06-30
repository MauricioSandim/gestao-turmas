package ufla.projeto_es.gestao_turmas.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ufla.projeto_es.gestao_turmas.mapper.FaltaMapper;
import ufla.projeto_es.gestao_turmas.model.falta.request.CreateFaltaRequestDTO;
import ufla.projeto_es.gestao_turmas.model.falta.request.UpdateFaltaRequestDTO;
import ufla.projeto_es.gestao_turmas.model.falta.response.FaltaResponseDTO;
import ufla.projeto_es.gestao_turmas.model.usuario.Usuario;
import ufla.projeto_es.gestao_turmas.service.FaltaService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/turmas")
@Tag(name = "Faltas", description = "Endpoint para CRUD de turmas")
@RequiredArgsConstructor
public class FaltaController {

    private final FaltaService faltaService;
    private final FaltaMapper faltaMapper;

    @GetMapping("/{turmaId}/faltas/{matriculaId}")
    @Operation(summary = "Obter faltas por matrícula")
    public ResponseEntity<List<FaltaResponseDTO>> consultar(@PathVariable Long matriculaId, @PathVariable Long turmaId, @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(faltaService.consultar(turmaId, matriculaId, usuario).stream().map(faltaMapper::toResponseDTO).toList());
    }

    @PostMapping("/{turmaId}/faltas/{matriculaId}")
    @Operation(summary = "Criar faltas por matrícula")
    public ResponseEntity<FaltaResponseDTO> cadastrar(@PathVariable Long turmaId, @PathVariable Long matriculaId, @RequestBody @Valid CreateFaltaRequestDTO requestDTO, @AuthenticationPrincipal Usuario usuario) {
        faltaService.criar(turmaId, matriculaId, requestDTO, usuario);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{turmaId}/faltas/{matriculaId}/{id}")
    @Operation(summary = "Atualizar faltas por matrícula")
    public ResponseEntity<FaltaResponseDTO> atualizar(@PathVariable Long turmaId, @PathVariable Long matriculaId, @PathVariable Long id, @RequestBody @Valid UpdateFaltaRequestDTO requestDTO, @AuthenticationPrincipal Usuario usuario) {
        faltaService.update(turmaId, matriculaId, id, requestDTO, usuario);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{turmaId}/faltas/{matriculaId}/{id}")
    @Operation(summary = "Deletar faltas por matrícula")
    public ResponseEntity<?> deletar(@PathVariable Long turmaId, @PathVariable Long matriculaId, @PathVariable Long id, @AuthenticationPrincipal Usuario usuario) {
        faltaService.deletar(turmaId, matriculaId, id, usuario);
        return ResponseEntity.noContent().build();
    }
}
