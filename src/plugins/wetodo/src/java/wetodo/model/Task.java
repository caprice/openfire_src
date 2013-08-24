package wetodo.model;

import java.sql.Timestamp;

public class Task {
    private int id;
    private String tid;
    private String tgid;
    private String roomid;
    private String name;
    private int status;
    private Timestamp create_date;
    private Timestamp modify_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", tid='" + tid + '\'' +
                ", tgid='" + tgid + '\'' +
                ", roomid=" + roomid +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", create_date=" + create_date +
                ", modify_date=" + modify_date +
                '}';
    }
}
