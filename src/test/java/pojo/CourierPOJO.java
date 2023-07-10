package pojo;

public class CourierPOJO {
    private String login;
    private String password;
    private String firstName;
    private int id;

    public CourierPOJO(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public CourierPOJO(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public CourierPOJO(int id) {
        this.id = id;
    }

    public CourierPOJO() {}


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
