import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import static org.apache.http.HttpStatus.*;
import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;
import api.Steps;

import static org.hamcrest.Matchers.equalTo;

public class CourierLoginTest {
    private static String randomLogin = RandomStringUtils.random(10, true, false);
    private static String randomPassword = RandomStringUtils.random(10, true, false);
    private static String randomName = RandomStringUtils.random(10, true, false);
    private static Response response;

    @BeforeClass
    public static void setUp() {
        response = Steps.courierCreate(randomLogin, randomPassword, randomName);
        response.then().statusCode(SC_CREATED);
    }

    @Test
    @DisplayName("Login with wrong login")
    @Description("Expected 404 code and message 'Учетная запись не найдена'")
    public void loginWrongLogin() {
        response = Steps.courierLogin(randomLogin + "1", randomPassword);
        response.then().statusCode(SC_NOT_FOUND);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Login with wrong password")
    @Description("Expected 404 code and message 'Учетная запись не найдена'")
    public void loginWrongPasswordTest() {
        response = Steps.courierLogin(randomLogin, randomPassword + "1");
        response.then().statusCode(SC_NOT_FOUND);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Login without login")
    @Description("Expected 400 code and message 'Недостаточно данных для входа'")
    public void loginWithoutLoginTest() {
        response = Steps.courierLogin("", randomPassword);
        response.then().statusCode(SC_BAD_REQUEST);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Login without password")
    @Description("Expected 400 code and message 'Недостаточно данных для входа'")
    public void loginWithoutPasswordTest() {
        response = Steps.courierLogin(randomLogin, "");
        response.then().statusCode(SC_BAD_REQUEST);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Correct login with login and password existed")
    @Description("Expected 200 code and non-zero id")
    public void loginCorrectTest() {
        response = Steps.courierLogin(randomLogin, randomPassword);
        response.then().statusCode(SC_OK);
        response.then().assertThat().body("id", Matchers.notNullValue());
    }

    @AfterClass
    public static void deleteAfter() {
        int id = Steps.courierLogin(randomLogin, randomPassword).then().extract().path("id");
        response = Steps.courierDelete(id);
        response.then().statusCode(SC_OK);
    }
}
