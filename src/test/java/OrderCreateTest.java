import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import API.Orders;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class OrderCreateTest {
    private Orders order = new Orders();
    private String[] colors;
    private int track;

    public OrderCreateTest(String[] colors) {
        this.colors = colors;
    }

    @Parameterized.Parameters
    public static Object[][] getColorsData() {
        return new Object[][]{
                {new String[] {"GREY"}},
                {new String[] {"BLACK"}},
                {new String[] {"BLACK", "GREY"}},
                {null},
        };
    }

    @Test
    @DisplayName("Creating order with different colors")
    @Description("Expected 201 code")
    public void createOrderTest() {
        Response response = order.createOrder(colors);
        response.then().statusCode(201);
        response.then().assertThat().body("track", Matchers.notNullValue());
        this.track = response.then().extract().path("track");
    }

    @After
    public void cancelOrder() {
        Response response = order.cancelOrder(this.track);
        response.then().statusCode(200);
    }
}
