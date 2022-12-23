import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.File;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


public class ReqResLoginTest extends BaseTest{

    @Test
    @DisplayName("Test Login Correcto")
    public void loginSuccessful() {

        this.jsonObject.put("email", "eve.holt@reqres.in");
        this.jsonObject.put("password", "cityslicka");

        RestAssured.
                given().
                log().all().
                contentType(ContentType.JSON).
                body(this.jsonObject.toString()).
                post(this.baseURI+"login").
                then().
                log().all().
                statusCode(this.STATUS_CODE_OK).
                body("token", notNullValue());
    }

    @Test
    @DisplayName("Test Login Correcto Validar Campos")
    public void validateFieldsloginSuccessful() {

        this.jsonObject.put("email", "eve.holt@reqres.in");
        this.jsonObject.put("password", "cityslicka");

        RestAssured.
                given().
                log().all().
                contentType(ContentType.JSON).
                body(this.jsonObject.toString()).
                post(this.baseURI+"login").
                then().
                log().all().
                statusCode(this.STATUS_CODE_OK).
                body("token", equalTo("QpwL5tke4Pnpja7X4"));
    }

    @Test
    @DisplayName("Test Login Correcto Validar Esquema")
    public void validateSchemaLoginSuceesful() {

        this.jsonObject.put("email", "eve.holt@reqres.in");
        this.jsonObject.put("password", "cityslicka");

        try {
            RestAssured.
                    given().
                    log().all().
                    contentType(ContentType.JSON).
                    body(this.jsonObject.toString()).
                    post(this.baseURI+"login").
                    then().
                    log().all().
                    statusCode(this.STATUS_CODE_OK).
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
        //this.jsonObject.put("password", "cityslicka");

        RestAssured.
                given().
                log().all().
                contentType(ContentType.JSON).
                body(this.jsonObject.toString()).
                post(this.baseURI+"login").
                then().
                log().all().
                statusCode(this.STATUS_CODE_BAD_REQUEST).
                body("error", notNullValue());
    }

    @Test
    @DisplayName("Test Login Incorrecto Contraseña Validar Campos")
    public void validateFieldsloginUnSuccessfulPassword() {

        this.jsonObject.put("email", "eve.holt@reqres.in");
        //this.jsonObject.put("password", "cityslicka");

        RestAssured.
                given().
                log().all().
                contentType(ContentType.JSON).
                body(this.jsonObject.toString()).
                post(this.baseURI+"login").
                then().
                log().all().
                statusCode(this.STATUS_CODE_BAD_REQUEST).
                body("error", equalTo("Missing password"));
    }

    @Test
    @DisplayName("Test Login Incorrecto Email Validar Campos")
    public void validateFieldsloginUnSuccessfulEmail() {

        //this.jsonObject.put("email", "eve.holt@reqres.in");
        this.jsonObject.put("password", "cityslicka");

        RestAssured.
                given().
                log().all().
                contentType(ContentType.JSON).
                body(this.jsonObject.toString()).
                post(this.baseURI+"login").
                then().
                log().all().
                statusCode(this.STATUS_CODE_BAD_REQUEST).
                body("error", equalTo("Missing email or username"));
    }

    @Test
    @DisplayName("Test Login Incorrecto Validar Esquema")
    public void validateSchemaLoginSuccessful() {

        this.jsonObject.put("email", "eve.holt@reqres.in");
        //this.jsonObject.put("password", "cityslicka");

        try {
            RestAssured.
                    given().
                    log().all().
                    contentType(ContentType.JSON).
                    body(this.jsonObject.toString()).
                    post(this.baseURI+"login").
                    then().
                    log().all().
                    statusCode(this.STATUS_CODE_BAD_REQUEST).
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
