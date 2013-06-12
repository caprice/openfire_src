package wetodo;

import org.jivesoftware.database.DbConnectionManager;

import java.sql.SQLException;
import java.util.ArrayList;

public class MUCPersistenceManager {
    public static List<String> getRoomIDsByUserJid(String userJid) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<String> roomIDs = new ArrayList<String>();
        try {
            con = DbConnectionManager.getConnection();
            pstmt = con.prepareStatement(LOAD_ROOMIDS_BY_JID);
            pstmt.setString(1, userJid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String roomId = rs.getString("roomID");
                roomIDs.add(roomId);
            }
        } catch (SQLException sqle) {
            Log.error(sqle.getMessage(), sqle);
        } finally {
            DbConnectionManager.closeConnection(rs, pstmt, con);
        }
        return roomIDs;
    }

    public static String getRoomNameByRoomId(String roomId) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String roomName = null;
        try {
            con = DbConnectionManager.getConnection();
            pstmt = con.prepareStatement(LOAD_ROOMNAME_BY_ROOMID);
            pstmt.setString(1, roomId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                roomName = rs.getString("name");

            }
        } catch (SQLException sqle) {
            Log.error(sqle.getMessage(), sqle);
        } finally {
            DbConnectionManager.closeConnection(rs, pstmt, con);
        }
        return roomName;
    }


    public static String getServiceIdByRoomId(String roomId) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String serviceID = null;
        try {
            con = DbConnectionManager.getConnection();
            pstmt = con.prepareStatement(LOAD_SERVICEID_BY_ROOMID);
            pstmt.setString(1, roomId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                serviceID = rs.getString("serviceID");

            }
        } catch (SQLException sqle) {
            Log.error(sqle.getMessage(), sqle);
        } finally {
            DbConnectionManager.closeConnection(rs, pstmt, con);
        }
        return serviceID;
    }

    public static String getNickNameByJId(String userJid) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String nick = null;
        try {
            con = DbConnectionManager.getConnection();
            pstmt = con.prepareStatement(LOAD_NICKNAME_BY_JID);
            pstmt.setString(1, userJid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                nick = rs.getString("nickname");
            }
        } catch (SQLException sqle) {
            Log.error(sqle.getMessage(), sqle);
        } finally {
            DbConnectionManager.closeConnection(rs, pstmt, con);
        }
        return nick;
    }


    public static RoomInfo getRoomInfoByRoomId(String roomId) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        RoomInfo rminf = null;
        try {
            con = DbConnectionManager.getConnection();
            pstmt = con.prepareStatement(LOAD_ROOMINFO_BY_ROOMID);
            pstmt.setString(1, roomId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                String serviceID = rs.getString("serviceID");
                String name = rs.getString("name");
                String naturalName = rs.getString("naturalName");
                String description = rs.getString("description");
                rminf = new RoomInfo(serviceID, name, naturalName, description);
            }
        } catch (SQLException sqle) {
            Log.error(sqle.getMessage(), sqle);
        } finally {
            DbConnectionManager.closeConnection(rs, pstmt, con);
        }
        return rminf;
    }


    public static List<String> getMembersByRoomId(String roomId) {
        // TODO Auto-generated method stub
        List<String> members = new ArrayList<String>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = DbConnectionManager.getConnection();
            pstmt = con.prepareStatement(LOAD_JIDS_BY_ROOMID);
            pstmt.setString(1, roomId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String jid = rs.getString("jid");
                members.add(jid);
            }
        } catch (SQLException sqle) {
            Log.error(sqle.getMessage(), sqle);
        } finally {
            DbConnectionManager.closeConnection(rs, pstmt, con);
        }
        return members;
    }
}
