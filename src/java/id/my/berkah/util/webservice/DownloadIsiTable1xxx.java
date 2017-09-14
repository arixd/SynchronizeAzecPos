/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.util.webservice;

import id.my.berkah.util.config.Config;
import id.my.berkah.util.config.MyBatisConnectionFactory;
import id.my.berkah.util.controller.Model;
import id.my.berkah.util.implement.UtilImpl;
import id.my.berkah.util.model.Qry;
import id.my.berkah.util.tools.CipherCrypto;
import id.my.berkah.util.tools.ZipFiles;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author dadan
 */
@WebServlet(name = "DownloadIsiTablexxxxxxxxxxxxxxx", urlPatterns =
{
    "/DownloadIsiTablexxxxxxxxxxxxxxxxxxx"
})
public class DownloadIsiTable1xxx extends HttpServlet
{

    protected String pesanKeluar, directory;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException
    {

        directory=Config.getConfig().getProperty("dirTemp");
        String requestType = request.getMethod();
        Boolean waras = true;

        if (requestType.equals("GET"))
        {
            waras = false;
//            waras = true;
            this.pesanKeluar = "Request Method hanya bisa POS";
        }

        // PrintWriter out = response.getWriter();
        if (waras)
        {
            //response.setContentType("application/json");
            //Gson gson = new Gson();
//            Properties config = new Properties();
//            adding
            String buId = "";
            String userId = "";
            System.out.println(" Test 1");
            

            try
            {
                StringBuilder sb = new StringBuilder();
                String s;
                while ((s = request.getReader().readLine()) != null)
                    sb.append(s);
                // buid, tanggal

                System.out.println("Request Client :" + sb.toString());
                buId = request.getParameter("BUID");
                userId = request.getParameter("UID");

                System.out.println("BU HERE ...." + buId);
                System.out.println("USER HERE ...." + userId);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("buId", buId);
                map.put("userId", userId);
                Model model = new Model();
                String result = model.downloadMainDaily(map);

//                masukan obj = (masukan) gson.fromJson(sb.toString(), masukan.class);
//                String zipfile = CreateFileFromDownload(directory, obj.getBuid(), obj.getTanggal());
//                String zipfile = CreateFileForDownload(directory, obj.getBuid());//, obj.getTanggal());
                String zipfile = CreateFileForDownload(directory, buId, userId);//, obj.getTanggal());

                encryptFile(zipfile);
                
                File file = new File(new StringBuilder().append(zipfile).append(".crp").toString());
                
                int length = 0;
                ServletOutputStream outStream = response.getOutputStream();
                ServletContext context = getServletConfig().getServletContext();
                String mimetype = context.getMimeType(new StringBuilder().append(zipfile).append(".crp").toString());

                response.setContentType(mimetype);
                response.setContentLength((int) file.length());
                response.setContentType("application/zip");
                response.setHeader("Content-Disposition", new StringBuilder().append("attachment; filename=\"").append(zipfile).append("\"").toString());
                byte[] byteBuffer = new byte[4096];
                DataInputStream in = new DataInputStream(new FileInputStream(file));
                
                while ((in != null) && ((length = in.read(byteBuffer)) != -1))
                {   outStream.write(byteBuffer, 0, length);}
                in.close();
                outStream.close();

                File f = new File(directory, zipfile);
                f.delete();

                File f2 = new File(directory, new StringBuilder().append(zipfile).append(".crp").toString());
                f2.delete();
                System.out.println("finish");

            } catch (Exception e)

            {
                e.printStackTrace();
                response.setContentType("text/html");
                //out.println(e.getMessage());
                //out.close();
            }

        } else
        {
            //response.setContentType("text/html");
            //out.println("Cannot access from GET method<br>" + this.pesanKeluar);
            //out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        processRequest(request, response);
    }

    String getPostData(HttpServletRequest req)
    {
        StringBuilder sb = new StringBuilder();
        try
        {
            BufferedReader reader = req.getReader();
            reader.mark(10000);
            String line;
            do
            {
                line = reader.readLine();
                sb.append(line).append("\n");
            } while (line != null);

            reader.reset();
        } catch (IOException e)
        {
            System.out.println("...   [ERROR] : " + e.getMessage());
            e.printStackTrace();
        }
        return sb.toString();
    }

    /*private String CreateFileFromDownload(String directory, String buid, String tanggal)*/
    String CreateFileForDownload(String directory, String buid, String userId)
    {

        System.out.println(" lewat sinih.......");
        List<String> listFile = new ArrayList<String>();
//        String directory = Config.getConfig().getProperty("dirTemp");
        String buId = buid;
        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyyHHmmss");
        SimpleDateFormat dt = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        SimpleDateFormat paramdate = new SimpleDateFormat("dd-MMM-yyyy");

        UtilImpl pro = new UtilImpl();
        Map map = new HashMap<>();
        map.put("BU", buId);

        try
        {
            Connection conn = MyBatisConnectionFactory.getSqlSessionFactory().openSession().getConnection();

            List<Qry> list = pro.getListTableDownload(map);
            System.out.println("Jumlah Data " + list.size());
            for (int i = 0; i < list.size(); i++)
            {
                Date date = new Date();
                String newDate = df.format(date);
                String parDate = paramdate.format(date);
                String fullName = directory + list.get(i).getTableName() + newDate + ".txt";
                File file = new File(fullName);
                FileWriter writer = new FileWriter(file);

                listFile.add(fullName);

                System.out.println("" + i);
                Statement stmt = conn.createStatement();
                String qrt = list.get(i).getSqlQry().replace("PARAMBU", buId);
                qrt = qrt.replace("PARAMDATE", "'" + parDate + "'");
                qrt = qrt.replace("PARAMUSER", userId);
                System.out.println(qrt);
                ResultSet rs = stmt.executeQuery(qrt);
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                String json = "{\"TableName\":\"" + list.get(i).getTableName() + "\",";
                json = json + "\n\"FieldName\":\"";
                String judul = "";
                int col = 0;
                for (int column = 1; column <= columnCount; column++)
                {
                    judul = judul + metaData.getColumnName(column) + ",";
                    col = col + 1;
                }
                json = json + judul.substring(0, judul.length() - 1) + "\",";

                //System.out.println(list.get(i).getTableName());
                long start = System.currentTimeMillis();

                json = json + "\n\"DATA\":[";
                writer.write(new StringBuilder().append(json).append("\n").toString());

                boolean awal = true;

                int rowdata = 0,max = 3,jumlahBaris = 0,anakKe=1;
                while (rs.next())
                {

                    String isi = "";

                    if (awal)
                    {
                        isi = "{\"Record\":\"";
                        awal = false;
                    } else
                    {
                        isi = ",{\"Record\":\"";
                    }
                    rowdata = 0;
                    for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++)
                    {
                        rowdata++;
         //System.out.println("cell( " + columnIndex + "," + b +") = "+rs.getObject(columnIndex).toString());

                        //if (rs.getObject(columnIndex) != null)
                        //{
                        int type = metaData.getColumnType(columnIndex);
                        System.out.print(type);

                        if (type == Types.VARCHAR || type == Types.CHAR)
                        {
                            if (rs.getObject(columnIndex) != null)
                            {
                                isi = isi + "'" + rs.getObject(columnIndex).toString().trim() + "',";
                            } else
                            {
                                isi = isi + "'" + "" + "',";
                            }
                        } else
                        {
                            if (type == Types.DATE || type == Types.TIMESTAMP)
                            {
                                if (rs.getObject(columnIndex) != null)
                                {
                                    String ddt = dt.format(rs.getObject(columnIndex));

                                    isi = isi + "to_date('" + ddt + "','dd-mon-yyyy hh24:mi:ss'),";
                                } else
                                {
                                    //                                    isi = isi + "'" + "" + "',";
                                    isi = isi + "null,";
                                }
                            } else
                            {
                                if (rs.getObject(columnIndex) != null)
                                {
                                    isi = isi + rs.getObject(columnIndex).toString() + ",";
                                } else
                                {
                                    isi = isi + "null,";
                                }
                            }
                        }
                        //}

                    }

                    isi = isi.substring(0, isi.length() - 1) + "\"}";
                    try
                    {
                        writer.write(new StringBuilder().append(isi).append("\n").toString());
                    } finally
                    {
                        // comment this out if you want to inspect the files afterward
                        file.delete();
                    }
                }
                System.out.println("  @@@@@@@@@@@@ " + rowdata);
                json = "]}";
                writer.write(new StringBuilder().append(json).append("\n").toString());
                writer.close();

                long end = System.currentTimeMillis();
                System.out.print("Record to Array... ");
                //System.out.println((end - start) / 1000f + " seconds");
            }
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }

        Date date = new Date();
        String newDate = df.format(date);
        String zipFile = directory + newDate + ".zip";

        ZipFiles zip = new ZipFiles();
        zip.CreateZipFileFromMultipleFiles(zipFile, listFile);

        return zipFile;
        /*
         List<String> listFile = new ArrayList<String>();
         SimpleDateFormat df = new SimpleDateFormat("ddMMyyyyHHmmss");
         SimpleDateFormat dt = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
         SimpleDateFormat paramdate = new SimpleDateFormat("dd-MMM-yyyy");

         UtilImpl pro = new UtilImpl();
         Map map = new HashMap<>();
         map.put("BU", "");
         try
         {
         Connection conn = MyBatisConnectionFactory.getSqlSessionFactory().openSession().getConnection();
         List<Qry> list = pro.getListTableDownload(map);
         for (int i = 0; i < list.size(); i++)
         {
         Date date = new Date();
         String newDate = df.format(date);
         String parDate = paramdate.format(date);

         String fullName = directory + list.get(i).getTableName() + newDate + ".txt";
         File file = new File(fullName);
         FileWriter writer = new FileWriter(file);

         listFile.add(fullName);

         try
         {
         Statement stmt = conn.createStatement();
         String qrt = list.get(i).getSqlQry().replace("PARAMBU", buid);
         qrt = qrt.replace("PARAMDATE", "'" + paramdate + "'");
         System.out.println(qrt);
         ResultSet rs = stmt.executeQuery(qrt);
         ResultSetMetaData metaData = rs.getMetaData();
         int columnCount = metaData.getColumnCount();
         String json = "{\"TableName\":\"" + list.get(i).getTableName() + "\",";
         json = json + "\n\"FieldName\":\"";
         String judul = "";
         for (int column = 0; column <= columnCount; column++)
         judul = judul + metaData.getColumnName(column) + ",";
         json = json + judul.substring(0, judul.length() - 1) + "\",";
         //System.out.println(list.get(i).getTableName());
         long start = System.currentTimeMillis();
         json = json + "\n\"DATA\":[";
         writer.write(new StringBuilder().append(json).append("\n").toString());
         boolean awal = true;
         System.out.println("" + json);
         while (rs.next())// ambil data
         {
         String isi = "";
         if (awal)
         {
         isi = "{\"Record\":\"";
         awal = false;
         } else
         {
         isi = ",{\"Record\":\"";
         }

         for (int columnIndex = 0; columnIndex <= columnCount; columnIndex++)
         {
         int type = metaData.getColumnType(columnIndex);
         if (type == Types.VARCHAR || type == Types.CHAR)
         {// jika varchar
         if (rs.getObject(columnIndex) != null)
         {
         isi = isi + "'" + rs.getObject(columnIndex).toString() + "',";
         } else
         {
         isi = isi + "null,";
         }
         } else
         {
         if (type == Types.DATE || type == Types.TIMESTAMP)
         {
         if (rs.getObject(columnIndex) != null)
         {
         String ddt = dt.format(rs.getObject(columnIndex));
         isi = isi + "'" + ddt + "',";
         } else
         {
         isi = isi + "null,";
         }
         } else
         {
         if (rs.getObject(columnIndex) != null)
         {
         isi = isi + rs.getObject(columnIndex).toString() + ",";
         } else
         {
         isi = isi + ",";
         }
         }
         }
         }

         isi = isi.substring(0, isi.length() - 1) + "\"}";
         try
         {
         writer.write(new StringBuilder().append(isi).append("\n").toString());
         } finally
         {
         // comment this out if you want to inspect the files afterward
         //file.delete();
         }

         }

         json = "]}";
         writer.write(new StringBuilder().append(json).append("\n").toString());
         writer.close();

         long end = System.currentTimeMillis();
         System.out.print("Record to Array... ");
         System.out.println((end - start) / 1000f + " seconds");
         } catch (Exception e)
         {
         }

         }
         } catch (Exception ex)
         {
         }

         Date date = new Date();
         String newDate = df.format(date);
         String zipFile = directory + newDate + ".zip";

         ZipFiles zip = new ZipFiles();
         zip.CreateZipFileFromMultipleFiles(zipFile, listFile);

         return zipFile;
              
         */

    }

    public Connection getConnection()
    {
        return MyBatisConnectionFactory.getSqlSessionFactory().openSession().getConnection();
    }

    public int encryptFile(String nameFile) throws SQLException
    {
        int encryption = CipherCrypto.bikinSandi(new StringBuilder().append(nameFile).toString(), new StringBuilder().append(nameFile).append(".crp").toString());
        return encryption;
    }

    class masukan
    {

        String buid, tanggal;

        public String getBuid()
        {
            return buid;
        }

        public void setBuid(String buid)
        {
            this.buid = buid;
        }

        public String getTanggal()
        {
            return tanggal;
        }

        public void setTanggal(String tanggal)
        {
            this.tanggal = tanggal;
        }
    }
}
