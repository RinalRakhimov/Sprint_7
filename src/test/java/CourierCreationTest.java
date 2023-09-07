import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.Before;
import static org.hamcrest.Matchers.*;
import static org.apache.http.HttpStatus.*;

public class CourierCreationTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }
    @Test
    @DisplayName("Check status code and body of /api/v1/courier") // имя теста
    @Description ("Basic test for /api/v1/courier endpoint")
    public void getNewCourierSuccessCreationStatusCodeAndBody(){
        Response responseNewCourierCreation = CourierSteps.sendPostRequestCourierCreation(new Courier("tuchka35", "1122", "charlie"));
        responseNewCourierCreation.then().assertThat()
                        .statusCode(SC_CREATED)
                        .and()
                        .body("ok", equalTo(true));
    }
    @Test
    @DisplayName("Check status code and body of /api/v1/courier with using dublicate login") // имя теста
    @Description ("Try to create new courier with existing login")
    public void createNewCourierWithExistingLogin(){
        Response responseNewCourierWithExistingLogin = CourierSteps.sendPostRequestCourierCreation(new Courier("tuchka35", "1122", "charlie"));
        responseNewCourierWithExistingLogin.then().assertThat()
                .statusCode(SC_CONFLICT)
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Create new courier without login") // имя теста
    @Description ("Try to create new courier without \"login\" parameter")
    public void createNewCourierWithoutLogin(){
        Response responseNewCourierWithoutLogin = CourierSteps.sendPostRequestCourierCreation(new Courier("", "2211", "charlie"));
        responseNewCourierWithoutLogin.then().assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
    @Test
    @DisplayName("Create new courier without password") // имя теста
    @Description ("Try to create new courier without \"password\" parameter")
    public void createNewCourierWithoutPassword(){
        Response responseNewCourierWithoutPassword = CourierSteps.sendPostRequestCourierCreation(new Courier("tuchka26", "", "charlie"));
        responseNewCourierWithoutPassword.then().assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
    @AfterClass
    public static void dataClear() {
        CourierSteps.deleteCourier(new Courier("tuchka35", "1122"));
    }
}