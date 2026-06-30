package ufla.projeto_es.gestao_turmas.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ufla.projeto_es.gestao_turmas.mapper.MatriculaMapper;
import ufla.projeto_es.gestao_turmas.model.matricula.Matricula;
import ufla.projeto_es.gestao_turmas.model.matricula.response.MatriculaCompleteResponseDTO;
import ufla.projeto_es.gestao_turmas.model.matricula.response.MatriculaResponseDTO;
import ufla.projeto_es.gestao_turmas.model.usuario.Usuario;
import ufla.projeto_es.gestao_turmas.service.AreaAlunoService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/area-aluno")
@Tag(name = "Area aluno", description = "Endpoint para consultas dos alunos")
@RequiredArgsConstructor
public class AreaAlunoController {

    private final AreaAlunoService areaAlunoService;
    private final MatriculaMapper matriculaMapper;

    @GetMapping
    public ResponseEntity<List<MatriculaCompleteResponseDTO>> getMatriculas(@AuthenticationPrincipal Usuario usuario) {
        List<Matricula> matriculas = areaAlunoService.findAll(usuario);

        return ResponseEntity.ok(matriculas.stream().map(matriculaMapper::toCompleteResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatriculaCompleteResponseDTO> getMatricula(@PathVariable Long id, @AuthenticationPrincipal Usuario usuario) {
        Matricula matricula = areaAlunoService.findById(id, usuario);

        return ResponseEntity.ok(matriculaMapper.toCompleteResponse(matricula));
    }
}
