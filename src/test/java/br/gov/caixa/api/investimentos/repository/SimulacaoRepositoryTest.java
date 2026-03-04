package br.gov.caixa.api.investimentos.repository;

import br.gov.caixa.api.investimentos.api.investimentos.domain.entity.Simulacao;
import br.gov.caixa.api.investimentos.api.investimentos.domain.enums.TipoProduto;
import br.gov.caixa.api.investimentos.api.investimentos.repository.SimulacaoRepository;
import io.quarkus.test.junit.QuarkusTest;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;
import io.quarkus.test.TestTransaction;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class SimulacaoRepositoryTest {

    @Inject
    SimulacaoRepository simulacaoRepository;

    private Simulacao criarSimulacao() {
        try {
            var ctor = Simulacao.class.getDeclaredConstructor();
            ctor.setAccessible(true);
            return ctor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Falha ao instanciar Simulacao via reflection", e);
        }
    }

    private static void setField(Object target, String fieldName, Object value) {
        try {
            Field f = null;
            Class<?> c = target.getClass();
            while (c != null) {
                try {
                    f = c.getDeclaredField(fieldName);
                    break;
                } catch (NoSuchFieldException ignored) {
                    c = c.getSuperclass();
                }
            }
            if (f == null) {
                throw new NoSuchFieldException("Campo não encontrado: " + fieldName
                        + " em " + target.getClass().getName());
            }
            f.setAccessible(true);
            f.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao setar campo '" + fieldName
                    + "' com valor do tipo " + (value == null ? "null" : value.getClass().getName()), e);
        }
    }

    @Test
    @TestTransaction
    void findByClienteId_deveRetornarListaVazia_quandoNaoHouverRegistros() {
        // opcional: evita flakiness se algum seed/execução anterior deixou dados
        // simulacaoRepository.deleteAll();

        List<Simulacao> lista = simulacaoRepository.findByClienteId(999L);
        assertNotNull(lista);
        assertTrue(lista.isEmpty());
    }

    @Test
    @TestTransaction
    void findByClienteId_deveFiltrarPorClienteId_eOrdenarPorDataDesc() {

        simulacaoRepository.deleteAll();

        Long clienteId = 123L;

        Simulacao sMaisAntiga = criarSimulacao();
        setField(sMaisAntiga, "clienteId", clienteId);
        setField(sMaisAntiga, "prazoMeses", 12);
        setField(sMaisAntiga, "valorInvestido", new BigDecimal("10000.00"));
        setField(sMaisAntiga, "valorFinal", new BigDecimal("11000.00"));
        setField(sMaisAntiga, "dataSimulacao", Instant.parse("2026-03-01T10:00:00Z").toEpochMilli());
        setField(sMaisAntiga, "produtoNome", "CDB Caixa 2026");
        setField(sMaisAntiga, "tipoProduto", TipoProduto.CDB);
        setField(sMaisAntiga, "rentabilidadeAplicada", new BigDecimal("0.12"));

        Simulacao sMaisNova = criarSimulacao();
        setField(sMaisNova, "clienteId", clienteId);
        setField(sMaisNova, "prazoMeses", 12);
        setField(sMaisNova, "valorInvestido", new BigDecimal("10000.00"));
        setField(sMaisNova, "valorFinal", new BigDecimal("12000.00"));
        setField(sMaisNova, "dataSimulacao", Instant.parse("2026-03-02T10:00:00Z").toEpochMilli());
        setField(sMaisNova, "produtoNome", "CDB Caixa 2026");
        setField(sMaisNova, "tipoProduto", TipoProduto.CDB);
        setField(sMaisNova, "rentabilidadeAplicada", new BigDecimal("0.12"));

        Simulacao sOutroCliente = criarSimulacao();
        setField(sOutroCliente, "clienteId", 777L);
        setField(sOutroCliente, "prazoMeses", 6);
        setField(sOutroCliente, "valorInvestido", new BigDecimal("8000.00"));
        setField(sOutroCliente, "valorFinal", new BigDecimal("9000.00"));
        setField(sOutroCliente, "dataSimulacao", Instant.parse("2026-03-03T10:00:00Z").toEpochMilli());
        setField(sOutroCliente, "produtoNome", "CDB Caixa 2026");
        setField(sOutroCliente, "tipoProduto", TipoProduto.CDB);
        setField(sOutroCliente, "rentabilidadeAplicada", new BigDecimal("0.12"));

        simulacaoRepository.persist(sMaisAntiga);
        simulacaoRepository.persist(sMaisNova);
        simulacaoRepository.persist(sOutroCliente);

        List<Simulacao> lista = simulacaoRepository.findByClienteId(clienteId);

        assertEquals(2, lista.size());
        assertEquals(Instant.parse("2026-03-02T10:00:00Z").toEpochMilli(), lista.get(0).getDataSimulacao());
        assertEquals(Instant.parse("2026-03-01T10:00:00Z").toEpochMilli(), lista.get(1).getDataSimulacao());
    }
}
