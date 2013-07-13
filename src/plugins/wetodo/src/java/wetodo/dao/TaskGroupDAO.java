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
    private static final String INSERT_TASKGROUP = "INSERT INTO wtdTaskGroup (tgid, roomid, name, version, create_date, modify_date) VALUES (null, ?, ?, ?, ?, ?)";
    private static final String LIST_TASKGROUP = "SELECT * from wtdTaskGroup where roomid = ?";

    public static TaskGroup add(TaskGroup taskGroup) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DbConnectionManager.getConnection();
            pstmt = con.prepareStatement(INSERT_TASKGROUP);
            pstmt.setInt(1, taskGroup.getRoomid());
            pstmt.setString(2, taskGroup.getName());
            pstmt.setInt(3, taskGroup.getVersion());
            pstmt.setDate(4, taskGroup.getCreate_date());
            pstmt.setDate(5, taskGroup.getModify_date());
            pstmt.executeUpdate();

            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) {
                taskGroup.setTgid(keys.getInt(1));
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

    public static List<TaskGroup> list(int roomid) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DbConnectionManager.getConnection();
            pstmt = con.prepareStatement(LIST_TASKGROUP);
            pstmt.setInt(1, roomid);
            ResultSet rs = pstmt.executeQuery();
            List<TaskGroup> list = new ArrayList<TaskGroup>();
            while (rs.next()) {
                TaskGroup taskGroup = new TaskGroup();
                taskGroup.setTgid(rs.getInt(1));
                taskGroup.setRoomid(rs.getInt(2));
                taskGroup.setName(rs.getString(3));
                taskGroup.setVersion(rs.getInt(4));
                taskGroup.setCreate_date(rs.getDate(5));
                taskGroup.setModify_date(rs.getDate(6));
                list.add(taskGroup);
            }
            return list;
        } catch (SQLException sqle) {
            return null;
        } finally {
            DbConnectionManager.closeConnection(pstmt, con);
        }
    }
}
