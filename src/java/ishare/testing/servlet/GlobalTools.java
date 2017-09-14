/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ishare.testing.servlet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;


/**
 *
 * @author dadan
 */
public class GlobalTools {

    public static String pathVerification(String url){
        if(url.charAt(url.length()-1)!='/'){
            url=url+File.pathSeparator;
        }
        return url;
    }
    
    public static void printLog(String type, String content){
        System.out.println("<"+type+"> "+content);
    }
    
    public static String getCurrencyFormatWithoutDecimal(String str) {
        BigDecimal amount;
        if (str == null) {
            return null;
        }
        str = str.replace(",", "");

        if (str.equals("")) {
            amount = new BigDecimal("0");
        } else {
            amount = new BigDecimal(str);
        }

        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setCurrencySymbol("");
        dfs.setMonetaryDecimalSeparator('.');
        dfs.setGroupingSeparator(',');

        DecimalFormat df = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        df.setDecimalFormatSymbols(dfs);

        String tmp = df.format(amount);

        return tmp.substring(0, tmp.indexOf('.'));
    }

//    public static void log(String content, String titleFile) {
//        try {
//            File file = new File(Config.getConfig().getProperty("dirLog") + titleFile + ".log");
//            // if file doesnt exists, then create it
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
//            BufferedWriter bw = new BufferedWriter(fw);
////            bw.write(readFile() + content);
//            bw.write(content);
//            bw.close();
//        } catch (FileNotFoundException ex) {
//
//        } catch (IOException ex) {
//
//        }
//    }
    
     public static void writeFile(String content, String dirFile) {
        try {
            File file = new File(dirFile);
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
//            bw.write(readFile() + content);
            bw.write(content);
            bw.close();
        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {

        }
    }
    
}
