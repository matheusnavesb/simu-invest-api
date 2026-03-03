package br.gov.caixa.api.investimentos.api.investimentos.resource;

import br.gov.caixa.api.investimentos.api.investimentos.dto.simulacao.SimulacaoDTO;
import br.gov.caixa.api.investimentos.api.investimentos.dto.simulacao.SimulacaoHistoricoResponseDTO;
import br.gov.caixa.api.investimentos.api.investimentos.dto.simulacao.SimulacaoResponseDTO;
import br.gov.caixa.api.investimentos.api.investimentos.service.SimulacaoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/simulacoes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
//@RolesAllowed("user")
public class SimulacaoResource {

    @Inject
    SimulacaoService simulacaoService;

    @POST
    public Response criar(@Valid SimulacaoDTO dto) {
        SimulacaoResponseDTO response = simulacaoService.criarSimulacao(dto);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @GET
    public List<SimulacaoHistoricoResponseDTO> historico(@QueryParam("clienteId") Long clienteId) {
        if (clienteId == null) {
            throw new BadRequestException("clienteId é obrigatório.");
        }
        return simulacaoService.historico(clienteId);
    }
}
