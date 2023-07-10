package pojo;

public class OrdersPOJO {
    private String firstName;
    private String lastName;
    private String address;
    private int metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color;

    public OrdersPOJO(String[] color) {
        this.color = color;
    }

    public OrdersPOJO() {
    }
}
