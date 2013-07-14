package wetodo.model;

import java.sql.Date;

public class TaskGroup {

    private int id;
    private String tgid;
    private int roomid;
    private String name;
    private int version;
    private Date create_date;
    private Date modify_date;

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

    public int getRoomid() {
        return roomid;
    }

    public void setRoomid(int roomid) {
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

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public Date getModify_date() {
        return modify_date;
    }

    public void setModify_date(Date modify_date) {
        this.modify_date = modify_date;
    }
}
