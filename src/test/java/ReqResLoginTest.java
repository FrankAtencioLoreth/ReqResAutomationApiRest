import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


public class ReqResLoginTest {

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
    @DisplayName("Test Login Correcto")
    public void loginSuccessful() {
        given().
        body(new File("src/test/json/dataLogin.json")).
        post("login").
        then().
        statusCode(HttpStatus.SC_OK).
        body("token", notNullValue());
    }

    @Test
    @DisplayName("Test Login Correcto Validar Campos")
    public void validateFieldsloginSuccessful() {
        this.jsonObject.put("email", "eve.holt@reqres.in");
        this.jsonObject.put("password", "cityslicka");

        given().
        body(this.jsonObject.toString()). //new File("file.json")
        post("login").
        then().
        statusCode(HttpStatus.SC_OK).
        body("token", equalTo("QpwL5tke4Pnpja7X4"));
    }

    @Test
    @DisplayName("Test Login Correcto Validar Esquema")
    public void validateSchemaLoginSuceesful() {
        this.jsonObject.put("email", "eve.holt@reqres.in");
        this.jsonObject.put("password", "cityslicka");

        try {
            given().
            body(this.jsonObject.toString()).
            post("login").
            then().
            statusCode(HttpStatus.SC_OK).
            body(
                JsonSchemaValidator.
                    matchesJsonSchema(
                            new File("src/test/json/loginSuccessful.json")
                    )
            );
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("Test Login Incorrecto")
    public void loginUnSuceesful() {
        this.jsonObject.put("email", "eve.holt@reqres.in");

        given().
        body(this.jsonObject.toString()).
        post("login").
        then().
        statusCode(HttpStatus.SC_BAD_REQUEST).
        body("error", notNullValue());
    }

    @Test
    @DisplayName("Test Login Incorrecto Contraseña Validar Campos")
    public void validateFieldsloginUnSuccessfulPassword() {
        this.jsonObject.put("email", "eve.holt@reqres.in");

        given().
        body(this.jsonObject.toString()).
        post("login").
        then().
        statusCode(HttpStatus.SC_BAD_REQUEST).
        body("error", equalTo("Missing password"));
    }

    @Test
    @DisplayName("Test Login Incorrecto Email Validar Campos")
    public void validateFieldsloginUnSuccessfulEmail() {
        this.jsonObject.put("password", "cityslicka");

        given().
        body(this.jsonObject.toString()).
        post("login").
        then().
        statusCode(HttpStatus.SC_BAD_REQUEST).
        body("error", equalTo("Missing email or username"));
    }

    @Test
    @DisplayName("Test Login Incorrecto Validar Esquema")
    public void validateSchemaLoginSuccessful() {
        this.jsonObject.put("email", "eve.holt@reqres.in");

        try {
            given().
            body(this.jsonObject.toString()).
            post("login").
            then().
            statusCode(HttpStatus.SC_BAD_REQUEST).
            body(
                JsonSchemaValidator.
                    matchesJsonSchema(
                            new File("src/test/json/loginUnSuccessful.json")
                    )
            );
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
