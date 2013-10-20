package wetodo.dao;

import org.jivesoftware.database.DbConnectionManager;
import wetodo.model.Room;
import wetodo.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {
    private static final String LIST_ROOM = "select t1.roomID,t1.roomJID,t1.jid userjid,t1.subject,t1.description,t1.creationDate,t2.jid ownerjid from (select r.roomID,r.roomJID,m.jid,r.subject,r.description,r.creationDate from ofMucRoom r,(select jid,roomID from ofMucMember union ALL SELECT jid,roomid from ofMucAffiliation) m where r.roomId = m.roomid and m.jid = ?)t1,ofMucAffiliation t2 where t1.roomID = t2.roomID and t2.affiliation = 10";
    private static final String FIND_BY_ROOMJID_DETAIL = "select t1.roomID,t1.roomJID,t1.subject,t1.description,t1.creationDate,t2.jid ownerjid from (select roomID,roomJID,subject,description,creationDate from ofMucRoom where roomJID = ?) t1,ofMucAffiliation t2 where t1.roomID = t2.roomID and t2.affiliation = 10";
    private static final String FETCH_MEMBER_LIST = "select userjid,name from (select userjid from (select roomID,roomJID from ofMucRoom)t1,(select jid userjid,roomID from ofMucMember union ALL SELECT jid,roomid from ofMucAffiliation)t2 where t1.roomID = t2.roomID and roomJID = ?)t3 left join ofUser t4 on t3.userjid = concat(t4.username,'@mbp.local')";

    public static List<Room> list(String jid) {
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
                room.setRoomJid(rs.getString(2));
                room.setJid(rs.getString(3));
                room.setSubject(rs.getString(4));
                room.setDescription(rs.getString(5));
                room.setCreationdate(new Timestamp(Long.parseLong(rs.getString(6))));

                User owner = new User();
                owner.setJID(rs.getString(7));
                room.setOwner(owner);

                list.add(room);
            }
            return list;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            DbConnectionManager.closeConnection(pstmt, con);
        }
    }

    public static Room findByRoomJidDetail(String roomJid) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DbConnectionManager.getConnection();
            pstmt = con.prepareStatement(FIND_BY_ROOMJID_DETAIL);
            pstmt.setString(1, roomJid);
            System.out.println(pstmt);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Room room = new Room();
                room.setRoomid(rs.getInt(1));
                room.setRoomJid(rs.getString(2));
                room.setSubject(rs.getString(3));
                room.setDescription(rs.getString(4));
                room.setCreationdate(new Timestamp(Long.parseLong(rs.getString(5))));

                User owner = new User();
                owner.setJID(rs.getString(6));
                room.setOwner(owner);

                return room;
            }
            return null;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            DbConnectionManager.closeConnection(pstmt, con);
        }
    }

    public static List<User> fetchMemberList(String roomId) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DbConnectionManager.getConnection();
            pstmt = con.prepareStatement(FETCH_MEMBER_LIST);
            pstmt.setString(1, roomId);
            ResultSet rs = pstmt.executeQuery();
            List<User> list = new ArrayList<User>();
            while (rs.next()) {

                User member = new User();
                member.setJID(rs.getString(1));
                member.setName(rs.getString(2));

                list.add(member);
            }
            return list;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            DbConnectionManager.closeConnection(pstmt, con);
        }
    }

}
