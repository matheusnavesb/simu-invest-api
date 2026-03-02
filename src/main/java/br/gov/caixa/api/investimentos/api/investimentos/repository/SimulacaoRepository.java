package br.gov.caixa.api.investimentos.api.investimentos.repository;

import br.gov.caixa.api.investimentos.api.investimentos.domain.entity.Simulacao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class SimulacaoRepository implements PanacheRepository<Simulacao> {

    public List<Simulacao> findByClienteId(Long clienteId) {
        return find("clienteId = ?1 order by dataSimulacao desc", clienteId).list();
    }
}