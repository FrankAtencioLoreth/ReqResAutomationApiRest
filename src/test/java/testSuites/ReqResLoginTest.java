package testSuites;

import io.qameta.allure.Feature;
import io.qameta.allure.Severity;;
import io.qameta.allure.SeverityLevel;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@Feature("Login User")
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
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Test Login Correcto")
    public void loginSuccessful() {
        given().
        body(new File("src/test/resources/json/dataLogin.json")).
        post("login").
        then().
        statusCode(HttpStatus.SC_OK).
        body("token", notNullValue());

        //Get token
        Response body = given().
                body(new File("src/test/resources/json/dataLogin.json")).
                post("login");
        String token = body.path("token").toString();
        System.out.println("TOKEN = " + token);
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
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
    @Severity(SeverityLevel.TRIVIAL)
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
                            new File("src/test/resources/json/loginSuccessful.json")
                    )
            );
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
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
    @Severity(SeverityLevel.NORMAL)
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
    @Severity(SeverityLevel.NORMAL)
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
    @Severity(SeverityLevel.NORMAL)
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
                            new File("src/test/resources/json/loginUnSuccessful.json")
                    )
            );
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
