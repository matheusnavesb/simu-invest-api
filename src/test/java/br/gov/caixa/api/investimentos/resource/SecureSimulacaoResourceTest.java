package br.gov.caixa.api.investimentos.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.smallrye.jwt.build.Jwt;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNot.not;

@QuarkusTest
public class SecureSimulacaoResourceTest {

    // 🔐 Gera token com role "user"
    private String tokenUser() {
        return Jwt.issuer("simu-invest-api")
                .upn("test-user")
                .groups(Set.of("user"))
                .expiresIn(Duration.ofHours(1))
                .sign();
    }

    @Test
    void deveCriarSimulacaoComTokenValido() {
        given()
                .header("Authorization", "Bearer " + tokenUser())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("""
                {
                  "clienteId": 123,
                  "tipoProduto": "CDB",
                  "valor": 10000.00,
                  "prazoMeses": 12
                }
            """)
                .when()
                .post("/secure/simulacoes")
                .then()
                .statusCode(201)
                .body("produtoValidado", notNullValue())
                .body("produtoValidado.tipoProduto", equalTo("CDB"))
                .body("valorFinal", notNullValue())
                .body("dataSimulacao", notNullValue());
    }

    @Test
    void deveRetornar422QuandoProdutoNaoElegivel() {
        given()
                .header("Authorization", "Bearer " + tokenUser())
                .contentType(ContentType.JSON)
                .body("""
                {
                  "clienteId": 123,
                  "tipoProduto": "LCI",
                  "valor": 1000.00,
                  "prazoMeses": 12
                }
            """)
                .when()
                .post("/secure/simulacoes")
                .then()
                .statusCode(422)
                .body("code", equalTo("PRODUTO_NAO_ELEGIVEL"));
    }

    @Test
    void deveRetornar400QuandoClienteIdAusenteNoGet() {
        given()
                .header("Authorization", "Bearer " + tokenUser())
                .when()
                .get("/secure/simulacoes")
                .then()
                .statusCode(400);
    }

    @Test
    void deveRetornar401QuandoNaoEnviarToken() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                {
                  "clienteId": 123,
                  "tipoProduto": "CDB",
                  "valor": 10000.00,
                  "prazoMeses": 12
                }
            """)
                .when()
                .post("/secure/simulacoes")
                .then()
                .statusCode(401);
    }

    @Test
    void deveRetornarHistoricoQuandoClienteIdInformado() {
        String token = tokenUser();

        // 1) Cria uma simulação para garantir que exista histórico
        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("""
            {
              "clienteId": 123,
              "tipoProduto": "CDB",
              "valor": 10000.00,
              "prazoMeses": 12
            }
        """)
                .when()
                .post("/secure/simulacoes")
                .then()
                .statusCode(201);

        // 2) Busca o histórico do cliente
        given()
                .header("Authorization", "Bearer " + token)
                .accept(ContentType.JSON)
                .when()
                .get("/secure/simulacoes?clienteId=123")
                .then()
                .statusCode(200)
                .body("$", not(empty()))
                .body("[0].clienteId", equalTo(123))
                .body("[0].produto", not(isEmptyOrNullString()))
                .body("[0].tipoProduto", equalTo("CDB"))
                .body("[0].valorInvestido", notNullValue())
                .body("[0].valorFinal", notNullValue())
                .body("[0].prazoMeses", equalTo(12))
                .body("[0].dataSimulacao", not(isEmptyOrNullString()));
    }
}
