package wetodo.dao;

import org.jivesoftware.database.DbConnectionManager;
import wetodo.model.Pay;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PayDAO {

    private static final String FIND_BY_RECEIPT = "select id,username,receipt,iap_id,create_date,modify_date from wtdPay where receipt = ?";
    private static final String ADD = "insert into wtdPay (id,username,receipt,iap_id,create_date,modify_date) values (null,?,?,?,?,?)";

    public static Pay findByReceipt(String receipt) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DbConnectionManager.getConnection();
            pstmt = con.prepareStatement(FIND_BY_RECEIPT);
            pstmt.setString(1, receipt);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Pay pay = new Pay();
                pay.setId(rs.getInt(1));
                pay.setUsername(rs.getString(2));
                pay.setReceipt(rs.getString(3));
                pay.setIapId(rs.getString(4));
                pay.setCreateDate(rs.getTimestamp(5));
                pay.setModifyDate(rs.getTimestamp(6));

                return pay;
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

    public static Pay add(Pay pay) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DbConnectionManager.getConnection();
            pstmt = con.prepareStatement(ADD);
            pstmt.setString(1, pay.getUsername());
            pstmt.setString(2, pay.getReceipt());
            pstmt.setString(3, pay.getIapId());
            pstmt.setTimestamp(4, pay.getCreateDate());
            pstmt.setTimestamp(5, pay.getModifyDate());

            pstmt.executeUpdate();

            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) {
                pay.setId(keys.getInt(1));
                return pay;
            } else {
                return null;
            }
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
