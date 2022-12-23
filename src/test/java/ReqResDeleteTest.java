import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReqResDeleteTest extends BaseTest{

    @Test
    @DisplayName("Test Eliminar usuario")
    public void deleteUserTest() {
        RestAssured.
                given().
                log().all().
                contentType(ContentType.JSON).
                body(this.jsonObject.toString()).
                delete(this.baseURI+"users/1").
                then().
                log().all().
                statusCode(this.STATUS_CODE_NO_CONTENT);
    }
}
