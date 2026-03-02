package br.gov.caixa.api.investimentos.api.investimentos.domain.entity;

import br.gov.caixa.api.investimentos.api.investimentos.domain.enums.Risco;
import br.gov.caixa.api.investimentos.api.investimentos.domain.enums.TipoProduto;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "produtos")
public class Produto extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_produto", nullable = false)
    private TipoProduto tipoProduto;

    @Column(name = "rentabilidade_anual", nullable = false, precision = 12, scale = 8)
    private BigDecimal rentabilidadeAnual;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Risco risco;

    @Column(name = "prazo_min_meses", nullable = false)
    private Integer prazoMinMeses;

    @Column(name = "prazo_max_meses", nullable = false)
    private Integer prazoMaxMeses;

    @Column(name = "valor_min", nullable = false, precision = 18, scale = 2)
    private BigDecimal valorMin;

    @Column(name = "valor_max", nullable = false, precision = 18, scale = 2)
    private BigDecimal valorMax;

    protected Produto() {
    } // JPA

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public TipoProduto getTipoProduto() {
        return tipoProduto;
    }

    public BigDecimal getRentabilidadeAnual() {
        return rentabilidadeAnual;
    }

    public Risco getRisco() {
        return risco;
    }

    public Integer getPrazoMinMeses() {
        return prazoMinMeses;
    }

    public Integer getPrazoMaxMeses() {
        return prazoMaxMeses;
    }

    public BigDecimal getValorMin() {
        return valorMin;
    }

    public BigDecimal getValorMax() {
        return valorMax;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTipoProduto(TipoProduto tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    public void setRentabilidadeAnual(BigDecimal rentabilidadeAnual) {
        this.rentabilidadeAnual = rentabilidadeAnual;
    }

    public void setRisco(Risco risco) {
        this.risco = risco;
    }

    public void setPrazoMinMeses(Integer prazoMinMeses) {
        this.prazoMinMeses = prazoMinMeses;
    }

    public void setPrazoMaxMeses(Integer prazoMaxMeses) {
        this.prazoMaxMeses = prazoMaxMeses;
    }

    public void setValorMin(BigDecimal valorMin) {
        this.valorMin = valorMin;
    }

    public void setValorMax(BigDecimal valorMax) {
        this.valorMax = valorMax;
    }
}