package testSuites;

import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.Test;
import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

@Feature("Get Single User")
public class ReqResSingleUserTest {

    private JSONObject jsonObject;

    @Before
    public void setup() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.requestSpecification = new RequestSpecBuilder().
                setContentType(ContentType.JSON).
                build();
        this.jsonObject = new JSONObject();
    }

    @Test
    @DisplayName("Test Obtener Usuario")
    public void getSingleUserTest() {
        given().
        get("users/1").
        then().
        statusCode(HttpStatus.SC_OK).
        body("data", notNullValue()).
        body("support", notNullValue());
    }

    @Test
    @DisplayName("Test Obtener Usuario No Encontrado")
    public void getSingleUserNotFoundTest() {
        given().
        get("users/10000").
        then().
        statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    @DisplayName("Test Obtener Usuario Validar Campos")
    public void validFieldsResponseCorrectTest() {
        given().
        get("users/1").
        then().
        statusCode(HttpStatus.SC_OK).
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
            given().
            get("users/1").
            then().
            body(
                JsonSchemaValidator.
                    matchesJsonSchema(
                            new File("src/test/resources/json/singleUser.json")
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
            given().
            get("users/10000").
            then().
            statusCode(HttpStatus.SC_NOT_FOUND).
            body(
                JsonSchemaValidator.
                    matchesJsonSchema(
                            new File("src/test/resources/json/empty.json")
                    )
            );
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
