package wetodo.dao;

import org.jivesoftware.database.DbConnectionManager;
import wetodo.model.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {
    private static final String LIST_ROOM = "SELECT * FROM ofMucMember WHERE jid = ?";

    public static List<Room> list(String username) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DbConnectionManager.getConnection();
            pstmt = con.prepareStatement(LIST_ROOM);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            List<Room> list = new ArrayList<Room>();
            while (rs.next()) {
                Room room = new Room();
                room.setJid(rs.getInt(1));
                room.setSubject(rs.getString(2));
                room.setDescription(rs.getString(3));
                room.setCreationdate(rs.getTimestamp(4));

                list.add(room);
            }
            return list;
        } catch (SQLException sqle) {
            return null;
        } finally {
            DbConnectionManager.closeConnection(pstmt, con);
        }
    }

}
