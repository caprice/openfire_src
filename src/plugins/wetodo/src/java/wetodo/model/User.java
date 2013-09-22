package wetodo.model;

import java.sql.Timestamp;

public class User {
    private String JID;
    private String username;
    private String name;
    private String phone;
    private String email;
    private Timestamp creationdate;
    private Timestamp modificationdate;

    public String getJID() {
        return JID;
    }

    public void setJID(String JID) {
        this.JID = JID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
