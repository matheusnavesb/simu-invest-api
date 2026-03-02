package br.gov.caixa.api.investimentos.api.investimentos.resource;

import io.quarkus.arc.profile.IfBuildProfile;
import io.smallrye.jwt.build.Jwt;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.jwt.Claims;

import java.time.Duration;
import java.util.Set;

@Path("/auth")
@IfBuildProfile("dev") // só aparece em dev (quarkus:dev)
@Produces(MediaType.TEXT_PLAIN)
public class AuthResource {

    @GET
    @Path("/token")
    public String token() {
        return Jwt.issuer("simu-invest-api")
                .upn("dev-user")
                .claim(Claims.preferred_username.name(), "dev-user")
                .groups(Set.of("user"))
                .expiresIn(Duration.ofHours(2))
                .sign();
    }
}
