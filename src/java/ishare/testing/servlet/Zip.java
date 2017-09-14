/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ishare.testing.servlet;

/**
 *
 * @author Shatria
 */
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Zip {

//    private static final String OUTPUT_ZIP_FILE = "C:\\MyFile2.zip";
//    private static final String SOURCE_FOLDER = "C:\\outputzip";
    private String sourceFile = "";
    private String outZipFile = "";

    private int totalFile = 0;

//    public static void main(String[] args) {
//        new Zip("C:\\install.exe", "D:\\install.zip").zipIt();
//    }

    public Zip(String sourceFile, String outZipFile) {
        totalFile = 0;
        this.sourceFile = sourceFile;
        this.outZipFile = outZipFile;
    }

    public int zipIt() {
        byte[] buffer = new byte[1024];
        try {
            FileOutputStream fos = new FileOutputStream(outZipFile);
            ZipOutputStream zos = new ZipOutputStream(fos);
            System.out.println("Output to Zip : " + outZipFile);
            File file = new File(sourceFile);
            ZipEntry ze = new ZipEntry(file.getName());
            zos.putNextEntry(ze);
            FileInputStream in = new FileInputStream(sourceFile);
            int len;
            while ((len = in.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }
            in.close();
            zos.closeEntry();
            //remember close it
            zos.close();
            System.out.println("Done Zip");
            return 0;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return 1;
    }

    public int UnZipFile(String a) {
        try {
            BufferedOutputStream out = null;
            ZipInputStream in = new ZipInputStream(new BufferedInputStream(new FileInputStream(a)));
            ZipEntry entry;
            while ((entry = in.getNextEntry()) != null) {
                String namafile = entry.getName().substring(20);

                int count;
                byte data[] = new byte[1000];
                out = new BufferedOutputStream(new FileOutputStream(namafile), 1000);
                while ((count = in.read(data, 0, 1000)) != -1) {
                    out.write(data, 0, count);
                }
                out.flush();
                out.close();
            }
            return 0;
        } catch (Exception e) {
            System.out.println("...\t  [ERROR] : " + e);
            e.printStackTrace();
            return 1;
        }
    }
}
