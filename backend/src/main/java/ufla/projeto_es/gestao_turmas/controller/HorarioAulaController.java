package ufla.projeto_es.gestao_turmas.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import ufla.projeto_es.gestao_turmas.mapper.HorarioAulaMapper;
import ufla.projeto_es.gestao_turmas.model.horarioAula.request.CreateHorarioAulaRequestDTO;
import ufla.projeto_es.gestao_turmas.model.horarioAula.request.UpdateHorarioAulaRequestDTO;
import ufla.projeto_es.gestao_turmas.model.horarioAula.response.HorarioAulaResponseDTO;
import ufla.projeto_es.gestao_turmas.model.usuario.Usuario;
import ufla.projeto_es.gestao_turmas.service.HorarioAulaService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/turmas")
@Tag(name = "Horario Aula", description = "Endpoint para CRUD de turmas")
@RequiredArgsConstructor
public class HorarioAulaController {
    private final HorarioAulaService horarioAulaService;
    private final HorarioAulaMapper horarioAulaMapper;

    @GetMapping("/{idTurma}/horarios-aula")
    @Operation(summary = "Listar todos os horários de uma turma")
    public ResponseEntity<List<HorarioAulaResponseDTO>> getHorarioAulasByTurmaId(@PathVariable Long idTurma, @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok().body(horarioAulaService.consultarHorarios(idTurma, usuario).stream().map(horarioAulaMapper::toResponse).toList());
    }

    @PostMapping("/{turmaId}/horarios-aula")
    @Operation(summary = "Cria um novo horário de aula para uma turma")
    public ResponseEntity<?> saveHorarioAula(@PathVariable Long turmaId, @RequestBody @Valid CreateHorarioAulaRequestDTO requestDTO, @AuthenticationPrincipal Usuario usuario) {
        horarioAulaService.criar(turmaId, requestDTO, usuario);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("{turmaId}/horarios-aula/{horarioAulaId}")
    @Operation(summary = "Atualiza um horário de aula existente")
    public ResponseEntity<?> updatateHorarioAula(@PathVariable Long turmaId, @PathVariable Long horarioAulaId, @RequestBody @Valid UpdateHorarioAulaRequestDTO requestDTO, @AuthenticationPrincipal Usuario usuario) {
        horarioAulaService.update(turmaId, horarioAulaId, requestDTO, usuario);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{turmaId}/horarios-aula/{horarioAulaId}")
    @Operation(summary = "Exclui um horário de aula existente")
    public ResponseEntity<?> deleteHorarioAula(@PathVariable Long turmaId, @PathVariable Long horarioAulaId, @AuthenticationPrincipal Usuario usuario) {
        horarioAulaService.delete(turmaId, horarioAulaId, usuario);

        return ResponseEntity.noContent().build();
    }


}
