package wetodo.model;

import java.sql.Timestamp;

public class TaskGroup {

    private int id;
    private String tgid;
    private String roomid;
    private String name;
    private int version;
    private Timestamp create_date;
    private Timestamp modify_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TaskGroup{" +
                "id=" + id +
                ", tgid='" + tgid + '\'' +
                ", roomid=" + roomid +
                ", name='" + name + '\'' +
                ", version=" + version +
                ", create_date=" + create_date +
                ", modify_date=" + modify_date +
                '}';
    }

    public String getTgid() {
        return tgid;
    }

    public void setTgid(String tgid) {
        this.tgid = tgid;
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
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
