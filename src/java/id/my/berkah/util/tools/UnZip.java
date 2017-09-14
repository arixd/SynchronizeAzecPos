/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.util.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 *
 * @author dadan
 */
public class UnZip
{

    public List<String> unZipIt(String zipFile, String outputFolder)
    {
        List<String> fileList = new ArrayList<>();
        byte[] buffer = new byte[1024];
        try
        {
            //create output directory is not exists
            File folder = new File(outputFolder);
            if (!folder.exists())
            {
                folder.mkdir();
            }
            //get the zip file content
            ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
            //get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();

            while (ze != null)
            {
                String fileName = ze.getName();
                File newFile = new File(folder, zipFile);
                System.out.println("file unzip : " + newFile.getAbsoluteFile());
                fileList.add(newFile.getAbsolutePath().toString());
                //create all non exists folders
                //else you will hit FileNotFoundException for compressed folder
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0)
                    fos.write(buffer, 0, len);

                fos.close();
                ze = zis.getNextEntry();
            }

            zis.closeEntry();
            zis.close();

            System.out.println("Done");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return fileList;
    }
}
