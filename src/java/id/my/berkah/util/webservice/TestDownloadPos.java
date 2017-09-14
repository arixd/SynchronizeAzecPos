/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.util.webservice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import id.my.berkah.util.config.Config;
import id.my.berkah.util.config.MyBatisConnectionFactory;
import id.my.berkah.util.controller.TesDataDownloadController;
import id.my.berkah.util.implement.UtilImpl;
import id.my.berkah.util.model.Qry;
import id.my.berkah.util.model.TabelData;
import id.my.berkah.util.tools.UnZip;
import id.my.berkah.util.tools.ZipFiles;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dadan
 */
public class TestDownloadPos
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {

        /*
         DataObject obj=new DataObject();
        
         Gson gson=new Gson();
        
         String json=gson.toJson(obj);
         try
         {
         FileWriter writer=new FileWriter("D:\\Example\\a.json");
         writer.write(json);
         writer.close();
         } catch (Exception e)
         {
         }
         System.out.println("Data Json "+json);

         Gson gg=new Gson();
         try
         {
         BufferedReader br=new BufferedReader(new FileReader("D:\\Example\\a.json"));
            
         DataObject d=gg.fromJson(br, DataObject.class);
         System.out.println("data java "+d);
         } catch (Exception e)
         {
         }
         */
//     
        List<String> listFile = new ArrayList<String>();
        String directory = Config.getConfig().getProperty("strTempDir");
        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyyHHmmss");
        SimpleDateFormat dt = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        SimpleDateFormat paramdate = new SimpleDateFormat("dd-MMM-yyyy");

        UtilImpl pro = new UtilImpl();
        Map map = new HashMap<>();
        map.put("BU", "149");
//
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
                String qrt = list.get(i).getSqlQry().replace("PARAMBU", "282");
                qrt = qrt.replace("PARAMDATE", "'" + parDate + "'");
                qrt = qrt.replace("PARAMUSER", "1244");

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

                System.out.println(list.get(i).getTableName());
                long start = System.currentTimeMillis();

                json = json + "\n\"DATA\":[";
                writer.write(new StringBuilder().append(json).append("\n").toString());

                boolean awal = true;

                int rowdata = 0;
                
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
//                        file.delete();
                    }
                }
                // zip.CreateZipFileFromMultipleFiles(zipFile, listFile);
                System.out.println("  @@@@@@@@@@@@ " + rowdata);
                json = "]}";
                writer.write(new StringBuilder().append(json).append("\n").toString());
                writer.close();

                long end = System.currentTimeMillis();
                System.out.print("Record to Array... ");
                System.out.println((end - start) / 1000f + " seconds");
            }
        } catch (Exception ex)
        {
                        ex.printStackTrace();
        }

//        Date date = new Date();
//        String newDate = df.format(date);
//        String zipFile = directory + newDate + ".zip";
//
//        ZipFiles zip = new ZipFiles();
//        zip.CreateZipFileFromMultipleFiles(zipFile, listFile);
//
//       
//        List<String> inputfile = listFile;
//        for (int a = 0; a < inputfile.size(); a++)
//            try
//            {
//                String nameOfFile = "";
//                nameOfFile = inputfile.get(a);
//                double nol = 2000.0; //  No. of lines to be split and saved in each output file.
//                File filex = new File(nameOfFile);
//                Scanner scanner = new Scanner(filex);
//                int count = 0;
//                while (scanner.hasNextLine())
//                {
//                    scanner.nextLine();
//                    count++;
//                }
//                System.out.println("Lines in the file: " + count);     // Displays no. of lines in the input file.
//
//                double temp = (count / nol);
//                int temp1 = (int) temp;
//                int nof = 0;
//                if (temp1 == temp)
//                {
//                    nof = temp1;
//                } else
//                {
//                    nof = temp1 + 1;
//                }
//                System.out.println("No. of files to be generated :" + nof); // Displays no. of files to be generated.  
//
//                FileInputStream fstream = new FileInputStream(filex);
//                DataInputStream in = new DataInputStream(fstream);
//
//                BufferedReader br = new BufferedReader(new InputStreamReader(in));
//                String strLine;
//                for (int j = 1; j <= nof; j++)
//                {
//                    FileWriter fstream1 = new FileWriter("D:\\TerimaExample\\" + j + ".txt");     // Destination File Location  
//                    BufferedWriter out = new BufferedWriter(fstream1);
//                    for (int i = 1; i <= nol; i++)
//                    {
//                        strLine = br.readLine();
//                        if (strLine != null)
//                        {
//                            out.write(strLine);
//                            if (i != nol)
//                            {
//                                out.newLine();
//                            }
//                        }
//                    }
//                    out.close();
//                }
//
//                in.close();
//
//            } catch (Exception ex)
//            {
//                Logger.getLogger(TestDownloadPos.class.getName()).log(Level.SEVERE, null, ex);
//            }


        System.out.println("Siap COBA Insert ");
        System.out.println("Siap COBA Insert ");
        System.out.println("Siap COBA Insert ");
        System.out.println("Siap COBA Insert ");

//        TesDataDownloadController tesDataDownloadController = new TesDataDownloadController();
//        tesDataDownloadController.insertDataToTabelHRN();
        
        
        
//        // TODO code application logic here
//        List<String> listFile = new ArrayList<String>();
//        String directory = Config.getConfig().getProperty("dirTemp");
//        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyyHHmmss");
//        SimpleDateFormat dt = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
//        SimpleDateFormat paramdate = new SimpleDateFormat("dd-MM-yyyy");
//        UtilImpl pro = new UtilImpl();
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("BU", "");
//
//        try
//        {
//            Connection conn = MyBatisConnectionFactory.getSqlSessionFactory().openSession().getConnection();
//            List<Qry> list = pro.getListTableDownload(map);
//            for (int i = 0; i < list.size(); i++)
//            {
//                Date date = new Date();
//                String newDate = df.format(date);
//                String parDate = paramdate.format(date);
//
////                System.out.println("Table Name " + list.get(i).getTableName());
//                String fullName = directory + list.get(i).getTableName() + newDate + ".txt";
//                File file = new File(fullName);
//                FileWriter writer = new FileWriter(file);
//
//                listFile.add(fullName);
//                Statement stmt = conn.createStatement();
//                String qrt = list.get(i).getSqlQry().replace("PARAMBU", "12");
////                System.out.print("** | " + list.get(i).getSqlQry() + "| -" + qrt);
//                qrt = qrt.replace("PARAMDATE", "'" + parDate + "'");
//                ResultSet rs = stmt.executeQuery(qrt);
//                ResultSetMetaData metaData = rs.getMetaData();
//                int columnCount = metaData.getColumnCount();
//
//                String json = "{\"TableName\":\"" + list.get(i).getTableName() + "\",";
//                json = json + "\n\"FieldName\":\"";;
////                {"TableName"
////                    :"TCSLS301T",
////                    "FieldName":" }
//                String judul = "";
//                for (int column = 1; column <= columnCount; column++)
//                    judul = judul + metaData.getColumnName(column) + ",";
//                json = json + judul.substring(0, judul.length() - 1) + "\",";
////                {"TableName":"TCSLS301T",
////"FieldName":"PS_DETAIL_ID,PS_ID,ITEM_ID,ITEM_CODE,PRICE,PCT_DISC,AMT_DISC,MIN_PRICE,MAX_PRICE,IS_BONUS,PARENT_ITEM_ID,PARENT_PS_DETAIL_ID,EFFECTIVE_DATE_NO,EFFECTIVE_DATE,EXPIRED_DATE_NO,EXPIRED_DATE,EXPIRED_BY,FS_ID,FIELDNAME1,CREATED_BY,CREATED_DATE,MODIFY_BY,MODIFY_DATE,PRIORITY_ID,TYPE,UPLOAD_ID,DEFAULT_BONUS",
////                }
//                long start = System.currentTimeMillis();
//
//                json = json + "\n\"DATA\":[";
////                "TableName":"TCSLS301T",
////"FieldName":"PS_DETAIL_ID,PS_ID,ITEM_ID,ITEM_CODE,PRICE,PCT_DISC,AMT_DISC,MIN_PRICE,MAX_PRICE,IS_BONUS,PARENT_ITEM_ID,PARENT_PS_DETAIL_ID,EFFECTIVE_DATE_NO,EFFECTIVE_DATE,EXPIRED_DATE_NO,EXPIRED_DATE,EXPIRED_BY,FS_ID,FIELDNAME1,CREATED_BY,CREATED_DATE,MODIFY_BY,MODIFY_DATE,PRIORITY_ID,TYPE,UPLOAD_ID,DEFAULT_BONUS",
////"DATA":[
//
////                untuk mengambil beberapa data dari database. Sangat dianjurkan untuk menggunakan StringBuilder apabila memungkinkan. Hal itu karena StringBuilder memiliki proses yang lebih cepat dibandingkan dengan StringBuffer. Namun apabila proses yang ada membutuhkan thread safety, maka sebaiknya menggunakan StringBuffer.
//                writer.write(new StringBuilder().append(json).append("\n").toString());
//                boolean awal = true;
//                System.out.println("" + json);
//                while (rs.next())// ambil data
//                {
//                    String isi = "";
//                    if (awal)
//                    {
//                        isi = "{\"Record \":\"";
//                        awal = false;
//                    } else
//                    {
//                        isi = ",{\"Record \":\"";
//                    }
//
//                    for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++)
//                    {
////                        TableName":"TCSLS001T",
////"FieldName":"SETUP_ID,GROUP_BU_ID,GROUP_BU_CODE,BU_ID,BU_CODE,CREATE_BY,CREATE_DATE,MODIFY_BY,MODIFY_DATE,EXPIRE_BY,EXPIRE_DATE,FS_ID",
////"DATA":[
////'2''2''12''2''12''2''93''2''93''2''93''2
//                        int type = metaData.getColumnType(columnIndex);
//                        if (type == Types.VARCHAR || type == Types.CHAR)
//                        {
//                            if (rs.getObject(columnIndex) != null)
//                            {
//                                isi = isi + "'" + rs.getObject(columnIndex).toString() + "',";
//                            } else
//                            {
//                                isi = isi + "'" + "" + "',";
//                            }
//                        } else
//                        {
//                            if (type == Types.DATE || type == Types.TIMESTAMP)
//                            {
//                                if (rs.getObject(columnIndex) != null)
//                                {
//                                    String ddt = dt.format(rs.getObject(columnIndex));
//
//                                    isi = isi + "'" + ddt + "',";
//                                } else
//                                {
//                                    isi = isi + "'" + "" + "',";
//                                }
//                            } else
//                            {
//                                if (rs.getObject(columnIndex) != null)
//                                {
//                                    isi = isi + rs.getObject(columnIndex).toString() + ",";
//                                } else
//                                {
//                                    isi = isi + ",";
//                                }
//                            }
//                        }
//                    }// end for
//
//                    isi = isi.substring(0, isi.length() - 1) + "\"}";
//                    try
//                    {
//
//                        writer.write(new StringBuilder().append(isi).append("\n").toString());
//                    } finally
//                    {
//                        // comment this out if you want to inspect the files afterward
//                        //file.delete();
//                    }
//                }
//
//                json = "]}";
//                writer.write(new StringBuilder().append(json).append("\n").toString());
//                writer.close();
//
//                long end = System.currentTimeMillis();
//                System.out.print("Record to Array... ");
//                //System.out.println((end - start) / 1000f + " seconds");
//            }
//
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        Date date = new Date();
//        String newDate = df.format(date);
//        String zipFile = directory + newDate + ".zip";
//
//        ZipFiles zip = new ZipFiles();
//        zip.CreateZipFileFromMultipleFiles(zipFile, listFile);
//        String directory = Config.getConfig().getProperty("dirTemp");
//        
//        UnZip un=new UnZip();
//        un.unZipIt(directory, "D:\\");
    }

}
