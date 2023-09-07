import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.apache.http.HttpStatus.*;

public class CourierLoginTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
        CourierSteps.sendPostRequestCourierCreation(new Courier("tuchk27", "1122", "charlie"));
    }

    @Test
    @Description ("Checking status code and body on log in /api/v1/courier/login")
    public void getLoginCourierStatusCodeAndBody() {
        Response loginResponse = CourierSteps.sendPostRequestCourierLogin(new Courier("tuchk27", "1122"));
        loginResponse.then().assertThat()
                .statusCode(SC_OK)
                .and()
                .body("id", notNullValue());
    }
    @Test
    @Description ("Checking status code and body on /api/v1/courier/login without \"login\" in request")
    public void getLoginCourierStatusCodeAndBodyWithoutLoginParameter() {
        Response loginResponse = CourierSteps.sendPostRequestCourierLogin(new Courier("", "1122"));
        loginResponse.then().assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }
    @Test
    @Description ("Checking status code and body on /api/v1/courier/login without \"password\" in request")
    public void getLoginCourierStatusCodeAndBodyWithoutPasswordParameter() {
        Response loginResponse = CourierSteps.sendPostRequestCourierLogin(new Courier("tuchk27", ""));
        loginResponse.then().assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }
    @Test
    @Description ("Checking status code and body on /api/v1/courier/login with not existing \"login\" and \"password\" in request")
    public void getLoginCourierStatusCodeAndBodyWithNotExistingParameters() {
        Response loginResponse = CourierSteps.sendPostRequestCourierLogin(new Courier("tuchka100", "2288"));
        loginResponse.then().assertThat()
                .statusCode(SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }
    @After
    public void dataClear() {
        CourierSteps.deleteCourier(new Courier("tuchk27", "1122"));
    }
}
