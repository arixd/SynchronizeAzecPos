/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.util.controller;

import com.google.gson.Gson;
import id.my.berkah.util.config.Config;
import id.my.berkah.util.config.MyBatisConnectionFactory;
import id.my.berkah.util.model.TabelData;
import id.my.berkah.util.webservice.TestDownloadPos;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
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
public class TesDataDownloadController
{

    public static void main(String[] args)
    {
        String directory = Config.getConfig().getProperty("dirTemp");
        new TesDataDownloadController().insertDataToTabelHRN(directory);
    }

    public void insertDataToTabelHRN(String directory)
    {

        File files = new File(directory);//
        File[] listFile = files.listFiles();//membaca banya data file
        String nameFileToPath;
        int jumlahFile = 0;
        Statement st = null;

        Integer jumTotalError = 0;
        for (int i = 0; i < listFile.length; i++)
            try
            {
                nameFileToPath = listFile[i].getCanonicalPath();
                if (nameFileToPath.contains(".txt"))
                {
                    System.out.println("\n");
                    System.out.println(" Name Of File  : " + nameFileToPath);
                    System.out.println("\n");
                    String penampung = "";
                    BufferedReader br = null;
                    try
                    {
                        String sCurrentLine;
                        br = new BufferedReader(new FileReader(nameFileToPath));
                        while ((sCurrentLine = br.readLine()) != null)
                            penampung = penampung + sCurrentLine + "\n";
                    } catch (IOException e)
                    {
                        System.out.println("Gagal membaca file " + nameFileToPath);
                        e.printStackTrace();
                    } finally
                    {
                        try
                        {
                            if (br != null)
                            {
                                br.close();
                            }
                        } catch (IOException ex)
                        {
                            ex.printStackTrace();
                        }
                    }
                    Gson gson = new Gson();
                    TabelData tabelData = (TabelData) gson.fromJson(penampung.toString(), TabelData.class);

                    String record = "";
                    System.out.println("total data:" + tabelData.getDATA().size());
                    for (int j = 0; j < tabelData.getDATA().size(); j++)
                        try
                        {

                            String tabelName = tabelData.getTableName();// tabel Name
                            String fieldName = tabelData.getFieldName();// field Name

                            String keyFieldNames[] = tabelData.getKeyFieldsName();//key Field Name 
                            String keyFields[] = tabelData.getDATA().get(j).getRecord().getKeyField();// fields key name
                            String keyFieldName = "";
                            String keyField = "";

                            String sqlWhere = "";
                            record = tabelData.getDATA().get(j).getRecord().getDataValue().substring(0, tabelData.getDATA().get(j).getRecord().getDataValue().length() - 1);
                            String sqlCekInsert = "SELECT COUNT(" + keyFieldNames[0] + ") FROM " + tabelName + " where ";//     (1> or 0)
                            for (int k = 0; k < keyFieldNames.length; k++)
                            {    try
                                {
                                    keyFieldName = keyFieldNames[k];
                                    keyField = keyFields[k].toString();
                                    String keyFil = "";
                                    keyFil=!keyField.matches("[0-9]+") ? "'"+keyField+"'":""+keyField+"";
                                    sqlWhere += keyFieldName + "=" + keyFil + " AND ";
                                } catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                            }
                            sqlCekInsert += sqlWhere.substring(0, sqlWhere.length() - 4);
                            String sqlInsert = "INSERT INTO " + tabelName + "(" + fieldName + ") VALUES(" + record + ")";

                            System.out.println("sqlCekInsert : " + sqlCekInsert);
                            System.out.println("sqlInsert : " + sqlInsert);
                            System.out.println(""); System.out.println("");
                            
                            
//                            System.out.println("  * "+tabelData.getDATA().get(j).getRecord().getDataValue());
//                            String arrField[] = tabelData.getFieldName().split(",");// convert field  to array.
//                            String arrVal[]=tabelData.getDATA().get(j).getRecord().getDataValue();
//                            System.out.println("Size "+arrField.length);
//                            System.out.println("Size "+arrVal.length);
//                            for(int l=0;l<arrField.length; l++)
//                            {
//                                System.out.println(arrField[l]+" "+arrVal[l]);
//                            }

//                            System.out.println("sqlCekInsert : "+sqlCekInsert);

//                            
//
//
////
                        } catch (Exception e)
                        {
                            System.out.println(" Error tabel ... : " + tabelData.getTableName());
                            e.printStackTrace();
                        }
                    jumlahFile = jumlahFile + 1;
//                    
//                    
//                    
                }
            } catch (Exception e)
            {
                jumlahFile = -1;
                jumTotalError = jumTotalError + 1;
                e.printStackTrace();
            }
    }

}
