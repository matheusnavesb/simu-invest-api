package br.gov.caixa.api.investimentos.api.investimentos.service;

import br.gov.caixa.api.investimentos.api.investimentos.domain.entity.Produto;
import br.gov.caixa.api.investimentos.api.investimentos.domain.entity.Simulacao;
import br.gov.caixa.api.investimentos.api.investimentos.dto.simulacao.SimulacaoDTO;
import br.gov.caixa.api.investimentos.api.investimentos.dto.simulacao.SimulacaoHistoricoResponseDTO;
import br.gov.caixa.api.investimentos.api.investimentos.dto.simulacao.SimulacaoResponseDTO;
import br.gov.caixa.api.investimentos.api.investimentos.exception.ProdutoElegivelNaoEncontradoException;
import br.gov.caixa.api.investimentos.api.investimentos.repository.ProdutoRepository;
import br.gov.caixa.api.investimentos.api.investimentos.repository.SimulacaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@ApplicationScoped
public class SimulacaoServiceImpl implements SimulacaoService{

    @Inject
    ProdutoRepository produtoRepository;

    @Inject
    SimulacaoRepository simulacaoRepository;

    @Inject
    SimulacaoCalculator calculator;

    @Override
    @Transactional
    public SimulacaoResponseDTO criarSimulacao(SimulacaoDTO dto) {

        // 1️⃣ Buscar produto elegível
        Produto produto = produtoRepository
                .findElegivel(dto.tipoProduto(), dto.valor(), dto.prazoMeses())
                .orElseThrow(() ->
                        new ProdutoElegivelNaoEncontradoException(
                                "Não há produto elegível para os parâmetros informados."
                        )
                );

        // 2️⃣ Calcular valor final
        BigDecimal valorFinal = calculator.calcularValorFinal(
                dto.valor(),
                produto.getRentabilidadeAnual(),
                dto.prazoMeses()
        );

//        Instant agora = Instant.now();
        Long agora = Instant.now().toEpochMilli();

        // 3️⃣ Persistir simulação
        Simulacao simulacao = Simulacao.criar(
                dto.clienteId(),
                produto.getNome(),
                produto.getTipoProduto(),
                dto.valor(),
                dto.prazoMeses(),
                produto.getRentabilidadeAnual(),
                valorFinal,
                agora
        );

        simulacaoRepository.persist(simulacao);

        // 4️⃣ Retornar DTO estruturado conforme enunciado
        return SimulacaoResponseDTO.of(
                produto,
                valorFinal,
                dto.prazoMeses(),
                //agora
                Instant.ofEpochMilli(agora)
        );
    }

    @Override
    public List<SimulacaoHistoricoResponseDTO> historico(Long clienteId) {

        return simulacaoRepository
                .findByClienteId(clienteId)
                .stream()
                .map(SimulacaoHistoricoResponseDTO::valueOf)
                .toList();
    }

//    // 🔢 Regra de negócio (cálculo composto mensal)
//    private BigDecimal calcularValorFinal(BigDecimal valor,
//                                          BigDecimal rentabilidadeAnual,
//                                          int prazoMeses) {
//
//        // taxaMensal = rentabilidadeAnual / 12
//        BigDecimal taxaMensal = rentabilidadeAnual
//                .divide(BigDecimal.valueOf(12), 16, RoundingMode.HALF_UP);
//
//        BigDecimal fator = BigDecimal.ONE.add(taxaMensal);
//
//        BigDecimal acumulado = BigDecimal.ONE;
//
//        for (int i = 0; i < prazoMeses; i++) {
//            acumulado = acumulado.multiply(fator);
//        }
//
//        return valor
//                .multiply(acumulado)
//                .setScale(2, RoundingMode.HALF_UP);
//    }
}