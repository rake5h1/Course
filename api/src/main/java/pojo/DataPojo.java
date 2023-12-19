package pojo;

public class DataPojo {
    private String city;
    private int temp;
    private int id;
    private String createdAt;

    public String getCity() {
        return city;
    }

    public int getTemp() {
        return temp;
    }

    public int getId() {
        return id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCity(String c) {
        this.city = c;
    }

    public void setTemp(int t) {
        this.temp = t;
    }

    public void setId(int i) {
        this.id = i;
    }

    public void setCreatedAt(String c) {
        this.createdAt = c;
    }

}
