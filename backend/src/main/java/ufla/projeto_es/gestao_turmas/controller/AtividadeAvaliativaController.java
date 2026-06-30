package ufla.projeto_es.gestao_turmas.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ufla.projeto_es.gestao_turmas.mapper.AtividadeAvaliativaMapper;
import ufla.projeto_es.gestao_turmas.model.atividadeAvalitiva.AtividadeAvaliativa;
import ufla.projeto_es.gestao_turmas.model.atividadeAvalitiva.request.CreateAtividadeAvaliativaRequestDTO;
import ufla.projeto_es.gestao_turmas.model.atividadeAvalitiva.request.UpdateAtividadeAvaliativaRequestDTO;
import ufla.projeto_es.gestao_turmas.model.atividadeAvalitiva.response.AtividadeAvaliativaResponseDTO;
import ufla.projeto_es.gestao_turmas.model.usuario.Usuario;
import ufla.projeto_es.gestao_turmas.service.AtividadeAvaliativaService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/turmas")
@Tag(name = "Atividades Avaliativas", description = "Endpoint para CRUD de turmas")
@RequiredArgsConstructor
public class AtividadeAvaliativaController {

    private final AtividadeAvaliativaService atividadeAvaliativaService;
    private final AtividadeAvaliativaMapper atividadeAvaliativaMapper;


    @PostMapping("/{turmaId}/atividades-avaliativas")
    @Operation(summary = "Cria uma nova atividade avaliativa")
    public ResponseEntity<?> createAtividadeAvaliativa(@PathVariable Long turmaId, @RequestBody @Valid CreateAtividadeAvaliativaRequestDTO request, @AuthenticationPrincipal Usuario usuario) {
        atividadeAvaliativaService.criar(turmaId, request, usuario);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{turmaId}/atividades-avaliativas")
    @Operation(summary = "Lista todas as atividades avaliativas de uma turma")
    public ResponseEntity<List<AtividadeAvaliativaResponseDTO>> getAtividadesAvaliativas(@PathVariable Long turmaId, @AuthenticationPrincipal Usuario usuario) {
        List<AtividadeAvaliativa> atividades = atividadeAvaliativaService.consultar(turmaId, usuario);

        return ResponseEntity.ok().body(atividades.stream().map(atividadeAvaliativaMapper::toResponseDTO).toList());
    }

    @PutMapping("/{turmaId}/atividades-avaliativas/{id}")
    @Operation(summary = "Atualiza uma atividade avaliativa")
    public ResponseEntity<?> updateAtividadeAvaliativa(@PathVariable Long turmaId, @PathVariable Long id, @RequestBody @Valid UpdateAtividadeAvaliativaRequestDTO requestDTO, @AuthenticationPrincipal Usuario usuario) {
        atividadeAvaliativaService.update(turmaId, id, requestDTO, usuario);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping(("/{turmaId}/atividades-avaliativas/{id}"))
    @Operation(summary = "Deleta uma atividade avaliativa")
    public ResponseEntity<?> deleteAtividadeAvaliativa(@PathVariable Long turmaId, @PathVariable Long id, @AuthenticationPrincipal Usuario usuario) {
        atividadeAvaliativaService.delete(turmaId, id, usuario);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
