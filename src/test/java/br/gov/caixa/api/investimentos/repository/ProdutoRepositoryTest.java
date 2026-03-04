package br.gov.caixa.api.investimentos.repository;

import br.gov.caixa.api.investimentos.api.investimentos.domain.entity.Produto;
import br.gov.caixa.api.investimentos.api.investimentos.domain.enums.Risco;
import br.gov.caixa.api.investimentos.api.investimentos.domain.enums.TipoProduto;
import br.gov.caixa.api.investimentos.api.investimentos.repository.ProdutoRepository;
import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.TestTransaction;

@QuarkusTest
public class ProdutoRepositoryTest {

    @Inject
    ProdutoRepository produtoRepository;

    private Produto criarProduto() {
        try {
            var ctor = Produto.class.getDeclaredConstructor();
            ctor.setAccessible(true);
            return ctor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @TestTransaction
    void findElegivel_deveRetornarProduto() {

        Produto p = criarProduto();

        p.setNome("CDB Caixa 2026");
        p.setTipoProduto(TipoProduto.CDB);
        p.setValorMin(new BigDecimal("1000"));
        p.setValorMax(new BigDecimal("100000"));
        p.setPrazoMinMeses(6);
        p.setPrazoMaxMeses(36);
        p.setRentabilidadeAnual(new BigDecimal("0.12"));
        p.setRisco(Risco.BAIXO);

        produtoRepository.persist(p);

        var resultado = produtoRepository.findElegivel(
                TipoProduto.CDB,
                new BigDecimal("10000"),
                12
        );

        assertTrue(resultado.isPresent());
    }
}
