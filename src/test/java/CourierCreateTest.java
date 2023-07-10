import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import API.Courier;

import static org.hamcrest.Matchers.equalTo;


public class CourierCreateTest {
    private String randomLogin;
    private String randomPassword;
    private String randomName;
    private Courier courier;

    @Before
    public void setUp () {
        randomLogin = RandomStringUtils.random(10, true, false);
        randomPassword = RandomStringUtils.random(10, true, true);
        randomName = RandomStringUtils.random(10, true, false);
        courier = new Courier();
    }

    @Step("Create new courier")
    public Response courierCreate(String login, String password, String name)  {
        Response response = courier.courierCreate(login, password, name);
        return  response;
    }

    @Step("Login courier")
    public Response courierLogin(String login, String password)  {
        Response response = courier.courierLogin(login, password);
        return  response;
    }

    @Step("Delete courier")
    public Response courierDelete(int id)  {
        Response response = courier.courierDelete(id);
        return  response;
    }

    @Test
    @DisplayName("Create courier without login")
    @Description("Expected 400 code and message 'Недостаточно данных для создания учетной записи'")
    public void createOnlyPasswordTest() {
        Response response = courierCreate("", randomPassword, randomName);
        response.then().statusCode(400);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Create courier without password")
    @Description("Expected 400 code and message 'Недостаточно данных для создания учетной записи'")
    public void createOnlyLoginTest() {
        Response response = courierCreate(randomLogin, "", randomName);
        response.then().statusCode(400);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Create correct courier")
    @Description("Expected 201 code and 'true' value")
    public void createCorrectTest() {
        Response response = courierCreate(randomLogin, randomPassword, randomName);
        response.then().statusCode(201);
        response.then().assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Create two identical couriers")
    @Description("Expected 409 code and message 'Этот логин уже используется'")
    public void createDoubleTest() {
        Response response = courierCreate(randomLogin, randomPassword, randomName);
        response.then().statusCode(201);
        response = courierCreate(randomLogin, randomPassword, randomName);
        response.then().statusCode(409);
        response.then().assertThat().body("message ", equalTo("Этот логин уже используется"));
    }

    @After
    public void deleteAfter() {
        Response response = courierLogin(randomLogin, randomPassword);
        int id;
        try {
            id = response.then().extract().path("id");
        } catch (NullPointerException e) {
            return;
        }
        response = courierDelete(id);
        response.then().statusCode(200);
    }
}
