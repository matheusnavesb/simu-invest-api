package br.gov.caixa.api.investimentos.service;

import br.gov.caixa.api.investimentos.api.investimentos.service.SimulacaoCalculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class SimulacaoCalculatorTest {

    @Test
    void deveCalcularValorFinalComJurosCompostosMensal() {
        SimulacaoCalculator calculator = new SimulacaoCalculator();

        BigDecimal valor = new BigDecimal("10000.00");
        BigDecimal rentabilidadeAnual = new BigDecimal("0.12");
        int prazoMeses = 12;

        BigDecimal valorFinal = calculator.calcularValorFinal(valor, rentabilidadeAnual, prazoMeses);

        // 10000 * (1 + 0.12/12)^12 = 11268.25 (arredondado para 2 casas)
        assertEquals(new BigDecimal("11268.25"), valorFinal);
    }
}
