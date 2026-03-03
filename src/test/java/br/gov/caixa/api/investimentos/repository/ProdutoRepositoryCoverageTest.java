package br.gov.caixa.api.investimentos.repository;

import br.gov.caixa.api.investimentos.api.investimentos.domain.entity.Produto;
import br.gov.caixa.api.investimentos.api.investimentos.domain.enums.Risco;
import br.gov.caixa.api.investimentos.api.investimentos.domain.enums.TipoProduto;
import br.gov.caixa.api.investimentos.api.investimentos.repository.ProdutoRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class ProdutoRepositoryCoverageTest {

    @Inject
    ProdutoRepository produtoRepository;

    @Test
    @Transactional
    void deveExecutarFindElegivelECobrirRepositorio() {
        // DEBUG: confirma qual classe foi injetada (package duplicado aparece aqui)
        System.out.println("Injected repo class: " + produtoRepository.getClass().getName());

        produtoRepository.deleteAll();

        Produto p = novoProduto();
        p.setNome("CDB Cobertura");
        p.setTipoProduto(TipoProduto.CDB);
        p.setRentabilidadeAnual(new BigDecimal("0.12"));
        p.setRisco(Risco.BAIXO);
        p.setPrazoMinMeses(6);
        p.setPrazoMaxMeses(36);
        p.setValorMin(new BigDecimal("1000.00"));
        p.setValorMax(new BigDecimal("100000.00"));

        produtoRepository.persist(p);

        var opt = produtoRepository.findElegivel(
                TipoProduto.CDB,
                new BigDecimal("10000.00"),
                12
        );

        assertTrue(opt.isPresent(), "Era para retornar produto elegível");
        assertEquals("CDB Cobertura", opt.get().getNome());
    }

    private Produto novoProduto() {
        try {
            Constructor<Produto> ctor = Produto.class.getDeclaredConstructor();
            ctor.setAccessible(true);
            return ctor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
