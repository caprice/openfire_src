package wetodo.dao;

import org.jivesoftware.database.DbConnectionManager;
import wetodo.model.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {
    private static final String LIST_ROOM = "select r.roomID,m.jid,r.name,r.description,r.creationDate from ofMucRoom r,(select jid,roomID from ofMucMember union ALL SELECT jid,roomid from ofMucAffiliation) m where r.roomId = m.roomid and m.jid = ?";
    private static final String FIND_BY_ROOMJID = "select roomID, roomJID, name, description, creationDate from ofMucRoom where roomJID = ?";

    public static List<Room> list(String jid) {
        System.out.println(jid);
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DbConnectionManager.getConnection();
            pstmt = con.prepareStatement(LIST_ROOM);
            pstmt.setString(1, jid);
            System.out.println(pstmt);
            ResultSet rs = pstmt.executeQuery();
            List<Room> list = new ArrayList<Room>();
            while (rs.next()) {
                Room room = new Room();
                room.setRoomid(rs.getInt(1));
                room.setJid(rs.getString(2));
                room.setSubject(rs.getString(3));
                room.setDescription(rs.getString(4));
                room.setCreationdate(new Timestamp(Long.parseLong(rs.getString(5))));

                list.add(room);
            }
            return list;
        } catch (SQLException sqle) {
            return null;
        } finally {
            DbConnectionManager.closeConnection(pstmt, con);
        }
    }

    public static Room findByRoomJid(String roomJid) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DbConnectionManager.getConnection();
            pstmt = con.prepareStatement(FIND_BY_ROOMJID);
            pstmt.setString(1, roomJid);
            System.out.println(pstmt);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Room room = new Room();
                room.setRoomid(rs.getInt(1));
                room.setJid(rs.getString(2));
                room.setSubject(rs.getString(3));
                room.setDescription(rs.getString(4));
                room.setCreationdate(new Timestamp(Long.parseLong(rs.getString(5))));
                return room;
            }
            return null;
        } catch (SQLException sqle) {
            return null;
        } finally {
            DbConnectionManager.closeConnection(pstmt, con);
        }
    }

}
