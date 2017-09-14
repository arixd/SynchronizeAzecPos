/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.util.webservice;

import com.google.gson.Gson;
import id.my.berkah.util.controller.Model;
import id.my.berkah.util.implement.UtilImpl;
import id.my.berkah.util.model.AuthBURestoreModel;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ari Ganteng
 */
@WebServlet(name = "AuthBURestore", urlPatterns = {"/AuthBURestore"})
public class AuthBURestore extends HttpServlet {

    Model model = new Model();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
        response.setContentType("application/json");
        Gson gson=new Gson();
        Reply reply=new Reply();
        String requestType=request.getMethod();
        
        String InBuCode=null;
        if(!requestType.equals("POST"))
        {
            reply.setStatusCode("405");
            reply.setOutError("");
            reply.setOutMessage("Request Method hanya bisa POST");
            response.getOutputStream().print(gson.toJson(reply));
            response.getOutputStream().flush();
        }else
        {
            
            try {
                InBuCode=request.getParameter("InBuCode");
                if(InBuCode!="" && !InBuCode.equals(""))
                {                   
                    AuthBURestoreModel obj=new AuthBURestoreModel();
                    obj.setInBuCode(InBuCode);
                    UtilImpl services=new UtilImpl();
                    AuthBURestoreModel out=(AuthBURestoreModel)services.getAuthBURestore(obj);
                    if((out.getOutError()!=null) && (!out.getOutError().equals("")))
                    {
                        if(out.getOutError().equals("1")){
                            reply.setStatusCode("200");
                            reply.setOutError(out.getOutError());
                            reply.setOutMessage(out.getOutMessage());
                            response.getOutputStream().print(gson.toJson(reply));
                            response.getOutputStream().flush();
                        }else{
                            reply.setStatusCode("200");
                            reply.setOutError(out.getOutError());
                            reply.setOutMessage(out.getOutMessage());
                            response.getOutputStream().print(gson.toJson(reply));
                            response.getOutputStream().flush();
                        }
                    }else{
                        reply.setStatusCode("404");
                        reply.setOutError("");
                        reply.setOutMessage("Return value null form database server");
                        response.getOutputStream().print(gson.toJson(reply));
                        response.getOutputStream().flush();
                    }
                }else{
                    reply.setStatusCode("404");
                    reply.setStatusCode("");
                    reply.setOutMessage("Parameter from client is null");
                    response.getOutputStream().print(gson.toJson(reply));
                    response.getOutputStream().flush();
                }
            } catch (Exception e) {
            }
        }
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

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }

    class Reply{
         String statusCode,OutError, OutMessage;
        
        public String getOutError() {
            return OutError;
        }

        public String getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(String statusCode) {
            this.statusCode = statusCode;
        }

        public void setOutError(String OutError) {
            this.OutError = OutError;
        }

        public String getOutMessage() {
            return OutMessage;
        }

        public void setOutMessage(String OutMessage) {
            this.OutMessage = OutMessage;
        }
    }

}
