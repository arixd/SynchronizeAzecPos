/*    */ package ishare.testing.servlet;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.security.InvalidAlgorithmParameterException;
/*    */ import java.security.InvalidKeyException;
/*    */ import java.security.NoSuchAlgorithmException;
/*    */ import java.security.spec.AlgorithmParameterSpec;
/*    */ import javax.crypto.Cipher;
/*    */ import javax.crypto.CipherInputStream;
/*    */ import javax.crypto.CipherOutputStream;
/*    */ import javax.crypto.NoSuchPaddingException;
/*    */ import javax.crypto.SecretKey;
/*    */ import javax.crypto.spec.IvParameterSpec;
/*    */ 
/*    */ public class DesEncrypter
/*    */ {
/*    */   Cipher ecipher;
/*    */   Cipher dcipher;
/* 42 */   byte[] buf = new byte[1024];
/*    */ 
/*    */   DesEncrypter(SecretKey key)
/*    */   {
/* 22 */     byte[] iv = { -114, 18, 57, -100, 7, 114, 111, 90 };
/*    */ 
/* 26 */     AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
/*    */     try {
/* 28 */       this.ecipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
/* 29 */       this.dcipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
/*    */ 
/* 32 */       this.ecipher.init(1, key, paramSpec);
/* 33 */       this.dcipher.init(2, key, paramSpec);
/*    */     }
/*    */     catch (InvalidAlgorithmParameterException e) {
/*    */     }
/*    */     catch (NoSuchPaddingException e) {
/*    */     }
/*    */     catch (NoSuchAlgorithmException e) {
/*    */     }
/*    */     catch (InvalidKeyException e) {
/*    */     }
/*    */   }
/*    */ 
/*    */   public void encrypt(InputStream in, OutputStream out) {
/*    */     try {
/* 47 */       out = new CipherOutputStream(out, this.ecipher);
/*    */ 
/* 50 */       int numRead = 0;
/* 51 */       while ((numRead = in.read(this.buf)) >= 0) {
/* 52 */         out.write(this.buf, 0, numRead);
/*    */       }
/* 54 */       out.close();
/*    */     }
/*    */     catch (IOException e) {
/*    */     }
/*    */   }
/*    */ 
/*    */   public void decrypt(InputStream in, OutputStream out) {
/*    */     try {
/* 62 */       in = new CipherInputStream(in, this.dcipher);
/*    */ 
/* 65 */       int numRead = 0;
/* 66 */       while ((numRead = in.read(this.buf)) >= 0) {
/* 67 */         out.write(this.buf, 0, numRead);
/*    */       }
/* 69 */       out.close();
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ikhsan\Documents\ERAFONE2\WEB-INF\classes\
 * Qualified Name:     com.indonesia.maestro.DesEncrypter
 * JD-Core Version:    0.6.2
 */