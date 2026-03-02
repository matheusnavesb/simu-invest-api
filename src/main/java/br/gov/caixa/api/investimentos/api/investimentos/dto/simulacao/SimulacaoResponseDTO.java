package br.gov.caixa.api.investimentos.api.investimentos.dto.simulacao;

import br.gov.caixa.api.investimentos.api.investimentos.domain.entity.Produto;
import br.gov.caixa.api.investimentos.api.investimentos.dto.produto.ProdutoResponseDTO;

import java.math.BigDecimal;
import java.time.Instant;

public record SimulacaoResponseDTO(
        ProdutoResponseDTO produtoValidado,
        BigDecimal valorFinal,
        Integer prazoMeses,
        Instant dataSimulacao
) {
    public static SimulacaoResponseDTO of(Produto produto, BigDecimal valorFinal, Integer prazoMeses, Instant dataSimulacao) {
        return new SimulacaoResponseDTO(
                ProdutoResponseDTO.valueOf(produto),
                valorFinal,
                prazoMeses,
                dataSimulacao
        );
    }

    public record ResultadoSimulacaoDTO(BigDecimal valorFinal, Integer prazoMeses) {
    }
}
