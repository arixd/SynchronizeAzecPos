/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.util.tools;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 *
 */
public class CipherCrypto
{

    public static int bikinSandi(String fromFile, String toFile)
    {
        try
        {
            String key = "squirrel123";

            FileInputStream fis = new FileInputStream(fromFile);
            FileOutputStream fos = new FileOutputStream(toFile);
            encrypt(key, fis, fos);

            return 0;
        } catch (Throwable e)
        {
            e.printStackTrace();
        }
        return 1;
    }

    public static void encrypt(String key, InputStream is, OutputStream os) throws Throwable
    {
        encryptOrDecrypt(key, 1, is, os);
    }

    public static void decrypt(String key, InputStream is, OutputStream os) throws Throwable
    {
        encryptOrDecrypt(key, 2, is, os);
    }

    public static void encryptOrDecrypt(String key, int mode, InputStream is, OutputStream os) throws Throwable
    {
        DESKeySpec dks = new DESKeySpec(key.getBytes());
        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
        SecretKey desKey = skf.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");

        if (mode == 1)
        {
            cipher.init(1, desKey);
            CipherInputStream cis = new CipherInputStream(is, cipher);
            doCopy(cis, os);
        } else if (mode == 2)
        {
            cipher.init(2, desKey);
            CipherOutputStream cos = new CipherOutputStream(os, cipher);
            doCopy(is, cos);
        }
    }

    public static void doCopy(InputStream is, OutputStream os) throws IOException
    {
        byte[] bytes = new byte[64];
        int numBytes;
        while ((numBytes = is.read(bytes)) != -1)
            os.write(bytes, 0, numBytes);
        os.flush();
        os.close();
        is.close();
    }
}
