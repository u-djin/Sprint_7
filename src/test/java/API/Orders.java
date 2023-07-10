package API;

import constants.APIConstants;
import io.restassured.response.Response;
import pojo.OrdersPOJO;
import static io.restassured.RestAssured.*;

public class Orders {
    public Response createOrder(String[] color) {
        Response response = given()
                .header("Content-type", "application/json")
                .body(new OrdersPOJO(color))
                .post(APIConstants.BASE_URL + APIConstants.ORDERS);
        return response;
    }

    public Response getOrders() {
        Response response = given()
                .header("Content-type", "application/json")
                .get(APIConstants.BASE_URL + APIConstants.ORDERS);
        return response;
    }

    public Response cancelOrder(int track) {
        Response response = given()
                .header("Content-type", "application/json")
                .put(APIConstants.BASE_URL + APIConstants.ORDER_CANCEL + String.valueOf(track));
        return response;
    }
}
