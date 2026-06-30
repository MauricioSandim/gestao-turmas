package ufla.projeto_es.gestao_turmas.model.matricula.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateMatriculaRequestDTO(@NotNull Long turmaId, @NotNull Long alunoId) {
}
