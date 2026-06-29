package ufla.projeto_es.gestao_turmas.model.matricula.request;

import jakarta.validation.constraints.NotNull;

public record UpdateMatriculaRequestDTO(@NotNull Long turmaId, @NotNull Long alunoId) {
}
