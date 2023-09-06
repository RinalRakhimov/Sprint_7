import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrdersListTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }
    @Test
    @Description("List of orders receiving")
    public void orderCreateColorsTest() {
        Response orderResponse = OrderSteps.getListOfOrders();

        orderResponse.then().assertThat().statusCode(200)
                .and()
                .body("orders", notNullValue());
    }
}