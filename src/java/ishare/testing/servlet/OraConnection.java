package ishare.testing.servlet;

import java.sql.SQLException;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.driver.OracleSQLException;
import oracle.jdbc.pool.OracleDataSource;

public class OraConnection {
    public OracleConnection OraConnect()
            throws SQLException {
        try {
            GetConfig getXML = new GetConfig();
            getXML.getAccess();
            OracleDataSource ods = new OracleDataSource();
            String url = "jdbc:oracle:thin:@" + getXML.getHOST() + ":" + getXML.getPORT() + ":" + getXML.getSID();
            ods.setUser(getXML.getUSER());
            ods.setPassword(getXML.getPASSWORD());
            ods.setURL(url);
            OracleConnection conn = (OracleConnection) ods.getConnection();
            return conn;
        } catch (OracleSQLException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }
//    public OracleConnection OraConnect()
//            throws SQLException {
//        try {
//            
//            OracleDataSource ods = new OracleDataSource();
//            String url = "jdbc:oracle:thin:@" +"192.168.3.33" + ":" + "2016" + ":" + "DEVL";
//            ods.setUser("APPS");
//            ods.setPassword("11GAPPS");
//            ods.setURL(url);
//            OracleConnection conn = (OracleConnection) ods.getConnection();
//            return conn;
//        } catch (OracleSQLException ex) {
//            ex.printStackTrace();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//
//        return null;
//    }
}
