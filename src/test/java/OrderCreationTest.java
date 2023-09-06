import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreationTest {
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> color;

    public OrderCreationTest(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters(name = "Создание заказа. Тестовые данные: {0}, {1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}")
    public static Object[][] getOrderData() {

        List<String> colorBlack = new ArrayList<>();
        colorBlack.add("BLACK");

        List<String> colorGrey = new ArrayList<>();
        colorGrey.add("GREY");

        List<String> colorBlackAndGrey = new ArrayList<>();
        colorBlackAndGrey.add("BLACK");
        colorBlackAndGrey.add("GREY");

        List<String> colorNo = new ArrayList<>();

        return new Object[][] {
                {"Марат", "Латыпов", "Страстной бульвар, 4", "4", "+7 800 355 35 35", 5, "2020-06-06", "Можно побыстрее?", colorBlack},
                {"Марат", "Латыпов", "Страстной бульвар, 4", "4", "+7 800 355 35 35", 5, "2020-06-06", "Можно побыстрее?", colorGrey},
                {"Марат", "Латыпов", "Страстной бульвар, 4", "4", "+7 800 355 35 35", 5, "2020-06-06", "Можно побыстрее?", colorBlackAndGrey},
                {"Марат", "Латыпов", "Страстной бульвар, 4", "4", "+7 800 355 35 35", 5, "2020-06-06", "Можно побыстрее?", colorNo},
        };
    }
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }
    @Test
    @Description("Orders creation with different colors")
    public void orderCreateColorsTest() {
        Response orderResponse = OrderSteps.getPostRequestForOrderCreation(new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color));
        orderResponse.then().assertThat().statusCode(201)
                .and()
                .body("track", notNullValue());
    }
}
