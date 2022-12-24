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

@Feature("Delete a User")
public class ReqResDeleteTest {

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
    @DisplayName("Test Eliminar usuario")
    public void deleteUserTest() {
        given().
        delete("users/1").
        then().
        statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
