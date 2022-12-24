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
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.notNullValue;

@Feature("Post a new User")
public class ReqResCreateTest {

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
    @DisplayName("Test Usuario Creado")
    public void createdUserTest() {
        this.jsonObject.put("name", "Amy Walderaich");
        this.jsonObject.put("job", "System Engineer");
        
        given().
        body(this.jsonObject.toString()).
        post("users").
        then().
        statusCode(HttpStatus.SC_CREATED).
        body("name", notNullValue()).
        body("job", notNullValue()).
        body("id", notNullValue()).
        body("createdAt", notNullValue());
    }

    @Test
    @DisplayName("Test Usuario Creado Campo name Vacio")
    public void createdUserFailNameTest() {
        this.jsonObject.put("job", "System Engineer");

        given().
        body(this.jsonObject.toString()).
        post("users").
        then().
        statusCode(HttpStatus.SC_CREATED).
        body("name", isEmptyOrNullString()).
        body("job", notNullValue()).
        body("id", notNullValue()).
        body("createdAt", notNullValue());
    }

    @Test
    @DisplayName("Test Usuario Creado Campo job Vacio")
    public void createdUserFailJobTest() {
        this.jsonObject.put("name", "Andrea Sierra");

        given().
        body(this.jsonObject.toString()).
        post("users").
        then().
        statusCode(HttpStatus.SC_CREATED).
        body("name", notNullValue()).
        body("job", isEmptyOrNullString()).
        body("id", notNullValue()).
        body("createdAt", notNullValue());
    }
}
