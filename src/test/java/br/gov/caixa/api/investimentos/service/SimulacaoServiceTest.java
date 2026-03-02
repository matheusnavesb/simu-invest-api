package br.gov.caixa.api.investimentos.service;

import br.gov.caixa.api.investimentos.api.investimentos.domain.entity.Produto;
import br.gov.caixa.api.investimentos.api.investimentos.domain.entity.Simulacao;
import br.gov.caixa.api.investimentos.api.investimentos.domain.enums.Risco;
import br.gov.caixa.api.investimentos.api.investimentos.domain.enums.TipoProduto;
import br.gov.caixa.api.investimentos.api.investimentos.dto.simulacao.SimulacaoDTO;
import br.gov.caixa.api.investimentos.api.investimentos.dto.simulacao.SimulacaoResponseDTO;
import br.gov.caixa.api.investimentos.api.investimentos.exception.ProdutoElegivelNaoEncontradoException;
import br.gov.caixa.api.investimentos.api.investimentos.repository.ProdutoRepository;
import br.gov.caixa.api.investimentos.api.investimentos.repository.SimulacaoRepository;
import br.gov.caixa.api.investimentos.api.investimentos.service.SimulacaoCalculator;
import br.gov.caixa.api.investimentos.api.investimentos.service.SimulacaoServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SimulacaoServiceTest {

    @Mock
    ProdutoRepository produtoRepository;

    @Mock
    SimulacaoRepository simulacaoRepository;

    @Mock
    SimulacaoCalculator calculator;

    @InjectMocks
    SimulacaoServiceImpl service;

    @Test
    void criarSimulacao_devePersistirERetornarResponse() {
        // Arrange
        SimulacaoDTO dto = new SimulacaoDTO(
                123L,
                new BigDecimal("10000.00"),
                12,
                TipoProduto.CDB
        );

        Produto produto = mock(Produto.class);
        when(produto.getId()).thenReturn(1L);
        when(produto.getNome()).thenReturn("CDB Caixa 2026");
        when(produto.getTipoProduto()).thenReturn(TipoProduto.CDB);
        when(produto.getRentabilidadeAnual()).thenReturn(new BigDecimal("0.12"));
        when(produto.getRisco()).thenReturn(Risco.BAIXO);
        when(produto.getPrazoMinMeses()).thenReturn(6);
        when(produto.getPrazoMaxMeses()).thenReturn(36);
        when(produto.getValorMin()).thenReturn(new BigDecimal("1000.00"));
        when(produto.getValorMax()).thenReturn(new BigDecimal("100000.00"));

        BigDecimal valorFinalEsperado = new BigDecimal("11268.25");

        when(produtoRepository.findElegivel(dto.tipoProduto(), dto.valor(), dto.prazoMeses()))
                .thenReturn(Optional.of(produto));

        when(calculator.calcularValorFinal(dto.valor(), produto.getRentabilidadeAnual(), dto.prazoMeses()))
                .thenReturn(valorFinalEsperado);

        // Act
        SimulacaoResponseDTO response = service.criarSimulacao(dto);

        // Assert (response)
        assertNotNull(response);
        assertEquals(12, response.prazoMeses());
        assertEquals(valorFinalEsperado, response.valorFinal());
        assertNotNull(response.dataSimulacao());
        Assertions.assertEquals("CDB Caixa 2026", response.produtoValidado().nome());

        // Assert (persistência)
        ArgumentCaptor<Simulacao> captor = ArgumentCaptor.forClass(Simulacao.class);
        verify(simulacaoRepository, times(1)).persist(captor.capture());

        Simulacao simulacaoPersistida = captor.getValue();
        assertEquals(123L, simulacaoPersistida.getClienteId());
        assertEquals("CDB Caixa 2026", simulacaoPersistida.getProdutoNome());
        assertEquals(TipoProduto.CDB, simulacaoPersistida.getTipoProduto());
        assertEquals(new BigDecimal("10000.00"), simulacaoPersistida.getValorInvestido());
        assertEquals(12, simulacaoPersistida.getPrazoMeses());
        assertEquals(new BigDecimal("0.12"), simulacaoPersistida.getRentabilidadeAplicada());
        assertEquals(valorFinalEsperado, simulacaoPersistida.getValorFinal());

        // data_simulacao é epoch millis (Long)
        assertTrue(simulacaoPersistida.getDataSimulacao() > 0);

        // Sanidade: dataSimulacao do response bate com o epoch persistido (tolerância pequena)
        long epochFromResponse = response.dataSimulacao().toEpochMilli();
        long epochPersistido = simulacaoPersistida.getDataSimulacao();
        assertTrue(Math.abs(epochFromResponse - epochPersistido) < 2000);
    }

    @Test
    void criarSimulacao_semProdutoElegivel_deveLancarException() {
        // Arrange
        SimulacaoDTO dto = new SimulacaoDTO(
                        123L,
                new BigDecimal("1000.00"),
                12,
                TipoProduto.LCI
                );

        when(produtoRepository.findElegivel(dto.tipoProduto(), dto.valor(), dto.prazoMeses()))
                .thenReturn(Optional.empty());

        // Act + Assert
        ProdutoElegivelNaoEncontradoException ex =
                assertThrows(ProdutoElegivelNaoEncontradoException.class, () -> service.criarSimulacao(dto));

        assertTrue(ex.getMessage().contains("Não há produto elegível"));

        verifyNoInteractions(calculator);
        verify(simulacaoRepository, never()).persist((Simulacao) any());
    }
}