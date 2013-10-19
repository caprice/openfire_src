package wetodo.dao;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.jivesoftware.database.DbConnectionManager;
import wetodo.conf.MucConf;
import wetodo.model.User;

import java.sql.Connection;

public class UserDAO {
    private static final String FIND_BY_USERNAME = "select * from ofUser where username = ?";

    public static User findByUsername(String username) {
        Connection conn = null;
        try {
            conn = DbConnectionManager.getConnection();
            QueryRunner qRunner = new QueryRunner();
            User user = (User) qRunner.query(conn,
                    FIND_BY_USERNAME,
                    new BeanHandler(Class.forName("wetodo.model.User")),
                    new Object[]{username});
            user.setJID(user.getUsername() + "@" + MucConf.SERVER);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn);
        }
        return null;
    }

}
