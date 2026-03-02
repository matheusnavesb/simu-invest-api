package br.gov.caixa.api.investimentos.api.investimentos.repository;

import br.gov.caixa.api.investimentos.api.investimentos.domain.entity.Produto;
import br.gov.caixa.api.investimentos.api.investimentos.domain.enums.TipoProduto;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.util.Optional;

@ApplicationScoped
public class ProdutoRepository implements PanacheRepository<Produto> {

    public Optional<Produto> findElegivel(TipoProduto tipoProduto, BigDecimal valor, int prazoMeses) {
        return find("""
            tipoProduto = ?1
            and ?2 between valorMin and valorMax
            and ?3 between prazoMinMeses and prazoMaxMeses
            """, tipoProduto, valor, prazoMeses)
                .firstResultOptional();
    }
}
