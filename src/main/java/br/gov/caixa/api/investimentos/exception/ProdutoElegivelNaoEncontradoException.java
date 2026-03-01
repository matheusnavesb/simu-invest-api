package br.gov.caixa.api.investimentos.exception;

public class ProdutoElegivelNaoEncontradoException extends RuntimeException {

    public ProdutoElegivelNaoEncontradoException(String message) {
        super(message);
    }
}
