package wetodo.dao;

import org.jivesoftware.database.DbConnectionManager;
import wetodo.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private static final String LIST = "select id,iap_id,name,month,create_date,modify_date from wtdProduct";

    public static List<Product> list() {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DbConnectionManager.getConnection();
            pstmt = con.prepareStatement(LIST);
            ResultSet rs = pstmt.executeQuery();
            List<Product> list = new ArrayList<Product>();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt(1));
                product.setIapId(rs.getString(2));
                product.setName(rs.getString(3));
                product.setMonth(rs.getInt(4));
                product.setCreationdate(rs.getTimestamp(5));
                product.setModificationdate(rs.getTimestamp(6));

                list.add(product);
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
