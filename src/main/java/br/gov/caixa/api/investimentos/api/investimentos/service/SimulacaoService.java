package br.gov.caixa.api.investimentos.api.investimentos.service;

import br.gov.caixa.api.investimentos.api.investimentos.dto.simulacao.SimulacaoDTO;
import br.gov.caixa.api.investimentos.api.investimentos.dto.simulacao.SimulacaoHistoricoResponseDTO;
import br.gov.caixa.api.investimentos.api.investimentos.dto.simulacao.SimulacaoResponseDTO;

import java.util.List;

public interface SimulacaoService {

    SimulacaoResponseDTO criarSimulacao(SimulacaoDTO dto);

    List<SimulacaoHistoricoResponseDTO> historico(Long clienteId);
}
