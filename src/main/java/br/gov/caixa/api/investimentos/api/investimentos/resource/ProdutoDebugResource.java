package br.gov.caixa.api.investimentos.api.investimentos.resource;

import br.gov.caixa.api.investimentos.api.investimentos.dto.produto.ProdutoResponseDTO;
import br.gov.caixa.api.investimentos.api.investimentos.repository.ProdutoRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/debug/produtos")
@Produces(MediaType.APPLICATION_JSON)
public class ProdutoDebugResource {

    @Inject
    ProdutoRepository produtoRepository;

    @GET
    public List<ProdutoResponseDTO> listar() {
        return produtoRepository.listAll()
                .stream()
                .map(ProdutoResponseDTO::valueOf)
                .toList();
    }
}
