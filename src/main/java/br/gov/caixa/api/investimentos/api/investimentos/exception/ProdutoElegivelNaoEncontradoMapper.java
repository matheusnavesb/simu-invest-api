package br.gov.caixa.api.investimentos.api.investimentos.exception;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ProdutoElegivelNaoEncontradoMapper implements ExceptionMapper<ProdutoElegivelNaoEncontradoException> {

    @Override
    public Response toResponse(ProdutoElegivelNaoEncontradoException e) {
        return Response.status(422)
                .type(MediaType.APPLICATION_JSON)
                .entity(new ErrorResponse("PRODUTO_NAO_ELEGIVEL", e.getMessage()))
                .build();
    }

    public record ErrorResponse(String code, String message) {}
}
