import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;


public class apiTest2 {
    public  static Long numberId;
    public static String petId;
    private String BaseURL = "https://petstore.swagger.io/v2/pet";
    private String petName = "Ostin";

    @BeforeMethod
    public  void configureRestAssured() {
        RestAssured.baseURI = BaseURL; // RestAssured это главный класс, формир-ся база респонса для дальнейшего использования
        RestAssured.given()
                .accept(ContentType.JSON); // в запросе передается боди JSON
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter()); // логирование реквеста и респонса, чисто логи
    }

    @Test(priority = 1, description="POST request")
    @Step("Executing POST request")
    public void postDetails() {
// prepare request body in format {"xxxx":"yyyy"}
        JSONObject jsonObject = new JSONObject();
        // сздаем JSONObject, который заполняем след. данными
        jsonObject.put("name", petName);
        jsonObject.put("status", "available");

//        JSONArray jsonArray = new JSONArray(); // нужно было для создания массива JSONов в методе .post("createWithArray")
//        jsonArray.put(jsonObject); //

        // Executing POST request
        numberId = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(jsonObject.toString())
                .when() //
                .post()
                .then()
                .statusCode(200)
                .extract()
                .response()
                .path("id"); // парам записывается в переменную, которую конверт в стринг
        petId = numberId.toString();
    }

    @Test (priority = 2, description="GET request")
    @Step("Executing GET request")
    public void readAllDetails2() {
        // Executing GET request
        RestAssured.given()
                .when()
                .get(petId)
                .then()
                .statusCode(200)
                .body("name", equalTo(petName))
                .extract()
                .response();
    }
    @Test (priority = 3, description="PUT request")
    @Step("Executing Put request")
    public void putDetails()
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", petId);
        jsonObject.put("name", "Gala");
        jsonObject.put("status", "new");

        // Executing PUT request
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(jsonObject.toString())
                .when()
                .put()
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    @Test (priority = 4, description="GET request")
    @Step("Executing GET request")
    public void readAllDetails()
    {
        // Executing GET request
        RestAssured.given()
                .when()
                .get(petId)
                .then()
                .statusCode(200)
                .body("name", equalTo("Gala"))
                .extract()
                .response();
    }

    @Test (priority = 5, description = "DELETE request")
    @Step("Executing Delete request")
    public void deleteDetails() {
        // Executing DELETE request
        RestAssured.given()
                .when()
                .delete(petId)
                .then()
                .statusCode(200)
                .body("message", equalTo(petId))
                .extract()
                .response();
    }
}

