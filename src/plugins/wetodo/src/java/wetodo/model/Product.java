package wetodo.model;


import java.sql.Timestamp;

public class Product {
    private int id;
    private String iapId;
    private String name;
    private int month;
    private Timestamp creationdate;
    private Timestamp modificationdate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIapId() {
        return iapId;
    }

    public void setIapId(String iapId) {
        this.iapId = iapId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public Timestamp getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Timestamp creationdate) {
        this.creationdate = creationdate;
    }

    public Timestamp getModificationdate() {
        return modificationdate;
    }

    public void setModificationdate(Timestamp modificationdate) {
        this.modificationdate = modificationdate;
    }
}
