package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class Steps {
    static Courier courier = new Courier();

    @Step("Create new courier")
    public static Response courierCreate(String login, String password, String name)  {
        Response response = courier.courierCreate(login, password, name);
        return  response;
    }

    @Step("Login courier")
    public static Response courierLogin(String login, String password)  {
        Response response = courier.courierLogin(login, password);
        return  response;
    }

    @Step("Delete courier")
    public static Response courierDelete(int id)  {
        Response response = courier.courierDelete(id);
        return  response;
    }
}
