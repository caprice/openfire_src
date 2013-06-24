package org.jivesoftware.openfire.muc.spi;

public class RoomInfo {
    private String serviceID;
    private String name;
    private String naturalName;
    private String description;

    public RoomInfo(String serviceID, String name, String naturalName, String description) {
        this.serviceID = serviceID;
        this.name = name;
        this.naturalName = naturalName;
        this.description = description;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNaturalName() {
        return naturalName;
    }

    public void setNaturalName(String naturalName) {
        this.naturalName = naturalName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
