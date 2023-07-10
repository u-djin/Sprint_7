package API;

import constants.APIConstants;
import io.restassured.response.Response;
import pojo.CourierPOJO;
import static io.restassured.RestAssured.*;

public class Courier {
    public Response courierCreate(String login, String password, String firstName) {
        Response response = given()
                .header("Content-type", "application/json")
                .body(new CourierPOJO(login, password, firstName))
                .post(APIConstants.BASE_URL + APIConstants.COURIER_CREATE_PATH);
        return response;
    }

    public Response courierLogin(String login, String password) {
        Response response = given()
                .header("Content-type", "application/json")
                .body(new CourierPOJO(login, password))
                .post(APIConstants.BASE_URL + APIConstants.COURIER_LOGIN_PATH);
        return response;
    }

    public Response courierDelete(int id) {
        Response response = given()
                .header("Content-type", "application/json")
                .body(new CourierPOJO(id))
                .delete(APIConstants.BASE_URL + APIConstants.COURIER_DELETE_PATH + String.valueOf(id));
        return response;
    }
}
