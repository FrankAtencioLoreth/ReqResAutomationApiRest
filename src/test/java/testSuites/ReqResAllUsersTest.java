package testSuites;

import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.Is.is;

@Feature("Get all Users")
public class ReqResAllUsersTest {

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
    @DisplayName("Test obtener todos los usuarios")
    public void getAllUsers() {
        given().
        body(this.jsonObject.toString()).
        get("unknown").
        then().
        statusCode(HttpStatus.SC_OK);
    }

    @Test
    @DisplayName("Test obtener todos los usuarios validar campos")
    public void getAllUsersValidateFields() {
        given().
        body(this.jsonObject.toString()).
        get("unknown").
        then().
        statusCode(HttpStatus.SC_OK).
        body("page", is(instanceOf(Integer.class))).
        body("per_page", is(instanceOf(Integer.class))).
        body("total", is(instanceOf(Integer.class))).
        body("total_pages", is(instanceOf(Integer.class))).
        body("data", is(instanceOf(Object.class)));
    }
}
