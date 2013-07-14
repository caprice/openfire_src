package wetodo.model;

import java.sql.Date;

public class Task {
    private int id;
    private String tid;
    private String tgid;
    private int roomid;
    private String name;
    private int status;
    private Date create_date;
    private Date modify_date;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
