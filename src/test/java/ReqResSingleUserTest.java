import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.File;

import static org.hamcrest.Matchers.*;

public class ReqResSingleUserTest extends BaseTest{

    @Test
    @DisplayName("Test Obtener Usuario")
    public void getSingleUserTest() {
        RestAssured.
                given().
                log().all().
                contentType(ContentType.JSON).
                get(this.baseURI+"users/1").
                then().
                log().all().
                statusCode(this.STATUS_CODE_OK).
                body("data", notNullValue()).
                body("support", notNullValue());
    }

    @Test
    @DisplayName("Test Obtener Usuario No Encontrado")
    public void getSingleUserNotFoundTest() {
        RestAssured.
                given().
                log().all().
                contentType(ContentType.JSON).
                get(this.baseURI+"users/10000").
                then().
                log().all().
                statusCode(this.STATUS_CODE_NOT_FOUND);
    }

    @Test
    @DisplayName("Test Obtener Usuario Validar Campos")
    public void validFieldsResponseCorrectTest() {
        RestAssured.
                given().
                log().all().
                contentType(ContentType.JSON).
                get(this.baseURI+"users/1").
                then().
                log().all().
                statusCode(this.STATUS_CODE_OK).
                body("data", notNullValue()).
                body("data.id", equalTo(1)).
                body("data.email", equalTo("george.bluth@reqres.in")).
                body("data.first_name", equalTo("George")).
                body("data.last_name", equalTo("Bluth")).
                body("data.avatar", equalTo("https://reqres.in/img/faces/1-image.jpg")).
                body("support", notNullValue()).
                body("support.url", equalTo("https://reqres.in/#support-heading")).
                body("support.text", equalTo("To keep ReqRes free, contributions towards server costs are appreciated!"));
    }

    @Test
    @DisplayName("Test Obtener Usuario Validar Esquema")
    public void validSchemaSingleUserResponseCorrect() {

        try {
            RestAssured.
                    given().
                    log().all().
                    contentType(ContentType.JSON).
                    get(this.baseURI+"users/1").
                    then().
                    log().all().
                    body(
                            JsonSchemaValidator.
                                    matchesJsonSchema(
                                            new File("src/test/json/singleUser.json")
                                    )
                    );
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    @Test
    @DisplayName("Test Obtener Usuario No Encontrado Validar Esquema")
    public void validSchemaSingleUserResponseNotFound() {

        try {
            RestAssured.
                    given().
                    log().all().
                    contentType(ContentType.JSON).
                    get(this.baseURI+"users/10000").
                    then().
                    log().all().
                    statusCode(this.STATUS_CODE_NOT_FOUND).
                    body(
                            JsonSchemaValidator.
                                    matchesJsonSchema(
                                            new File("src/test/json/empty.json")
                                    )
                    );
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
}
