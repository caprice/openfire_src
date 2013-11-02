package wetodo.dao;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.jivesoftware.database.DbConnectionManager;
import wetodo.model.Product;

import java.sql.Connection;
import java.util.List;

public class ProductDAO {
    private static final String ALL = "select * from wtdProduct";
    private static final String FIND_BY_IAPID = "select * from wtdProduct where iap_id = ?";

    public static List<Product> all() {
        Connection conn = null;
        try {
            conn = DbConnectionManager.getConnection();
            QueryRunner qRunner = new QueryRunner();
            List<Product> list = (List<Product>) qRunner.query(conn,
                    ALL, new BeanListHandler(Class
                    .forName("wetodo.model.Product")));
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn);
        }
        return null;
    }

    public static Product findByIapId(String iapId) {
        Connection conn = null;
        try {
            conn = DbConnectionManager.getConnection();
            QueryRunner qRunner = new QueryRunner();
            Product product = (Product) qRunner.query(conn,
                    FIND_BY_IAPID,
                    new BeanHandler(Class.forName("wetodo.model.Product")),
                    new Object[]{iapId});
            return product;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn);
        }
        return null;
    }
}
