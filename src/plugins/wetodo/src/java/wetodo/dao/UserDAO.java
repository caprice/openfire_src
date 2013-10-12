package wetodo.dao;

import org.jivesoftware.database.DbConnectionManager;
import wetodo.conf.MucConf;
import wetodo.model.User;

import java.sql.*;

public class UserDAO {
    private static final String FIND_BY_USERNAME = "select username,name,email,creationDate,modificationDate,vip_expire,vip from ofUser where username = ?";

    public static User findByUsername(String username) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DbConnectionManager.getConnection();
            pstmt = con.prepareStatement(FIND_BY_USERNAME);
            pstmt.setString(1, username);
            System.out.println(pstmt);
            ResultSet rs = pstmt.executeQuery();
            while (true) {
                if (!(rs.next())) break;

                User user = new User();
                user.setUsername(rs.getString(1));
                user.setName(rs.getString(2));
                user.setEmail(rs.getString(3));
                user.setCreationdate(new Timestamp(Long.parseLong(rs.getString(4))));
                user.setModificationdate(new Timestamp(Long.parseLong(rs.getString(5))));
                user.setVipExpire(rs.getInt(6));
                user.setVip(rs.getInt(7));
                user.setJID(user.getUsername() + "@" + MucConf.SERVER);

                return user;
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
}
