package wetodo.dao;

import org.jivesoftware.database.DbConnectionManager;
import wetodo.model.TaskGroup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskGroupDAO {
    private static final String INSERT_TASKGROUP = "INSERT INTO wtdTaskGroup (id, tgid, roomid, name, version, create_date, modify_date) VALUES (null, ?, ?, ?, ?, ?, ?)";
    private static final String LIST_TASKGROUP = "SELECT * from wtdTaskGroup where roomid = ?";
    private static final String FIND_TASKGROUP = "SELECT * from wtdTaskGroup where tgid = ?";
    private static final String UPDATE_VERSION = "UPDATE wtdTaskGroup set version = version + 1 where tgid = ?";
    private static final String MODIFY_TASKGROUP = "UPDATE wtdTaskGroup set name = ?, version = version + 1, modify_date = ? where tgid = ? and roomid = ?";
    private static final String DEL_TASKGROUP = "DELETE from wtdTaskGroup where tgid = ?";

    public static TaskGroup add(TaskGroup taskGroup) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DbConnectionManager.getConnection();
            pstmt = con.prepareStatement(INSERT_TASKGROUP);
            pstmt.setString(1, taskGroup.getTgid());
            pstmt.setString(2, taskGroup.getRoomid());
            pstmt.setString(3, taskGroup.getName());
            pstmt.setInt(4, taskGroup.getVersion());
            pstmt.setTimestamp(5, taskGroup.getCreate_date());
            pstmt.setTimestamp(6, taskGroup.getModify_date());
            pstmt.executeUpdate();

            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) {
                taskGroup.setId(keys.getInt(1));
                return taskGroup;
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

    public static List<TaskGroup> list(String roomid) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DbConnectionManager.getConnection();
            pstmt = con.prepareStatement(LIST_TASKGROUP);
            pstmt.setString(1, roomid);
            ResultSet rs = pstmt.executeQuery();
            List<TaskGroup> list = new ArrayList<TaskGroup>();
            while (rs.next()) {
                TaskGroup taskGroup = new TaskGroup();
                taskGroup.setId(rs.getInt(1));
                taskGroup.setTgid(rs.getString(2));
                taskGroup.setRoomid(rs.getString(3));
                taskGroup.setName(rs.getString(4));
                taskGroup.setVersion(rs.getInt(5));
                taskGroup.setCreate_date(rs.getTimestamp(6));
                taskGroup.setModify_date(rs.getTimestamp(7));
                list.add(taskGroup);
            }
            return list;
        } catch (SQLException sqle) {
            return null;
        } finally {
            DbConnectionManager.closeConnection(pstmt, con);
        }
    }

    public static TaskGroup find(String tgid) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DbConnectionManager.getConnection();
            pstmt = con.prepareStatement(FIND_TASKGROUP);
            pstmt.setString(1, tgid);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                TaskGroup taskGroup = new TaskGroup();
                taskGroup.setId(rs.getInt(1));
                taskGroup.setTgid(rs.getString(2));
                taskGroup.setRoomid(rs.getString(3));
                taskGroup.setName(rs.getString(4));
                taskGroup.setVersion(rs.getInt(5));
                taskGroup.setCreate_date(rs.getTimestamp(6));
                taskGroup.setModify_date(rs.getTimestamp(7));
                return taskGroup;
            }
            return null;
        } catch (SQLException sqle) {
            return null;
        } finally {
            DbConnectionManager.closeConnection(pstmt, con);
        }
    }

    public static void updateVersion(String tgid) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DbConnectionManager.getConnection();
            pstmt = con.prepareStatement(UPDATE_VERSION);
            pstmt.setString(1, tgid);
            pstmt.executeUpdate();
        } catch (SQLException sqle) {
            // Log error
        } finally {
            DbConnectionManager.closeConnection(pstmt, con);
        }
    }

    public static boolean modify(TaskGroup taskGroup) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DbConnectionManager.getConnection();
            pstmt = con.prepareStatement(MODIFY_TASKGROUP);
            pstmt.setString(1, taskGroup.getName());
            pstmt.setTimestamp(2, taskGroup.getModify_date());
            pstmt.setString(3, taskGroup.getTgid());
            pstmt.setString(4, taskGroup.getRoomid());
            pstmt.executeUpdate();

            return true;

        } catch (SQLException sqle) {
            // Log error
            return false;
        } finally {
            DbConnectionManager.closeConnection(pstmt, con);
        }
    }

    public static boolean del(String tgid) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DbConnectionManager.getConnection();
            pstmt = con.prepareStatement(DEL_TASKGROUP);
            pstmt.setString(1, tgid);
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
