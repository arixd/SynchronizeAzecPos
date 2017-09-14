/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ishare.testing.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleConnection;

/**
 *
 * @author Shatria
 */
@WebServlet(name = "ErrorReport", urlPatterns = {"/ErrorReport"})
public class ErrorReport extends HttpServlet {

    private String title = "ErrorReport";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        Reply rep = new Reply();
        String requestType = request.getMethod();
        boolean ok = true;

        String KEY_VALUE = null;
        String ALIAS_TABLE = null;
        String TABLE_NAME = null;
        String KEY_FIELD = null;
        String BU_ID = null;
        String LINE_NUMBER = null;

        if (!requestType.equals("POST")) {
            rep.setStatusCode("1");
            rep.setMessage("Request Method hanya bisa POST");
            response.getOutputStream().print(rep.getMessage());
            response.getOutputStream().flush();
        } else {
            try {
                response.getOutputStream().print("Process");
//                StringBuilder sb = new StringBuilder();
//                String s;
//                while ((s = request.getReader().readLine()) != null) {
//                    sb.append(s);
//                }
                KEY_VALUE = request.getParameter("KEY_VALUE");
                ALIAS_TABLE = request.getParameter("ALIAS_TABLE");
                TABLE_NAME = request.getParameter("TABLE_NAME");
                KEY_FIELD = request.getParameter("KEY_FIELD");
                BU_ID = request.getParameter("BU_ID");
                LINE_NUMBER = request.getParameter("LINE_NUMBER");

                GlobalTools.printLog(title, "KEY_VALUE : " + KEY_VALUE);
                GlobalTools.printLog(title, "ALIAS_TABLE : " + ALIAS_TABLE);
                GlobalTools.printLog(title, "TABLE_NAME : " + TABLE_NAME);
                GlobalTools.printLog(title, "KEY_FIELD : " + KEY_FIELD);
                GlobalTools.printLog(title, "BU_ID : " + BU_ID);
                GlobalTools.printLog(title, "LINE_NUMBER : " + LINE_NUMBER);

                Map map = new HashMap<String, Object>();
                map.put("KEY_VALUE", KEY_VALUE);
                map.put("ALIAS_TABLE", ALIAS_TABLE);
                map.put("TABLE_NAME", TABLE_NAME);
                map.put("KEY_FIELD", KEY_FIELD);
                map.put("BU_ID", BU_ID);
                map.put("LINE_NUMBER", LINE_NUMBER);

                OraConnection oco = new OraConnection();
                Connection conn = oco.OraConnect();
                if (conn != null) {
                    try {
                        String sql = "INSERT INTO download_error_report(\n"
                                + "KEY_VALUE,\n"
                                + "ALIAS_TABLE,\n"
                                + "TABLE_NAME,\n"
                                + "KEY_FIELD,\n"
                                + "BU_ID,\n"
                                + "LINE_NUMBER\n"
                                + ")VALUES(\n"
                                + "?,\n"
                                + "?,\n"
                                + "?,\n"
                                + "?,\n"
                                + "?,\n"
                                + "?\n"
                                + ")";
                        PreparedStatement cstmt = conn.prepareStatement(sql);

                        cstmt.setString(1, KEY_VALUE);
                        cstmt.setString(2, ALIAS_TABLE);
                        cstmt.setString(3, TABLE_NAME);
                        cstmt.setString(4, KEY_FIELD);
                        cstmt.setInt(5, Integer.parseInt(BU_ID));
                        cstmt.setInt(6, Integer.parseInt(LINE_NUMBER));
                        cstmt.executeUpdate();

                    } catch (Exception e) {
                    }
                    rep.setStatusCode("0");
                    rep.setMessage("Success Report");
                }
            } catch (Exception ex) {
                rep.setStatusCode("1");
                rep.setMessage("Fail Report :" + ex.getMessage());
            }
            response.getOutputStream().print("");
            response.getOutputStream().flush();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    class Reply {

        String statusCode, message;

        public String getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(String statusCode) {
            this.statusCode = statusCode;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }
}
