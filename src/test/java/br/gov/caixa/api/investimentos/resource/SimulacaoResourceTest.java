package br.gov.caixa.api.investimentos.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class SimulacaoResourceTest {

    @Test
    void deveCriarSimulacaoComProdutoElegivel() {
        given()
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
                .post("/simulacoes")
                .then()
                .statusCode(201)
                .body("produtoValidado", notNullValue())
                .body("produtoValidado.tipoProduto", equalTo("CDB"))
                .body("prazoMeses", equalTo(12))
                .body("valorFinal", equalTo(11268.25f))
                .body("dataSimulacao", not(isEmptyOrNullString()));
    }

    @Test
    void deveRetornar422QuandoNaoHouverProdutoElegivel() {
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("""
                {
                  "clienteId": 123,
                  "tipoProduto": "LCI",
                  "valor": 1000.00,
                  "prazoMeses": 12
                }
            """)
                .when()
                .post("/simulacoes")
                .then()
                .statusCode(422)
                .body("code", equalTo("PRODUTO_NAO_ELEGIVEL"));
    }
}
