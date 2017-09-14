package ishare.testing.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.driver.OracleSQLException;

@WebServlet(name = "SummaryDownload", urlPatterns = {"/SummaryDownload"})
public class SummaryDownload extends HttpServlet {

    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    String command;
    String addr;
    String buid;
    String uid;
    String idTable;
    String pesanKeluar;
    String namaFile;
    String GenID;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestType = request.getMethod();

        this.addr = request.getRemoteAddr();

        Boolean waras = Boolean.valueOf(true);

        if (requestType.equals("POST")) {

        }
    }

    String getPostData(HttpServletRequest req) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = req.getReader();
            reader.mark(10000);
            String line;
            do {
                line = reader.readLine();
                sb.append(line).append("\n");
            } while (line != null);

            reader.reset();
        } catch (IOException e) {
            System.out.println("...   [ERROR] : " + e.getMessage());
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    public static void main(String[] args) {
        SummaryDownload summaryDownload = new SummaryDownload();
        try {
            summaryDownload.getSummaryDownloadDaily();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getSummaryDownloadDaily() throws SQLException {
        System.out.println("Summary Download Daily");
        int result = 1;
        try {
            OraConnection oco = new OraConnection();
            OracleConnection conn = oco.OraConnect();
            if (conn != null) {
                try {
                    String query = "Select x.min_id,x.max_id,\n"
                            + "(\n"
                            + "Select y.alias_name\n"
                            + "From  synch_table_alias y \n"
                            + "Where  y.table_name = x.source\n"
                            + ") source_description  \n"
                            + ",x.event_name,x.count_rows,x.source\n"
                            + "From\n"
                            + "(\n"
                            + "Select min(id) min_id,max(id) max_id,a.source,a.event_name,count(1) count_rows\n"
                            + "From  pos_datadownload a\n"
                            + "Where ID >= 31864 \n"
                            + "Group by    a.source,a.event_name    \n"
                            + ") x\n"
                            + "Order\n"
                            + "by x.min_id";

                    Statement s = conn.createStatement();
                    s.executeQuery(query);
                    ResultSet rs = s.getResultSet();

//                    System.out.println("meta data "+rs.getMetaData().getColumnName(0));
                    System.out.println("meta data " + rs.getMetaData().getColumnCount());
                    int totalColumn = rs.getMetaData().getColumnCount();
                    for (int i = 1; i <= totalColumn; i++) {
                        System.out.print(rs.getMetaData().getColumnName(i) + " ");
                    }
                    System.out.println();
                    result = 0;
                    while (rs.next()) {
                        for (int i = 1; i <= totalColumn; i++) {
                            System.out.print(rs.getObject(i) + " ");
                        }
                        System.out.println();
                    }
                } catch (OracleSQLException oex) {
                    oex.printStackTrace();
                    this.pesanKeluar = new StringBuilder().append(oex.getMessage()).append(this.buid).toString();
                    result = 1;
                } finally {
                    conn.close();
                }
            }
        } catch (OracleSQLException oex) {
            oex.printStackTrace();
            this.pesanKeluar = new StringBuilder().append(oex.getMessage()).append(this.buid).toString();
            result = 1;
        }
        return result;
    }
}
