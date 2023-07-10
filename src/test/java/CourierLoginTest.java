import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;
import API.Courier;

import static org.hamcrest.Matchers.equalTo;

public class CourierLoginTest {
    private static String randomLogin = RandomStringUtils.random(10, true, false);
    private static String randomPassword = RandomStringUtils.random(10, true, false);
    private static String randomName = RandomStringUtils.random(10, true, false);
    private static Courier courier = new Courier();
    private static Response response;

    @BeforeClass
    public static void setUp() {
        response = courier.courierCreate(randomLogin, randomPassword, randomName);
        response.then().statusCode(201);
    }

    @Step("Login courier")
    public Response courierLogin(String login, String password)  {
        Response response = courier.courierLogin(login, password);
        return  response;
    }

    @Test
    @DisplayName("Login with wrong login")
    @Description("Expected 404 code and message 'Учетная запись не найдена'")
    public void loginWrongLogin() {
        response = courierLogin(randomLogin + "1", randomPassword);
        response.then().statusCode(404);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Login with wrong password")
    @Description("Expected 404 code and message 'Учетная запись не найдена'")
    public void loginWrongPasswordTest() {
        response = courierLogin(randomLogin, randomPassword + "1");
        response.then().statusCode(404);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Login without login")
    @Description("Expected 400 code and message 'Недостаточно данных для входа'")
    public void loginWithoutLoginTest() {
        response = courierLogin("", randomPassword);
        response.then().statusCode(400);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Login without password")
    @Description("Expected 400 code and message 'Недостаточно данных для входа'")
    public void loginWithoutPasswordTest() {
        response = courierLogin(randomLogin, "");
        response.then().statusCode(400);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Correct login with login and password existed")
    @Description("Expected 200 code and non-zero id")
    public void loginCorrectTest() {
        response = courierLogin(randomLogin, randomPassword);
        response.then().statusCode(200);
        response.then().assertThat().body("id", Matchers.notNullValue());
    }

    @AfterClass
    public static void deleteAfter() {
        int id = courier.courierLogin(randomLogin, randomPassword).then().extract().path("id");
        response = courier.courierDelete(id);
        response.then().statusCode(200);
    }
}
