/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.util.webservice;

import id.my.berkah.util.config.Config;
import id.my.berkah.util.config.MyBatisConnectionFactory;
import id.my.berkah.util.controller.TesDataDownloadController;
import id.my.berkah.util.implement.UtilImpl;
import id.my.berkah.util.model.Qry;
import id.my.berkah.util.tools.ZipFiles;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ari
 */
public class TesCreareObject
{

    public static String directory = Config.getConfig().getProperty("dirTemp");
    public File file;
    public String json;
    public String judul;

    public FileWriter writer;

    public static void main(String[] args)
    {
        new TesCreareObject().createTabelFile();
    }

    public void createTabelFile()
    {
//
//        List<String> listFile = new ArrayList<String>();
//        String directory = Config.getConfig().getProperty("dirTemp");
//        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyyHHmmss");
//        SimpleDateFormat dt = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
//        SimpleDateFormat paramdate = new SimpleDateFormat("dd-MMM-yyyy");
//
//        UtilImpl pro = new UtilImpl();
//        Map map = new HashMap<>();
//        map.put("BU", "");
//
//        try
//        {
//            Connection conn = MyBatisConnectionFactory.getSqlSessionFactory().openSession().getConnection();
//
//            List<Qry> list = pro.getListTableDownload(map);
//            System.out.println("Jumlah Data " + list.size());
//            for (int i = 0; i < list.size(); i++)
//            {
//                Date date = new Date();
//                String newDate = df.format(date);
//                String parDate = paramdate.format(date);
//                String fullName = directory + list.get(i).getTableName() + newDate + ".txt";
//                File file = new File(fullName);
//                FileWriter writer = new FileWriter(file);
//
//                listFile.add(fullName);
//
//                System.out.println("" + i);
//                Statement stmt = conn.createStatement();
//                String qrt = list.get(i).getSqlQry().replace("PARAMBU", "282");
//                qrt = qrt.replace("PARAMDATE", "'" + parDate + "'");
//                qrt = qrt.replace("PARAMUSER", "1244");
//
//                System.out.println(qrt);
//                ResultSet rs = stmt.executeQuery(qrt);
//                ResultSetMetaData metaData = rs.getMetaData();
//                int columnCount = metaData.getColumnCount();
//
//                String json = "{\"TableName\":\"" + list.get(i).getTableName() + "\",";
//                json = json + "\n\"FieldName\":\"";
//                String judul = "";
//                int col = 0;
//                for (int column = 1; column <= columnCount; column++)
//                {
//                    judul = judul + metaData.getColumnName(column) + ",";
//                    col = col + 1;
//                }
//                json = json + judul.substring(0, judul.length() - 1) + "\",";
//
//                json = json + "\n\"KeyFieldsName\":[";
//
//                String[] keyFields = list.get(i).getKeyFields().split(",");
//                String keyFieldsJson = "";
//                for (int keyNo = 0; keyNo < keyFields.length; keyNo++)
//                    if (keyNo == keyFields.length - 1)
//                    {
//                        keyFieldsJson += "\"" + keyFields[keyNo] + "\"";
//                    } else
//                    {
//                        keyFieldsJson += "\"" + keyFields[keyNo] + "\",";
//                    }
//                json = json + keyFieldsJson;
//                json = json + "],";
//
//                System.out.println(list.get(i).getTableName());
//                long start = System.currentTimeMillis();
//                json= json+"\n\"DmlType\":\"" + list.get(i).getDmlType() + "\",";
//                        
//                        
//                json = json + "\n\"DATA\":[";
//                writer.write(new StringBuilder().append(json).append("\n").toString());
//
//                boolean awal = true;
//
//                int rowdata = 0, max = 10000, jumlahBaris = 0, anakKe = 1;
//                while (rs.next())
//                {
//                    jumlahBaris = jumlahBaris + 1;
//                    if (jumlahBaris == max)
//                    {
//                        json = "]}";
//                        writer.write(new StringBuilder().append(json).append("\n").toString());
//                        writer.close();
//
//                        fullName = "";
//                        fullName = directory + list.get(i).getTableName() + newDate + "__" + anakKe + ".txt";
//
//                        file = new File(fullName);
//                        writer = new FileWriter(file);
//                        listFile.add(fullName);
//
//                        json = null;
//                        json = "{\"TableName\":\"" + list.get(i).getTableName() + "\",";
//                        json = json + "\n\"FieldName\":\"";
//                        judul = "";
//                        int colx = 0;
//                        for (int column = 1; column <= columnCount; column++)
//                        {
//                            judul = judul + metaData.getColumnName(column) + ",";
//                            colx = colx + 1;
//                        }
//                        json = json + judul.substring(0, judul.length() - 1) + "\",";
//
//                        System.out.println(list.get(i).getTableName());
//                        
//                         json = json + "\n\"KeyFieldsName\":[";
//
//                        keyFields = list.get(i).getKeyFields().split(",");
//                        keyFieldsJson = "";
//                        for (int keyNo = 0; keyNo < keyFields.length; keyNo++)
//                            if (keyNo == keyFields.length - 1)
//                            {
//                                keyFieldsJson += "\"" + keyFields[keyNo] + "\"";
//                            } else
//                            {
//                                keyFieldsJson += "\"" + keyFields[keyNo] + "\",";
//                            }
//                        json = json + keyFieldsJson;
//                        json = json + "],";
//                        
//
//                        json = json + "\n\"DATA\":[";
//                        writer.write(new StringBuilder().append(json).append("\n").toString());
//
//                        awal = true;
//                        jumlahBaris = 0;
//                        anakKe = anakKe + 1;
//                    }
//                    String isi = "";
//
//                    if (awal)
//                    {
//                        isi = "{\"Record\":{";
//                        awal = false;
//                    } else
//                    {
//                        isi = ",{\"Record\":{";
//                    }
//                    isi+="\"DataValue\":\"";
//                    rowdata = 0;
//                    for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++)
//                    {
//                        rowdata++;
//         //System.out.println("cell( " + columnIndex + "," + b +") = "+rs.getObject(columnIndex).toString());
//
//                        //if (rs.getObject(columnIndex) != null)
//                        //{
//                        int type = metaData.getColumnType(columnIndex);
//                        //System.out.println("type:"+type+", column:"+metaData.getColumnLabel(columnIndex));
// 
//                        if (type == Types.VARCHAR || type == Types.CHAR)
//                        {
//                            if (rs.getObject(columnIndex) != null)
//                            {
//                                isi = isi + "'" + rs.getObject(columnIndex).toString().trim() + "',";
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
//                                    isi = isi + "to_date('" + ddt + "','dd-mon-yyyy hh24:mi:ss'),";
//                                } else
//                                {
//                                    //                                    isi = isi + "'" + "" + "',";
//                                    isi = isi + "null,";
//                                }
//                            } else
//                            {
//                                if (rs.getObject(columnIndex) != null)
//                                {
//                                    isi = isi + rs.getObject(columnIndex).toString() + ",";
//                                } else
//                                {
//                                    isi = isi + "null,";
//                                }
//                            }
//                        }
//                        //}
//
//                    }
//
////                    isi = isi.substring(0, isi.length() - 1) + "\"},";
//                    isi += "\",";
//                    
//                    isi += "\"KeyField\":[";
//                    for (int keyNo = 0; keyNo < keyFields.length; keyNo++)
//                    if (keyNo == keyFields.length - 1)
//                    {
//                        System.out.println("keyFields[keyNo]:"+keyFields[keyNo]);
//                        isi += "\"" + rs.getObject(keyFields[keyNo]).toString() + "\"";
//                    } else
//                    {
//                        isi += "\"" + rs.getObject(keyFields[keyNo]).toString() + "\",";
//                    }
//                    
//                    isi += "]}}"; 
//                    
//                    System.out.println(isi);
//                    try
//                    {
//                        writer.write(new StringBuilder().append(isi).append("\n").toString());
//                    } finally
//                    {
//                        // comment this out if you want to inspect the files afterward
//                        file.delete();
//                    }
//                }
//                // zip.CreateZipFileFromMultipleFiles(zipFile, listFile);
//                System.out.println("  @@@@@@@@@@@@ " + rowdata);
//                if (jumlahBaris != max)
//                {
//                    json = "]}";
//                    writer.write(new StringBuilder().append(json).append("\n").toString());
//                    writer.close();
//                }
//                long end = System.currentTimeMillis();
//                System.out.print("Record to Array... ");
//                System.out.println((end - start) / 1000f + " seconds");
//
//            }
//        } catch (Exception ex)
//        {
//            ex.printStackTrace();
//        }
//
//        Date date = new Date();
//        String newDate = df.format(date);
//        String zipFile = directory + newDate + ".zip";
//
//        ZipFiles zip = new ZipFiles();
//        zip.CreateZipFileFromMultipleFiles(zipFile, listFile);

//        
        
        System.out.println("Proses Insert");
        TesDataDownloadController tesDataDownloadController = new TesDataDownloadController();
        tesDataDownloadController.insertDataToTabelHRN(directory);
    }
}
