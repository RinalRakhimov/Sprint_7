import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class CourierSteps {

    private static final String CREATION_ENDPOINT = "/api/v1/courier";
    private static final String LOGIN_ENDPOINT = "/api/v1/courier/login";
    @Step("Send POST request to /api/v1/courier")
    @Description("Courier creation")
    public static Response sendPostRequestCourierCreation(Object json){
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .post(CREATION_ENDPOINT);
        return response;
    }

    @Step("Send POST request to /api/v1/courier/login")
    @Description("Get /api/v1/courier/login response")
    public static Response sendPostRequestCourierLogin(Object json){

        Response response =
                given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .post(LOGIN_ENDPOINT);
        return response;
    }

    @Step("Delete courier by id")
    @Description("Sent delete request on /api/v1/courier/:id")
    public static void deleteCourier(Object json) {
           int id = sendPostRequestCourierLogin(json)
                .then().extract().body().path("id");
        given()
                .delete(CREATION_ENDPOINT + "/" + id);
    }
}