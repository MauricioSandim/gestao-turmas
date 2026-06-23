package ufla.projeto_es.gestao_turmas.model.turma.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateTurmaRequestDTO (@NotBlank(message = "O nome da turma não pode estar vazio") String nome) {
}
