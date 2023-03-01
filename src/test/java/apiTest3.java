import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

public class apiTest3 {
    private String baseUrl = "https://dummy.restapiexample.com/api/v1";
    public static Integer numberId;
    public static String numId;

    @BeforeMethod
    public void configureRestAssured() {
        RestAssured.baseURI = baseUrl;
        RestAssured.given()
                .accept(ContentType.JSON);
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

    }

    @Test(priority = 1, description="POST request")
    @Step("Executing POST request")

    public void postDetails() throws InterruptedException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "test");
        jsonObject.put("salary", "123");
        jsonObject.put("age", "23");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(jsonObject.toString())
                .when()
                .post("/create")
                .then()
                .statusCode(200)
                .body("message", equalTo("Successfully! Record has been added."));
        Thread.sleep(60000);
    }


    @Test (priority = 2, description="GET request")
    @Step("Executing GET request")

    public void readDetail() throws InterruptedException {

        RestAssured.given()
                .when()
                .get("/employee/1")
                .then()
                .statusCode(200)
                .assertThat()
                .body("data.id", equalTo(1));
        Thread.sleep(60000);
    }

    @Test (priority = 3, description="PUT request")
    @Step("Executing Put request")

    public void putDetails() throws InterruptedException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "test");
        jsonObject.put("salary", "123");
        jsonObject.put("age", "23");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(jsonObject.toString())
                .when()
                .put("update/21")
                .then()
                .statusCode(200);
        Thread.sleep(60000);
    }

    @Test (priority = 4, description = "DELETE request")
    @Step("Executing Delete request")
    public void deleteDetails() throws InterruptedException {
        RestAssured.given()
                .when()
                .delete("/delete/2")
                .then()
                .statusCode(200)
                .assertThat()
                .body("message", equalTo("Successfully! Record has been deleted"));
        Thread.sleep(60000);
    }
}

