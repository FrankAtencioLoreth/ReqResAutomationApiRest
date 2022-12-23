import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

public class ReqResAllUsersTest extends BaseTest{

    @Test
    @DisplayName("Test obtener todos los usuarios")
    public void getAllUsers() {
        RestAssured.
                given().
                log().all().
                contentType(ContentType.JSON).
                body(this.jsonObject.toString()).
                get(this.baseURI+"unknown").
                then().
                log().all().
                statusCode(this.STATUS_CODE_OK);
    }

    @Test
    @DisplayName("Test obtener todos los usuarios validar campos")
    public void getAllUsersValidateFields() {
        RestAssured.
                given().
                log().all().
                contentType(ContentType.JSON).
                body(this.jsonObject.toString()).
                get(this.baseURI+"unknown").
                then().
                log().all().
                statusCode(this.STATUS_CODE_OK).
                body("page", is(instanceOf(Integer.class))).
                body("per_page", is(instanceOf(Integer.class))).
                body("total", is(instanceOf(Integer.class))).
                body("total_pages", is(instanceOf(Integer.class))).
                body("data", is(instanceOf(Object.class)));
    }
}
