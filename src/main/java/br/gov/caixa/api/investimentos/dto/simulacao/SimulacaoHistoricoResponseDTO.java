package br.gov.caixa.api.investimentos.dto.simulacao;

import br.gov.caixa.api.investimentos.domain.entity.Simulacao;
import br.gov.caixa.api.investimentos.domain.enums.TipoProduto;

import java.math.BigDecimal;
import java.time.Instant;

public record SimulacaoHistoricoResponseDTO(
        Long id,
        Long clienteId,
        String produto,
        TipoProduto tipoProduto,
        BigDecimal valorInvestido,
        BigDecimal valorFinal,
        Integer prazoMeses,
        Instant dataSimulacao
) {
    public static SimulacaoHistoricoResponseDTO valueOf(Simulacao simulacao) {
        return new SimulacaoHistoricoResponseDTO(
                simulacao.getId(),
                simulacao.getClienteId(),
                simulacao.getProdutoNome(),
                simulacao.getTipoProduto(),
                simulacao.getValorInvestido(),
                simulacao.getValorFinal(),
                simulacao.getPrazoMeses(),
                simulacao.getDataSimulacao()
        );
    }
}
