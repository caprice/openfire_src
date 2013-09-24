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
    private static final String LIST_TASK_ALL = "SELECT * FROM wtdTask WHERE roomid = ?";
    private static final String LIST_TASK = "SELECT * FROM wtdTask WHERE roomid = ? and tgid = ?";
    private static final String INSERT_TASK = "INSERT INTO wtdTask (id, tid, tgid, roomid, name, status, create_date, modify_date) VALUES (null, ?, ?, ?, ?, ?, ?, ?)";
    private static final String MODIFY_TASK = "UPDATE wtdTask SET name = ?, modify_date = ?, status = ? WHERE tid = ? AND tgid = ? AND roomid = ?";
    private static final String FIND_TASK = "SELECT * FROM wtdTask WHERE tid = ?";
    private static final String DEL_TASK = "DELETE FROM wtdTask WHERE tid = ?";

    public static List<Task> list_all(String roomid) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DbConnectionManager.getConnection();
            pstmt = con.prepareStatement(LIST_TASK_ALL);
            pstmt.setString(1, roomid);
            ResultSet rs = pstmt.executeQuery();
            List<Task> list = new ArrayList<Task>();
            while (rs.next()) {
                Task task = new Task();
                task.setId(rs.getInt(1));
                task.setTid(rs.getString(2));
                task.setTgid(rs.getString(3));
                task.setRoomid(rs.getString(4));
                task.setName(rs.getString(5));
                task.setStatus(rs.getInt(6));
                task.setCreate_date(rs.getTimestamp(7));
                task.setModify_date(rs.getTimestamp(8));

                list.add(task);
            }
            return list;
        } catch (SQLException sqle) {
            return null;
        } finally {
            DbConnectionManager.closeConnection(pstmt, con);
        }
    }

    public static List<Task> list(String roomid, String tgid) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DbConnectionManager.getConnection();
            pstmt = con.prepareStatement(LIST_TASK);
            pstmt.setString(1, roomid);
            pstmt.setString(2, tgid);
            ResultSet rs = pstmt.executeQuery();
            List<Task> list = new ArrayList<Task>();
            while (rs.next()) {
                Task task = new Task();
                task.setId(rs.getInt(1));
                task.setTid(rs.getString(2));
                task.setTgid(rs.getString(3));
                task.setRoomid(rs.getString(4));
                task.setName(rs.getString(5));
                task.setStatus(rs.getInt(6));
                task.setCreate_date(rs.getTimestamp(7));
                task.setModify_date(rs.getTimestamp(8));

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
            pstmt.setString(3, task.getRoomid());
            pstmt.setString(4, task.getName());
            pstmt.setInt(5, task.getStatus());
            pstmt.setTimestamp(6, task.getCreate_date());
            pstmt.setTimestamp(7, task.getModify_date());
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

    public static boolean modify(Task task) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DbConnectionManager.getConnection();
            pstmt = con.prepareStatement(MODIFY_TASK);
            pstmt.setString(1, task.getName());
            pstmt.setTimestamp(2, task.getModify_date());
            pstmt.setInt(3, task.getStatus());
            pstmt.setString(4, task.getTid());
            pstmt.setString(5, task.getTgid());
            pstmt.setString(6, task.getRoomid());
            pstmt.executeUpdate();

            return true;
        } catch (SQLException sqle) {
            // Log error
            return false;
        } finally {
            DbConnectionManager.closeConnection(pstmt, con);
        }
    }

    public static Task find(String tid) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DbConnectionManager.getConnection();
            pstmt = con.prepareStatement(FIND_TASK);
            pstmt.setString(1, tid);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Task task = new Task();
                task.setId(rs.getInt(1));
                task.setTid(rs.getString(2));
                task.setTgid(rs.getString(3));
                task.setRoomid(rs.getString(4));
                task.setName(rs.getString(5));
                task.setStatus(rs.getInt(6));
                task.setCreate_date(rs.getTimestamp(7));
                task.setModify_date(rs.getTimestamp(8));
                return task;
            }
            return null;
        } catch (SQLException sqle) {
            return null;
        } finally {
            DbConnectionManager.closeConnection(pstmt, con);
        }
    }

    public static boolean del(String tid) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DbConnectionManager.getConnection();
            pstmt = con.prepareStatement(DEL_TASK);
            pstmt.setString(1, tid);
            pstmt.executeUpdate();

            return true;
        } catch (SQLException sqle) {
            // Log error
            return false;
        } finally {
            DbConnectionManager.closeConnection(pstmt, con);
        }
    }

}
