package br.gov.caixa.api.investimentos.dto.produto;

import br.gov.caixa.api.investimentos.domain.enums.Risco;
import br.gov.caixa.api.investimentos.domain.enums.TipoProduto;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProdutoDTO(
        @NotBlank(message = "nome é obrigatório.")
        @Size(max = 60, message = "nome deve ter no máximo 60 caracteres.")
        String nome,

        @NotNull(message = "tipoProduto é obrigatório.")
        TipoProduto tipoProduto,

        @NotNull(message = "rentabilidadeAnual é obrigatória.")
        @DecimalMin(value = "0.0", inclusive = false, message = "rentabilidadeAnual deve ser > 0.")
        BigDecimal rentabilidadeAnual,

        @NotNull(message = "risco é obrigatório.")
        Risco risco,

        @NotNull(message = "prazoMinMeses é obrigatório.")
        @Min(value = 1, message = "prazoMinMeses deve ser >= 1.")
        Integer prazoMinMeses,

        @NotNull(message = "prazoMaxMeses é obrigatório.")
        @Min(value = 1, message = "prazoMaxMeses deve ser >= 1.")
        Integer prazoMaxMeses,

        @NotNull(message = "valorMin é obrigatório.")
        @DecimalMin(value = "0.0", inclusive = false, message = "valorMin deve ser > 0.")
        BigDecimal valorMin,

        @NotNull(message = "valorMax é obrigatório.")
        @DecimalMin(value = "0.0", inclusive = false, message = "valorMax deve ser > 0.")
        BigDecimal valorMax
) {
}
