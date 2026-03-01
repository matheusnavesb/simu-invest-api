package br.gov.caixa.api.investimentos.dto.simulacao;

import br.gov.caixa.api.investimentos.domain.enums.TipoProduto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record SimulacaoDTO(
        @NotNull(message = "clienteId é obrigatório.")
        Long clienteId,

        @NotNull(message = "valor é obrigatório.")
        @Positive(message = "valor deve ser positivo.")
        BigDecimal valor,

        @NotNull(message = "prazoMeses é obrigatório.")
        @Min(value = 1, message = "prazoMeses deve ser no mínimo 1.")
        Integer prazoMeses,

        @NotNull(message = "tipoProduto é obrigatório.")
        TipoProduto tipoProduto
) {
}
