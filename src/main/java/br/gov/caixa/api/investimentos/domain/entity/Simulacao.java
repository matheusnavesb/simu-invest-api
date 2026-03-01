package br.gov.caixa.api.investimentos.domain.entity;

import br.gov.caixa.api.investimentos.domain.enums.TipoProduto;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "simulacoes")
public class Simulacao extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    @Column(name = "produto_nome", nullable = false)
    private String produtoNome;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_produto", nullable = false)
    private TipoProduto tipoProduto;

    @Column(name = "valor_investido", nullable = false, precision = 18, scale = 2)
    private BigDecimal valorInvestido;

    @Column(name = "prazo_meses", nullable = false)
    private Integer prazoMeses;

    @Column(name = "rentabilidade_aplicada", nullable = false, precision = 12, scale = 8)
    private BigDecimal rentabilidadeAplicada;

    @Column(name = "valor_final", nullable = false, precision = 18, scale = 2)
    private BigDecimal valorFinal;

    @Column(name = "data_simulacao", nullable = false)
    private Instant dataSimulacao;

    protected Simulacao() {
    } // JPA

    public Long getId() {
        return id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public String getProdutoNome() {
        return produtoNome;
    }

    public TipoProduto getTipoProduto() {
        return tipoProduto;
    }

    public BigDecimal getValorInvestido() {
        return valorInvestido;
    }

    public Integer getPrazoMeses() {
        return prazoMeses;
    }

    public BigDecimal getRentabilidadeAplicada() {
        return rentabilidadeAplicada;
    }

    public BigDecimal getValorFinal() {
        return valorFinal;
    }

    public Instant getDataSimulacao() {
        return dataSimulacao;
    }

    public static Simulacao criar(
            Long clienteId,
            String produtoNome,
            TipoProduto tipoProduto,
            BigDecimal valorInvestido,
            Integer prazoMeses,
            BigDecimal rentabilidadeAplicada,
            BigDecimal valorFinal,
            Instant dataSimulacao) {

        Simulacao s = new Simulacao();
        s.clienteId = clienteId;
        s.produtoNome = produtoNome;
        s.tipoProduto = tipoProduto;
        s.valorInvestido = valorInvestido;
        s.prazoMeses = prazoMeses;
        s.rentabilidadeAplicada = rentabilidadeAplicada;
        s.valorFinal = valorFinal;
        s.dataSimulacao = dataSimulacao;
        return s;
    }
}