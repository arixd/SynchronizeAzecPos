/*    */ package ishare.testing.servlet;
/*    */ 
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import javax.crypto.Cipher;
/*    */ import javax.crypto.CipherInputStream;
/*    */ import javax.crypto.CipherOutputStream;
/*    */ import javax.crypto.SecretKey;
/*    */ import javax.crypto.SecretKeyFactory;
/*    */ import javax.crypto.spec.DESKeySpec;
/*    */ 
/*    */ public class CipherCrypto
/*    */ {
/*    */   public static int bikinSandi(String fromFile, String toFile)
/*    */   {
/*    */     try
/*    */     {
/* 17 */       String key = "squirrel123";
/*    */ 
/* 19 */       FileInputStream fis = new FileInputStream(fromFile);
/* 20 */       FileOutputStream fos = new FileOutputStream(toFile);
/* 21 */       encrypt(key, fis, fos);
/*    */ 
/* 26 */       return 0;
/*    */     }
/*    */     catch (Throwable e)
/*    */     {
/* 30 */       e.printStackTrace();
/* 31 */     }return 1;
/*    */   }
/*    */ 
/*    */   public static void encrypt(String key, InputStream is, OutputStream os)
/*    */     throws Throwable
/*    */   {
/* 37 */     encryptOrDecrypt(key, 1, is, os);
/*    */   }
/*    */ 
/*    */   public static void decrypt(String key, InputStream is, OutputStream os) throws Throwable {
/* 41 */     encryptOrDecrypt(key, 2, is, os);
/*    */   }
/*    */ 
/*    */   public static void encryptOrDecrypt(String key, int mode, InputStream is, OutputStream os)
/*    */     throws Throwable
/*    */   {
/* 47 */     DESKeySpec dks = new DESKeySpec(key.getBytes());
/* 48 */     SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
/* 49 */     SecretKey desKey = skf.generateSecret(dks);
/* 50 */     Cipher cipher = Cipher.getInstance("DES");
/*    */ 
/* 52 */     if (mode == 1)
/*    */     {
/* 54 */       cipher.init(1, desKey);
/* 55 */       CipherInputStream cis = new CipherInputStream(is, cipher);
/* 56 */       doCopy(cis, os);
/*    */     }
/* 58 */     else if (mode == 2)
/*    */     {
/* 60 */       cipher.init(2, desKey);
/* 61 */       CipherOutputStream cos = new CipherOutputStream(os, cipher);
/* 62 */       doCopy(is, cos);
/*    */     }
/*    */   }
/*    */ 
/*    */   public static void doCopy(InputStream is, OutputStream os) throws IOException
/*    */   {
/* 68 */     byte[] bytes = new byte[64];
/*    */     int numBytes;
/* 71 */     while ((numBytes = is.read(bytes)) != -1)
/*    */     {
/* 73 */       os.write(bytes, 0, numBytes);
/*    */     }
/* 75 */     os.flush();
/* 76 */     os.close();
/* 77 */     is.close();
/*    */   }
/*    */ }

/* Location:           C:\Users\ikhsan\Documents\ERAFONE2\WEB-INF\classes\
 * Qualified Name:     com.indonesia.maestro.CipherCrypto
 * JD-Core Version:    0.6.2
 */