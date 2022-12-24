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

@Feature("Update User")
public class ReqResUpdateTest {

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
    @DisplayName("Test Usuario Update")
    public void updateUserTest() {
        this.jsonObject.put("name", "Amy Walderaich");
        this.jsonObject.put("job", "System Engineer");

        given().
        body(this.jsonObject.toString()).
        put("users/1").
        then().
        statusCode(HttpStatus.SC_OK).
        body("name", notNullValue()).
        body("job", notNullValue()).
        body("updatedAt", notNullValue());
    }

    @Test
    @DisplayName("Test Usuario Update Fail job Vacio")
    public void updateUserFailTestJobEmpty() {
        this.jsonObject.put("name", "Amy Walderaich");

        given().
        body(this.jsonObject.toString()).
        put("users/1").
        then().
        statusCode(HttpStatus.SC_OK).
        body("name", notNullValue()).
        body("job", isEmptyOrNullString()).
        body("updatedAt", notNullValue());

    }

    @Test
    @DisplayName("Test Usuario Update Fail job Vacio")
    public void updateUserFailTestNameEmpty() {
        this.jsonObject.put("job", "System Engineer");

        given().
        body(this.jsonObject.toString()).
        put("users/1").
        then().
        statusCode(HttpStatus.SC_OK).
        body("name", isEmptyOrNullString()).
        body("job", notNullValue()).
        body("updatedAt", notNullValue());

    }
}
