import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class OrderSteps {
    @Step("Send POST request to /api/v1/orders")
    @Description("Get /api/v1/orders response")
    public static Response getPostRequestForOrderCreation(Object json){

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .post("/api/v1/orders");
        return response;
    }
    @Step("Send GET request to /api/v1/orders")
    @Description("Get list of orders")
    public static Response getListOfOrders(){

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .get("/api/v1/orders");
        return response;
    }
}