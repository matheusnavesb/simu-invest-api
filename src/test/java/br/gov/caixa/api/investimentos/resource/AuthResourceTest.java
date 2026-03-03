package br.gov.caixa.api.investimentos.resource;

import br.gov.caixa.api.investimentos.profile.DevTestProfile;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
@QuarkusTest
@TestProfile(DevTestProfile.class)
public class AuthResourceTest {

    @Test
    void deveGerarTokenJwt() {
        given()
                .accept(ContentType.TEXT)
                .when()
                .get("/auth/token")
                .then()
                .statusCode(200)
                .body(not(isEmptyOrNullString()))
                // JWT tem 3 partes separadas por ponto
                .body(containsString("."))
                .body(matchesPattern("^[A-Za-z0-9\\-_]+\\.[A-Za-z0-9\\-_]+\\.[A-Za-z0-9\\-_]+$"));
    }
}
