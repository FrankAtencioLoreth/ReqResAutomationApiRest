import org.json.JSONObject;

public class BaseTest {
    public final String baseURI = "https://reqres.in/api/";
    public JSONObject jsonObject = new JSONObject();

    public final Integer STATUS_CODE_OK = 200;
    public final Integer STATUS_CODE_CREATED = 201;
    public final Integer STATUS_CODE_NO_CONTENT = 204;
    public final Integer STATUS_CODE_BAD_REQUEST = 400;
    public final Integer STATUS_CODE_NOT_FOUND = 404;
}
