import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.equalTo;

public class ReqResRegisterUserTest {

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
    @DisplayName("Test Register Correcto")
    public void registerUserTestSuccessful() {
        this.jsonObject.put("email", "eve.holt@reqres.in");
        this.jsonObject.put("password", "cityslicka");

        given().
        body(this.jsonObject.toString()). //new File("file.json")
        post("register").
        then().
        statusCode(HttpStatus.SC_OK).
        body("id", notNullValue()).
        body("id",  is(instanceOf(Integer.class))).
        body("token", equalTo("QpwL5tke4Pnpja7X4"));
    }

    @Test
    @DisplayName("Test Register Incorrecto Campo email Vacio")
    public void registerUserTestFailEmailEmmpty() {
        this.jsonObject.put("password", "cityslicka");

        given().
        body(this.jsonObject.toString()). //new File("file.json")
        post("register").
        then().
        statusCode(HttpStatus.SC_BAD_REQUEST).
        body("error", notNullValue()).
        body("error", is(equalTo("Missing email or username")));
    }

    @Test
    @DisplayName("Test Register Incorrecto Campo password Vacio")
    public void registerUserTestFailPasswordEmmpty() {
        this.jsonObject.put("email", "eve.holt@reqres.in");

        given().
        body(this.jsonObject.toString()). //new File("file.json")
        post("register").
        then().
        statusCode(HttpStatus.SC_BAD_REQUEST).
        body("error", notNullValue()).
        body("error", is(equalTo("Missing password")));
    }
}
