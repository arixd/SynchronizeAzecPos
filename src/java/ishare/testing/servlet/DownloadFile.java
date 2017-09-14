package ishare.testing.servlet;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DownloadFile", urlPatterns = {"/DownloadFile"})
public class DownloadFile extends HttpServlet {

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
        
        String fileName = "recovery.zip";
        String buCode = request.getParameter("buCode");

         System.out.println("buCode:"+buCode);

        String buId = "0";

        try {
            if (waras.booleanValue()) {
                downloadFile(response, Config.getConfig().getProperty("dirRecovery") + fileName);
            } else {
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("Cannot access from GET method<br>" + this.pesanKeluar);
                out.close();
            }
        } catch (Exception ex) {
            System.out.println("...   [ERROR] : " + ex.getMessage());
            ex.printStackTrace();
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println(ex.getMessage());
            out.close();
        }
    }

    public void downloadFile(HttpServletResponse response, String dirPatchJar) {
        ServletOutputStream outStream = null;
        try {
//                String dirPatchJar = "/home/dadan/PatchJar/posclient.jar";
//        String dirPatchJar = "D:\\azec\\patchJar\\posclient.jar";
            System.out.println("dirPatchJar : " + dirPatchJar);
            File file = new File(dirPatchJar);
            this.namaFile = file.getName();
            int length = 0;
            outStream = response.getOutputStream();
            ServletContext context = getServletConfig().getServletContext();
            response.setContentLength((int) file.length());
            System.out.println();
            System.out.println("Total File : " + (int) file.length());
            response.setContentType("application/zip");
//                response.setContentType("application/jar");
            response.setHeader("Content-Disposition", new StringBuilder().append("attachment; filename=").append(this.namaFile).toString());

//            response.setHeader("Content-Disposition", new StringBuilder().append("attachment; filename=\"").append(this.namaFile).append(".jar").append("\"").toString());
            byte[] byteBuffer = new byte[4096];
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            while ((in != null) && ((length = in.read(byteBuffer)) != -1)) {
                outStream.write(byteBuffer, 0, length);
            }
            in.close();
            outStream.close();
            System.out.println("finish");
        } catch (IOException ex) {
            Logger.getLogger(DownloadFile.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                outStream.close();
            } catch (IOException ex) {
                Logger.getLogger(DownloadFile.class.getName()).log(Level.SEVERE, null, ex);
            }
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
}
