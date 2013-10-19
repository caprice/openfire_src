package wetodo.dao;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.jivesoftware.database.DbConnectionManager;
import wetodo.model.Pay;

import java.math.BigInteger;
import java.sql.Connection;

public class PayDAO {

    private static final String FIND_BY_RECEIPT = "select * from wtdPay where receipt = ?";
    private static final String CREATE = "insert into wtdPay (id,username,receipt,iap_id,create_date,modify_date) values (null,?,?,?,?,?)";

    public static Pay findByReceipt(String receipt) {
        Connection conn = null;
        try {
            conn = DbConnectionManager.getConnection();
            QueryRunner qRunner = new QueryRunner();
            Pay pay = (Pay) qRunner.query(conn,
                    FIND_BY_RECEIPT,
                    new BeanHandler(Class.forName("wetodo.model.Pay")),
                    new Object[]{receipt});
            return pay;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn);
        }
        return null;
    }

    public static Pay create(Pay pay) {
        Connection conn = null;
        try {
            conn = DbConnectionManager.getConnection();
            QueryRunner qRunner = new QueryRunner();
            qRunner.update(conn, CREATE, new Object[]{
                    pay.getUsername(),
                    pay.getReceipt(),
                    pay.getIap_id(),
                    pay.getCreate_date(),
                    pay.getModify_date()
            });
            int id = ((BigInteger) qRunner.query(conn, "select last_insert_id()", new ScalarHandler(1))).intValue();
            pay.setId(id);
            return pay;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn);
        }
        return null;
    }
}
