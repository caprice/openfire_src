package wetodo.dao;

import org.jivesoftware.database.DbConnectionManager;
import wetodo.model.TaskGroup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TaskGroupDAO {
    private static final String INSERT_TASKGROUP = "INSERT INTO wtdTaskGroup (tgid, roomid, name, version, create_date, modify_date) VALUES (null, ?, ?, ?, ?, ?)";

    public static void add(TaskGroup taskGroup) {
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
        } catch (SQLException sqle) {
            // Log error
        } finally {
            DbConnectionManager.closeConnection(pstmt, con);
        }
    }
}
