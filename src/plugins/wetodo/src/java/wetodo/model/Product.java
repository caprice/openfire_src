package wetodo.model;


import java.sql.Timestamp;

public class Product {
    private int id;
    private String iap_id;
    private String name;
    private int day;
    private Timestamp create_date;
    private Timestamp modify_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIap_id() {
        return iap_id;
    }

    public void setIap_id(String iap_id) {
        this.iap_id = iap_id;
    }

    public Timestamp getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Timestamp create_date) {
        this.create_date = create_date;
    }

    public Timestamp getModify_date() {
        return modify_date;
    }

    public void setModify_date(Timestamp modify_date) {
        this.modify_date = modify_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

}
