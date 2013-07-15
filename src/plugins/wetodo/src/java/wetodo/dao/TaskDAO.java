package wetodo.dao;

import org.jivesoftware.database.DbConnectionManager;
import wetodo.model.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {
    private static final String LIST_TASK_ALL = "SELECT * from wtdTask where roomid = ?";
    private static final String LIST_TASK = "SELECT * from wtdTask where roomid = ? and tgid = ?";
    private static final String INSERT_TASK = "INSERT INTO wtdTask (id, tid, tgid, roomid, name, status, create_date, modify_date) values (null, ?, ?, ?, ?, ?, ?, ?)";

    public static List<Task> list_all(int roomid) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DbConnectionManager.getConnection();
            pstmt = con.prepareStatement(LIST_TASK_ALL);
            pstmt.setInt(1, roomid);
            ResultSet rs = pstmt.executeQuery();
            List<Task> list = new ArrayList<Task>();
            while (rs.next()) {
                Task task = new Task();
                task.setId(rs.getInt(1));
                task.setTid(rs.getString(2));
                task.setTgid(rs.getString(3));
                task.setRoomid(rs.getInt(4));
                task.setName(rs.getString(5));
                task.setStatus(rs.getInt(6));
                task.setCreate_date(rs.getDate(7));
                task.setModify_date(rs.getDate(8));

                list.add(task);
            }
            return list;
        } catch (SQLException sqle) {
            return null;
        } finally {
            DbConnectionManager.closeConnection(pstmt, con);
        }
    }

    public static List<Task> list(int roomid, String tgid) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DbConnectionManager.getConnection();
            pstmt = con.prepareStatement(LIST_TASK);
            pstmt.setInt(1, roomid);
            pstmt.setString(2, tgid);
            ResultSet rs = pstmt.executeQuery();
            List<Task> list = new ArrayList<Task>();
            while (rs.next()) {
                Task task = new Task();
                task.setId(rs.getInt(1));
                task.setTid(rs.getString(2));
                task.setTgid(rs.getString(3));
                task.setRoomid(rs.getInt(4));
                task.setName(rs.getString(5));
                task.setStatus(rs.getInt(6));
                task.setCreate_date(rs.getDate(7));
                task.setModify_date(rs.getDate(8));

                list.add(task);
            }
            return list;
        } catch (SQLException sqle) {
            return null;
        } finally {
            DbConnectionManager.closeConnection(pstmt, con);
        }
    }

    public static Task add(Task task) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DbConnectionManager.getConnection();
            pstmt = con.prepareStatement(INSERT_TASK);
            pstmt.setString(1, task.getTid());
            pstmt.setString(2, task.getTgid());
            pstmt.setInt(3, task.getRoomid());
            pstmt.setString(4, task.getName());
            pstmt.setInt(5, task.getStatus());
            pstmt.setDate(6, task.getCreate_date());
            pstmt.setDate(7, task.getModify_date());
            pstmt.executeUpdate();

            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) {
                task.setId(keys.getInt(1));
                return task;
            } else {
                return null;
            }

        } catch (SQLException sqle) {
            // Log error
            return null;
        } finally {
            DbConnectionManager.closeConnection(pstmt, con);
        }
    }

}
