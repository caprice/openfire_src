package wetodo.model;

import java.sql.Timestamp;

public class Pay {
    private int id;
    private String username;
    private String receipt;
    private String iap_id;
    private Timestamp create_date;
    private Timestamp modify_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
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
}
