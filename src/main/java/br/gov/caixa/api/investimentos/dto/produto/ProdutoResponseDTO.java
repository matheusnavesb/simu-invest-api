package br.gov.caixa.api.investimentos.dto.produto;

import br.gov.caixa.api.investimentos.domain.entity.Produto;
import br.gov.caixa.api.investimentos.domain.enums.Risco;
import br.gov.caixa.api.investimentos.domain.enums.TipoProduto;

import java.math.BigDecimal;

public record ProdutoResponseDTO(
        Long id,
        String nome,
        TipoProduto tipoProduto,
        BigDecimal rentabilidadeAnual,
        Risco risco,
        Integer prazoMinMeses,
        Integer prazoMaxMeses,
        BigDecimal valorMin,
        BigDecimal valorMax
) {
    public static ProdutoResponseDTO valueOf(Produto p) {
        return new ProdutoResponseDTO(
                p.getId(),
                p.getNome(),
                p.getTipoProduto(),
                p.getRentabilidadeAnual(),
                p.getRisco(),
                p.getPrazoMinMeses(),
                p.getPrazoMaxMeses(),
                p.getValorMin(),
                p.getValorMax()
        );
    }
}
