import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;


public class apiTest {
    private String BaseURL = "https://petstore.swagger.io/v2/user/";
    private String userName = "Gala";

    @BeforeMethod
    public  void configureRestAssured () {
        RestAssured.baseURI = BaseURL;
        RestAssured.given().accept(ContentType.JSON);
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }
    @Test(priority = 1, description="POST request")
    @Step("Executing POST request")
    public void postDetails()
    {
// prepare request body in format [{"xxxx":"yyyy"},{"zzzz":"hhhh"}]
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", userName);
        jsonObject.put("firstName", "Gala");
        jsonObject.put("lastName", "Petrova");
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(jsonArray.toString())
                .when()
                .post("createWithArray")
                .then()
                .statusCode(200)
                .extract()
                .response();
    }
    @Test (priority = 2, description="GET request")
    @Step("Executing GET request")
    public void readAllDetails()
    {
        // Executing GET request
        RestAssured.given()
                .when()
                .get(userName)
                .then()
                .statusCode(200)
                .body("username", equalTo(userName))
                .extract()
                .response();

    }
    @Test (priority = 3, description="DELETE request")
    @Step("Executing Delete request")
    public void deleteDetails()
    {
        // Executing DELETE request
        RestAssured.given()
                .when()
                .delete(userName)
                .then()
                .statusCode(200)
                .body("message", equalTo(userName))
                .extract()
                .response();
    }
}
