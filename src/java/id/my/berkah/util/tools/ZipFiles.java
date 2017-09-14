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
import java.util.zip.ZipOutputStream;

/**
 *
 * @author Ari
 */
public class ZipFiles
{

    int status;
    String message;

    List<String> filesListInDir = new ArrayList<String>();

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    /**
     * This method zips the directory
     *
     * @param dir
     * @param zipDirName
     */
    public void zipDirectory(File dir, String zipDirName)
    {
        try
        {
            populateFilesList(dir);
            //zip file one by one
            //create ZipOutputStream to write to the zip file
            FileOutputStream fos = new FileOutputStream(zipDirName);
            ZipOutputStream zos = new ZipOutputStream(fos);
            for (String filePath : filesListInDir)
            {
                System.out.println("Zipping" + filePath);
                //for ZipEntry we need to keep only relative file path, so we used substring on absolute path
                ZipEntry ze = new ZipEntry(filePath.substring(dir.getAbsolutePath().length() + 1, filePath.length()));
                zos.putNextEntry(ze);
                //read the file and write to ZipOutputStream
                FileInputStream fis = new FileInputStream(filePath);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = fis.read(buffer)) > 0)
                    zos.write(buffer, 0, len);
                zos.closeEntry();
                fis.close();
            }

            zos.close();
            fos.close();
            status = 0;
            message = "Sukses";
        } catch (IOException e)
        {
            e.printStackTrace();
            status = 1;
            message = e.getMessage();
        }

    }

    private void populateFilesList(File dir)
    {
        File[] files = dir.listFiles();
        for (File file : files)
            if (file.isFile())
            {
                filesListInDir.add(file.getAbsolutePath());
            } else
            {
                populateFilesList(file);
            }
    }

    /**
     * This method compresses the single file to zip format
     *
     * @param file
     * @param zipFileName
     */
    public void zipSingleFile(File file, String zipFileName)
    {
        try
        {
            //create ZipOutputStream to write to the zip file
            FileOutputStream fos = new FileOutputStream(zipFileName);
            ZipOutputStream zos = new ZipOutputStream(fos);
            //add a new Zip Entry to the ZipOutputStream
            ZipEntry ze = new ZipEntry(file.getName());
            zos.putNextEntry(ze);
            //read the file and write to ZipOutputStream
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > 0)
                zos.write(buffer, 0, len);

            //Close the zip entry to write to zip file
            zos.closeEntry();
            //Close resources
            zos.close();
            fis.close();
            fos.close();
            //System.out.println(file.getCanonicalPath()+" is zipped to "+zipFileName);
            status = 0;
            message = file.getCanonicalPath() + " is zipped to " + zipFileName;
        } catch (IOException e)
        {
            e.printStackTrace();
            status = 1;
            message = e.getMessage();
        }

    }

    public void CreateZipFileFromMultipleFiles(String zipFile, List<String> srcFiles)
    {
        try
        {
            // create byte buffer
            byte[] buffer = new byte[1024];
            FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zos = new ZipOutputStream(fos);

            for (int i = 0; i < srcFiles.size(); i++)
            {
                File srcFile = new File(srcFiles.get(i));
                FileInputStream fis = new FileInputStream(srcFile);
                // begin writing a new ZIP entry, positions the stream to the start of the entry data
                zos.putNextEntry(new ZipEntry(srcFile.getName()));
                int length;
                while ((length = fis.read(buffer)) > 0)
                    zos.write(buffer, 0, length);
                zos.closeEntry();
                // close the InputStream
                fis.close();
            }
            // close the ZipOutputStream
            zos.close();
        } catch (IOException ioe)
        {
            System.out.println("Error creating zip file: " + ioe);
            ioe.printStackTrace();
        }
    }

}
