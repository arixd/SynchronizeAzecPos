/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.util.controller;

import com.google.gson.Gson;
import id.my.berkah.util.config.Config;
import id.my.berkah.util.config.MyBatisConnectionFactory;
import id.my.berkah.util.implement.UtilImpl;
import id.my.berkah.util.model.Qry;
import id.my.berkah.util.model.TabelData;
import id.my.berkah.util.webservice.TestDownloadPos;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ari
 */
public class TesDataDownloadController1
{
//
//    String directory = Config.getConfig().getProperty("dirTemp");
//
//    public void insertDataToTabelHRN()
//    {
//
//        Map<String, Object> tabelData = this.getDataFromDirec(directory);
//
//        List<String> datafile = (List<String>) tabelData.get("namaFile");
//        List<TabelData> ddt = (List<TabelData>) tabelData.get("TabelData");
//
//        Connection con=null;
//        con=getConnection();
//        System.out.println("@@@@@@@@@@@@@ hagsha sjagsya ");
//        for (int i = 0; i < ddt.size(); i++)
//        {    try
//            {
//                String namaTabel = ddt.get(i).getTableName();
//                String namaField = ddt.get(i).getFieldName();
//                String namaRecord = "";
//
//                for (int j = 0; j < ddt.get(i).getDATA().size(); j++)
//                {
//                    try
//                    {
//                        namaRecord = ddt.get(i).getDATA().get(j).getData();
//                        String sql = "INSERT INTO " + namaTabel + "(" + namaField + ")VALUES(" + namaRecord + ")";
//                        System.out.println(""+sql);
//                        Statement st=con.createStatement();
//                        int result=st.executeUpdate(sql);
//                        con.commit();
//                        //System.out.println("");System.out.println("");
//                        //System.out.println(datafile.get(i)+" : "+sql+" hasil  "+result);
//                        //System.out.println(datafile.get(i));
//                        System.out.println(""+j);
//                        //System.out.println("");
//                    } catch (Exception e)
//                    {
//                    }
//                    
//                }
//            } catch (Exception e)
//            {
//                System.out.println(" File Kosong  [[[[[[[[[[[[[[ " + datafile.get(i));
//            }
//        }
//    }
//
//    /* namaFile same as a.txt for example */
//    public String bacaFile(String namaFile)
//    {
//        BufferedReader br = null;
//        String stringHasil = "";
//        try
//        {
//            String sCurrentLine;
//            br = new BufferedReader(new FileReader(namaFile));
//            while ((sCurrentLine = br.readLine()) != null)
//                stringHasil = stringHasil + sCurrentLine + "\n";
//        } catch (IOException e)
//        {
//            System.out.println("Gagal membaca file " + namaFile);
//            e.printStackTrace();
//        } finally
//        {
//            try
//            {
//                if (br != null)
//                {
//                    br.close();
//                }
//            } catch (IOException ex)
//            {
//                ex.printStackTrace();
//            }
//        }
//        return stringHasil;
//    }
//
//    /*Me list semua file yang berektensi .txt dalam direktory
//     membaca setiap isi file .txt
//     tiap isi file dirubah ke dalam Java class menggunakan Json
//     isi file java disimpan kedalam sebuah array
//     */
//    public Map<String, Object> getDataFromDirec(String dir)
//    {
//        Map<String, Object> map = new HashMap<String, Object>();
//        File files = new File(dir);
//        File[] listFiles = files.listFiles();
//
//        String fileToPath;
//        List<TabelData> tabelDatas = new ArrayList<TabelData>();
//        List<String> fileName = new ArrayList<String>();
//
//        
//        int jumlahFile = 0;
//        for (int i = 0; i < listFiles.length; i++)
//            try
//            {
//                // mendapatkan nama file dari directory
//                fileToPath = listFiles[i].getCanonicalPath();
//                if (fileToPath.contains(".txt"))
//                {
//                    // baca isi file yang mengandung .txt
//                    String isiFile = bacaFile(fileToPath);
//                    Gson gson = new Gson();
//                    TabelData tabelData = (TabelData) gson.fromJson(isiFile, TabelData.class);
//                    /*
//                    tabelDatas.add(tabelData);
//                    fileName.add(fileToPath.replace(dir, ""));
//                    jumlahFile = jumlahFile + 1;
//                    */
//                    
//                    
//                }
//            } catch (IOException ex)
//            {
//                Logger.getLogger(TestDownloadPos.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        System.out.println("Jumlah File " + jumlahFile);
//        map.put("TabelData", tabelDatas);
//        map.put("namaFile", fileName);
//
//        return map;
//    }
//
//    public Connection getConnection()
//    {
//        return MyBatisConnectionFactory.getSqlSessionFactory().openSession().getConnection();
//    }
}
