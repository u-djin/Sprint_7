import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import static org.apache.http.HttpStatus.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import api.Steps;

import static org.hamcrest.Matchers.equalTo;


public class CourierCreateTest {
    private String randomLogin;
    private String randomPassword;
    private String randomName;

    @Before
    public void setUp () {
        randomLogin = RandomStringUtils.random(10, true, false);
        randomPassword = RandomStringUtils.random(10, true, true);
        randomName = RandomStringUtils.random(10, true, false);
    }

    @Test
    @DisplayName("Create courier without login")
    @Description("Expected 400 code and message 'Недостаточно данных для создания учетной записи'")
    public void createOnlyPasswordTest() {
        Response response = Steps.courierCreate("", randomPassword, randomName);
        response.then().statusCode(SC_BAD_REQUEST);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Create courier without password")
    @Description("Expected 400 code and message 'Недостаточно данных для создания учетной записи'")
    public void createOnlyLoginTest() {
        Response response = Steps.courierCreate(randomLogin, "", randomName);
        response.then().statusCode(SC_BAD_REQUEST);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Create correct courier")
    @Description("Expected 201 code and 'true' value")
    public void createCorrectTest() {
        Response response = Steps.courierCreate(randomLogin, randomPassword, randomName);
        response.then().statusCode(SC_CREATED);
        response.then().assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Create two identical couriers")
    @Description("Expected 409 code and message 'Этот логин уже используется'")
    public void createDoubleTest() {
        Response response = Steps.courierCreate(randomLogin, randomPassword, randomName);
        response.then().statusCode(SC_CREATED);
        response = Steps.courierCreate(randomLogin, randomPassword, randomName);
        response.then().statusCode(SC_CONFLICT);
        response.then().assertThat().body("message ", equalTo("Этот логин уже используется"));
    }

    @After
    public void deleteAfter() {
        Response response = Steps.courierLogin(randomLogin, randomPassword);
        int id;
        try {
            id = response.then().extract().path("id");
        } catch (NullPointerException e) {
            return;
        }
        response = Steps.courierDelete(id);
        response.then().statusCode(SC_OK);
    }
}
