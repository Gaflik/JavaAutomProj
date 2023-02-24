import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

public class apiTest4 {
    private String BaseURL = "https://reqres.in/api";
    public static String numId;
    @BeforeMethod
    public void configureRestAssured() {
        RestAssured.baseURI = BaseURL;
        RestAssured.given().accept(ContentType.JSON);
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test (priority = 1, description="GET request")
    @Step("Executing GET request")
    public  void getUsers() {
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when()
                .get("/users")
                .then()
                .statusCode(200);
    }

    @Test (priority = 2, description="GET request")
    @Step("Executing GET request")
    public  void getUser() {
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .param("id", 3)
                .param("first_name", "Emma")
                .when()
                .get("/users")
                .then()
                .statusCode(200);
    }

    @Test (priority = 3, description="POST request")
    @Step("Executing Post request")
    public  void createUser() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "morpheus");
        jsonObject.put("job", "leader");

        numId = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(jsonObject.toString())
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .extract()
                .response()
                .path("id");
        System.out.println(numId);
    }

    @Test (priority = 4, description="PUT request")
    @Step("Executing Put request")
    public  void updateUser() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "morpheus");
        jsonObject.put("job", "leader resident");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(jsonObject.toString())
                .when()
                .put("/users/2")
                .then()
                .statusCode(200)
                .assertThat()
                .body("job", equalTo("leader resident"));
    }

//    @Test (priority = 5, description = "DELETE request")
//    @Step("Executing Delete request")
//    public void deleteUser() {
//        // Executing DELETE request
//        RestAssured.given()
//                .param("id", 3)
//                .when()
//                .delete("/users/2")
//                .then()
//                .statusCode(200)
//                .body("message", equalTo(petId))
//                .extract()
//                .response();
//    }
}
