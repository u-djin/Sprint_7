import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import API.Orders;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.greaterThan;

public class OrderGetTest {
    private Orders order = new Orders();

    @Test
    @DisplayName("Getting list of orders")
    @Description("Expected 200 code and non-empty list of orders")
    public void getOrdersTest() {
        Response response = order.getOrders();
        response.then().statusCode(200);
        response.then().body("orders", hasSize(greaterThan(0)));
    }
}
