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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
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

@WebServlet(name = "DownLoadRecovery", urlPatterns = {"/DownLoadRecovery"})
public class DownLoadRecovery extends HttpServlet {

    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    String command;
    String addr;
    String buCode;
    String month;
    String year;
//    String buid;
    String uid;
//    String idTable;
    String pesanKeluar;
    String namaFile;
    String GenID;

    GetConfig getConfig = new GetConfig();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            getConfig.getAccess();

            OracleConnection con = null;
            try {
                OraConnection oco = new OraConnection();
                con = oco.OraConnect();

                String query = "select * from global_setting where prompt_name like 'DOWNLOAD_DIRECTORY_APPSHOST'";
                PreparedStatement ps = con.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    getConfig.setStrTempDir(rs.getString(2));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                con.close();
            }
            String tempDir = "";
            if (getConfig.getTempDir().charAt(getConfig.getTempDir().length() - 1) != '/') {
                tempDir = getConfig.getTempDir() + File.separator;
                getConfig.setStrTempDir(tempDir);
            }
            System.out.println("strTempDir: " + getConfig.getTempDir());

            String requestType = request.getMethod();
    
            System.out.println("requestType: " + requestType);

            this.addr = request.getRemoteAddr();
            
            System.out.println("addr: " + this.addr);

            Boolean waras = Boolean.valueOf(true);

            if (requestType.equals("POST")) {
                this.command = getPostData(request).trim();

                String[] component = this.command.split("&");

                String[] cBUID = component[0].split("=");
                String[] cUID = component[1].split("=");
                String[] cMonth = component[2].split("=");
                String[] cYear = component[3].split("=");
                
                

                this.buCode = cBUID[1].replace("\n", "").replace("\r", "").replace("null", "");
                this.uid = cUID[1].replace("\n", "").replace("\r", "").replace("null", "");
                this.month= cMonth[1].replace("\n", "").replace("\r", "").replace("null", "");
                this.year = cYear[1].replace("\n", "").replace("\r", "").replace("null", "");
                
                System.out.println("buCode: "+buCode);
                System.out.println("uid: "+uid);
                System.out.println("month: "+month);
                System.out.println("year: "+year);
//                this.idTable = cIDTBL[1].replace("\n", "").replace("\r", "").replace("null", "")
//                        .replace("%5B", "[").replace("%5D", "]").replace("%2C", ",").replace("%7B", "{").replace("%7D", "}");
            } else if (requestType.equals("GET")) {
                waras = Boolean.valueOf(false);
                this.pesanKeluar = "Request Method hanya bisa POST";
            }

            try {
                if (waras.booleanValue()) {
                    int generateFile = GenerateFile();
                    if (generateFile == 0) {
                        if (new Zip(new StringBuilder().append(getConfig.getTempDir()).append(this.namaFile).toString(), new StringBuilder().append(getConfig.getTempDir()).append(this.namaFile).append(".zip").toString()).zipIt() == 0) {
                            encryptFile(namaFile + ".zip");
                            File file = new File(getConfig.getTempDir(), new StringBuilder().append(this.namaFile).append(".zip").append(".crp").toString());
                            int length = 0;
                            ServletOutputStream outStream = response.getOutputStream();
                            ServletContext context = getServletConfig().getServletContext();
                            String mimetype = context.getMimeType(new StringBuilder().append(this.namaFile).append(".zip").append(".crp").toString());

                            response.setContentType(mimetype);
                            response.setContentLength((int) file.length());

                            response.setContentType("application/zip");
                            response.setHeader("Content-Disposition", new StringBuilder().append("attachment; filename=\"").append(this.namaFile).append(".zip").append("\"").toString());

                            byte[] byteBuffer = new byte[4096];
                            DataInputStream in = new DataInputStream(new FileInputStream(file));

                            while ((in != null) && ((length = in.read(byteBuffer)) != -1)) {
                                outStream.write(byteBuffer, 0, length);
                            }

                            in.close();
                            outStream.close();

                            File f = new File(getConfig.getTempDir(), this.namaFile);
                            f.delete();

                            File f2 = new File(getConfig.getTempDir(), new StringBuilder().append(this.namaFile).append(".crp").toString());
                            f2.delete();
                            System.out.println("finish");
                        }

                    } else if (generateFile == -1) {
                        int length = 0;
                        File file = new File(getConfig.getTempDir(), new StringBuilder().append(this.namaFile).append(".zip").append(".crp").toString());
                        ServletOutputStream outStream = response.getOutputStream();
                        ServletContext context = getServletConfig().getServletContext();
                        String mimetype = context.getMimeType(new StringBuilder().append(this.namaFile).append(".zip").append(".crp").toString());

                        response.setContentType(mimetype);
                        response.setContentLength((int) file.length());

                        response.setContentType("application/zip");
                        response.setHeader("Content-Disposition", new StringBuilder().append("attachment; filename=\"").append(this.namaFile).append(".zip").append("\"").toString());

                        byte[] byteBuffer = new byte[4096];
                        DataInputStream in = new DataInputStream(new FileInputStream(file));

                        while ((in != null) && ((length = in.read(byteBuffer)) != -1)) {
                            outStream.write(byteBuffer, 0, length);
                        }

                        in.close();
                        outStream.close();

                        File f = new File(getConfig.getTempDir(), this.namaFile);
                        f.delete();

                        File f2 = new File(getConfig.getTempDir(), new StringBuilder().append(this.namaFile).append(".crp").toString());
                        f2.delete();
                        System.out.println("finish");
                    } else {
                        response.setContentType("text/html");

                    }
                } else {
                    response.setContentType("text/html");
                }
            } catch (Exception ex) {
                System.out.println("...   [ERROR] : " + ex.getMessage());
                ex.printStackTrace();
                response.setContentType("text/html");
            } finally {

            }
        } catch (Exception ex) {
            ex.printStackTrace();
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

    public int GenerateFile() throws SQLException {
        int resul = 1;
        try {
            OraConnection oco = new OraConnection();
            OracleConnection conn = oco.OraConnect();
//            OracleConnection conn = oco.OraConnect();
            if (conn != null) {
                try {
                    OracleCallableStatement cstmt = (OracleCallableStatement) conn.prepareCall("{call GetFilenameDMLRestoreUtlFull(?,?,?,?,?,?)}");
                    cstmt.setString(1, this.uid);
                    cstmt.setString(2, this.buCode);
                    cstmt.setString(3, this.month);//01
                    cstmt.setString(4, this.year);//2016  
                    cstmt.registerOutParameter(5, 12);
                    cstmt.registerOutParameter(6, 12);
                    cstmt.execute();

                    this.GenID = cstmt.getInt(5)+"";
                    System.out.println("GenID: " + this.GenID);
                    this.namaFile = cstmt.getString(6);
                    System.out.println("namaFile: " + this.namaFile);

                    cstmt.close();
                    if (this.GenID.equals("-1")) {
                        resul = -1;
                    } else {
                        try {

                            Statement s = conn.createStatement();
                            s.executeQuery(new StringBuilder().append("Select SCRIPT From temp_dml Where generate_id = ").append(this.GenID).append(" Order by temp_dml_id").toString());
                            ResultSet rs = s.getResultSet();

                            Writer output = null;
                            File file = new File(getConfig.getTempDir(), this.namaFile);
                            output = new BufferedWriter(new FileWriter(file));

                            while (rs.next()) {
                                output.write(new StringBuilder().append(rs.getString(1)).append("\n").toString());
                            }
                            output.close();

                            resul = 0;
                        } catch (Exception e) {
                            resul = 1;
                        }
                    }

                } catch (Exception oex) {
                    oex.printStackTrace();
                    this.pesanKeluar = new StringBuilder().append(oex.getMessage()).append(this.buCode).toString();
                    resul = 1;
                } finally {
                    conn.close();
                }
            }
        } catch (Exception oex) {
            oex.printStackTrace();
            this.pesanKeluar = new StringBuilder().append(oex.getMessage()).append(this.buCode).toString();
            resul = 1;
        }
        return resul;
    }

    public int encryptFile(String nameFile) throws SQLException {
        int encryption = CipherCrypto.bikinSandi(new StringBuilder().append(getConfig.getTempDir()).append(nameFile).toString(), new StringBuilder().append(getConfig.getTempDir()).append(nameFile).append(".crp").toString());
        return encryption;
    }

//    private int zipFiles(String fileasal, String filezip)
//            throws IOException {
//        try {
//            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(filezip)));
//            byte[] data = new byte[1000];
//            BufferedInputStream in = new BufferedInputStream(new FileInputStream(fileasal));
//            out.putNextEntry(new ZipEntry(fileasal));
//            int count;
//            while ((count = in.read(data, 0, 1000)) != -1) {
//                out.write(data, 0, count);
//            }
//            in.close();
//            out.flush();
//            out.close();
//            return 0;
//        } catch (Exception e) {
//            System.out.println("...   [ERROR] : " + e.getMessage());
//            e.printStackTrace();
//        }
//        return 1;
//    }
}

/* Location         : C:\Users\ikhsan\Documents\ERAFONE2\WEB-INF\classes\
 * Qualified Name   : ishare.testing.servlet.DownLoad
 * JD-Core Version  : 0.6.2
 */
