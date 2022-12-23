import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.notNullValue;

public class ReqResCreateTest extends BaseTest{

    @Test
    @DisplayName("Test Usuario Creado")
    public void createdUserTest() {

        this.jsonObject.put("name", "Amy Walderaich");
        this.jsonObject.put("job", "System Engineer");

        RestAssured.
                given().
                log().all().
                contentType(ContentType.JSON).
                body(this.jsonObject.toString()).
                post(this.baseURI+"users").
                then().
                log().all().
                statusCode(this.STATUS_CODE_CREATED).
                body("name", notNullValue()).
                body("job", notNullValue()).
                body("id", notNullValue()).
                body("createdAt", notNullValue());

    }

    @Test
    @DisplayName("Test Usuario Creado Campo name Vacio")
    public void createdUserFailNameTest() {

        //this.jsonObject.put("name", "Andrea Sierra");
        this.jsonObject.put("job", "System Engineer");

        RestAssured.
                given().
                log().all().
                contentType(ContentType.JSON).
                body(this.jsonObject.toString()).
                post(this.baseURI+"users").
                then().
                log().all().
                statusCode(this.STATUS_CODE_CREATED).
                body("name", isEmptyOrNullString()).
                body("job", notNullValue()).
                body("id", notNullValue()).
                body("createdAt", notNullValue());
    }

    @Test
    @DisplayName("Test Usuario Creado Campo job Vacio")
    public void createdUserFailJobTest() {

        this.jsonObject.put("name", "Andrea Sierra");
        //this.jsonObject.put("job", "System Engineer");

        RestAssured.
                given().
                log().all().
                contentType(ContentType.JSON).
                body(this.jsonObject.toString()).
                post(this.baseURI+"users").
                then().
                log().all().
                statusCode(this.STATUS_CODE_CREATED).
                body("name", notNullValue()).
                body("job", isEmptyOrNullString()).
                body("id", notNullValue()).
                body("createdAt", notNullValue());
    }
}
