package wetodo.sms;

public class Guard {
    private String username;
    private long time;
    private int count;

    public Guard(String username, long time, int count) {
        this.username = username;
        this.time = time;
        this.count = count;
    }

    int getCount() {
        return count;
    }

    void setCount(int count) {
        this.count = count;
    }

    long getTime() {
        return time;
    }

    void setTime(long time) {
        this.time = time;
    }

    String getUsername() {
        return username;
    }

    void setUsername(String username) {
        this.username = username;
    }
}
