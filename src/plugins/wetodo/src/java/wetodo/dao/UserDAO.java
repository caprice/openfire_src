package wetodo.dao;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.jivesoftware.database.DbConnectionManager;
import wetodo.conf.MucConf;
import wetodo.model.User;

import java.sql.Connection;
import java.sql.Timestamp;

public class UserDAO {
    private static final String FIND_BY_USERNAME = "select * from ofUser where username = ?";
    private static final String UPDATE = "update ofUser set vip_expire = ?, vip = ? where username = ?";

    public static User findByUsername(String username) {
        Connection conn = null;
        try {
            conn = DbConnectionManager.getConnection();
            QueryRunner qRunner = new QueryRunner();
            User user = (User) qRunner.query(conn,
                    FIND_BY_USERNAME,
                    new BeanHandler(Class.forName("wetodo.model.User")),
                    new Object[]{username});
            if (user != null) {
                user.setJID(user.getUsername() + "@" + MucConf.SERVER);
            }
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn);
        }
        return null;
    }

    public static void increaseVip(String username, int increaseDays) {
        long increaseMillis = (long) increaseDays * 3600 * 24 * 1000;
        Timestamp expireNew;
        Connection conn = null;
        try {
            conn = DbConnectionManager.getConnection();
            QueryRunner qRunner = new QueryRunner();
            User user = UserDAO.findByUsername(username);
            if (user == null) {
                return;
            }
            long vipExpire = user.getVip_expire().getTime();
            long now = System.currentTimeMillis();
            if (now > vipExpire) {
                expireNew = new Timestamp(now + increaseMillis);
            } else {
                expireNew = new Timestamp(vipExpire + increaseMillis);
            }

            qRunner.update(conn, UPDATE, new Object[]{
                    expireNew,
                    1,
                    username
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

}
