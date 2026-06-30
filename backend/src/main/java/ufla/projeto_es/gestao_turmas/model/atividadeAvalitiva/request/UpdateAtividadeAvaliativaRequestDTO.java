package ufla.projeto_es.gestao_turmas.model.atividadeAvalitiva.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UpdateAtividadeAvaliativaRequestDTO(
        @NotEmpty(message = "Nome da atividade não pode estar vazio") String nome, @NotNull BigDecimal nota) {
}
