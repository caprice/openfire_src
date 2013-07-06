package wetodo.model;

import org.jivesoftware.database.DbConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TaskGroup {
    private static final String INSERT_TASKGROUP = "INSERT INTO wtdTaskGroup (tgid, roomid, name) VALUES (null, ?, ?)";

    public static void add(int roomID, String taskGroupName) {
        Connection con = null;
        PreparedStatement pstmt = null;
        String INSERT_TASKGROUP = "INSERT INTO wtdTaskGroup (tgid, roomid, name) VALUES (null, ?, ?)";
        try {
            con = DbConnectionManager.getConnection();
            pstmt = con.prepareStatement(INSERT_TASKGROUP);
            pstmt.setInt(1, roomID);
            pstmt.setString(2, taskGroupName);
            pstmt.executeUpdate();
        } catch (SQLException sqle) {
            // Log error
        } finally {
            DbConnectionManager.closeConnection(pstmt, con);
        }
    }
}
