import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.notNullValue;

public class ReqResUpdateTest extends BaseTest{

    @Test
    @DisplayName("Test Usuario Update")
    public void updateUserTest() {

        this.jsonObject.put("name", "Amy Walderaich");
        this.jsonObject.put("job", "System Engineer");

        RestAssured.
                given().
                log().all().
                contentType(ContentType.JSON).
                body(this.jsonObject.toString()).
                put(this.baseURI+"users/1").
                then().
                log().all().
                statusCode(this.STATUS_CODE_OK).
                body("name", notNullValue()).
                body("job", notNullValue()).
                body("updatedAt", notNullValue());

    }

    @Test
    @DisplayName("Test Usuario Update Fail job Vacio")
    public void updateUserFailTestJobEmpty() {

        this.jsonObject.put("name", "Amy Walderaich");
        //this.jsonObject.put("job", "System Engineer");

        RestAssured.
                given().
                log().all().
                contentType(ContentType.JSON).
                body(this.jsonObject.toString()).
                put(this.baseURI+"users/1").
                then().
                log().all().
                statusCode(this.STATUS_CODE_OK).
                body("name", notNullValue()).
                body("job", isEmptyOrNullString()).
                body("updatedAt", notNullValue());

    }

    @Test
    @DisplayName("Test Usuario Update Fail job Vacio")
    public void updateUserFailTestNameEmpty() {

        //this.jsonObject.put("name", "Amy Walderaich");
        this.jsonObject.put("job", "System Engineer");

        RestAssured.
                given().
                log().all().
                contentType(ContentType.JSON).
                body(this.jsonObject.toString()).
                put(this.baseURI+"users/1").
                then().
                log().all().
                statusCode(this.STATUS_CODE_OK).
                body("name", isEmptyOrNullString()).
                body("job", notNullValue()).
                body("updatedAt", notNullValue());

    }
}
