package br.gov.caixa.api.investimentos.api.investimentos.service;

import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.math.RoundingMode;

@ApplicationScoped
public class SimulacaoCalculator {


    public BigDecimal calcularValorFinal(BigDecimal valor, BigDecimal rentabilidadeAnual, int prazoMeses) {
        BigDecimal taxaMensal = rentabilidadeAnual.divide(BigDecimal.valueOf(12), 16, RoundingMode.HALF_UP);
        BigDecimal fator = BigDecimal.ONE.add(taxaMensal);

        BigDecimal acumulado = BigDecimal.ONE;
        for (int i = 0; i < prazoMeses; i++) {
            acumulado = acumulado.multiply(fator);
        }

        return valor.multiply(acumulado).setScale(2, RoundingMode.HALF_UP);
    }
}