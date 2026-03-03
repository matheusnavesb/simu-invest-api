package br.gov.caixa.api.investimentos.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class ProdutoDebugResourceTest {

    @Test
    void deveListarProdutosDoSeed() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get("/debug/produtos")
                .then()
                .statusCode(200)
                .body("$", not(empty()))
                .body("size()", greaterThanOrEqualTo(1))
                .body("[0].id", notNullValue())
                .body("[0].nome", not(isEmptyOrNullString()))
                .body("[0].tipoProduto", not(isEmptyOrNullString()))
                .body("[0].rentabilidadeAnual", notNullValue())
                .body("[0].risco", not(isEmptyOrNullString()));
    }
}
