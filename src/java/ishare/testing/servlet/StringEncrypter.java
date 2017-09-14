/*     */ package ishare.testing.servlet;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.spec.AlgorithmParameterSpec;
/*     */ import java.security.spec.InvalidKeySpecException;
/*     */ import java.security.spec.KeySpec;
/*     */ import javax.crypto.BadPaddingException;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.IllegalBlockSizeException;
/*     */ import javax.crypto.KeyGenerator;
/*     */ import javax.crypto.NoSuchPaddingException;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.crypto.SecretKeyFactory;
/*     */ import javax.crypto.spec.PBEKeySpec;
/*     */ import javax.crypto.spec.PBEParameterSpec;
/*     */ import sun.misc.BASE64Decoder;
/*     */ import sun.misc.BASE64Encoder;
/*     */ 
/*     */ public class StringEncrypter
/*     */ {
/*     */   Cipher ecipher;
/*     */   Cipher dcipher;
/*     */ 
/*     */   StringEncrypter()
/*     */   {
/*     */   }
/*     */ 
/*     */   StringEncrypter(SecretKey key, String algorithm)
/*     */   {
/*     */     try
/*     */     {
/* 101 */       this.ecipher = Cipher.getInstance(algorithm);
/* 102 */       this.dcipher = Cipher.getInstance(algorithm);
/* 103 */       this.ecipher.init(1, key);
/* 104 */       this.dcipher.init(2, key);
/*     */     }
/*     */     catch (NoSuchPaddingException e)
/*     */     {
/* 108 */       System.out.println("EXCEPTION: NoSuchPaddingException");
/*     */     }
/*     */     catch (NoSuchAlgorithmException e)
/*     */     {
/* 112 */       System.out.println("EXCEPTION: NoSuchAlgorithmException");
/*     */     }
/*     */     catch (InvalidKeyException e)
/*     */     {
/* 116 */       System.out.println("EXCEPTION: InvalidKeyException");
/*     */     }
/*     */   }
/*     */ 
/*     */   StringEncrypter(String passPhrase)
/*     */   {
/* 132 */     byte[] salt = { -87, -101, -56, 50, 86, 52, -29, 3 };
/*     */ 
/* 138 */     int iterationCount = 19;
/*     */     try
/*     */     {
/* 143 */       KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount);
/* 144 */       SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
/*     */ 
/* 146 */       this.ecipher = Cipher.getInstance(key.getAlgorithm());
/* 147 */       this.dcipher = Cipher.getInstance(key.getAlgorithm());
/*     */ 
/* 150 */       AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);
/*     */ 
/* 152 */       this.ecipher.init(1, key, paramSpec);
/* 153 */       this.dcipher.init(2, key, paramSpec);
/*     */     }
/*     */     catch (InvalidAlgorithmParameterException e)
/*     */     {
/* 158 */       System.out.println("EXCEPTION: InvalidAlgorithmParameterException");
/*     */     }
/*     */     catch (InvalidKeySpecException e)
/*     */     {
/* 162 */       System.out.println("EXCEPTION: InvalidKeySpecException");
/*     */     }
/*     */     catch (NoSuchPaddingException e)
/*     */     {
/* 166 */       System.out.println("EXCEPTION: NoSuchPaddingException");
/*     */     }
/*     */     catch (NoSuchAlgorithmException e)
/*     */     {
/* 170 */       System.out.println("EXCEPTION: NoSuchAlgorithmException");
/*     */     }
/*     */     catch (InvalidKeyException e)
/*     */     {
/* 174 */       System.out.println("EXCEPTION: InvalidKeyException");
/*     */     }
/*     */   }
/*     */ 
/*     */   public String encrypt(String str)
/*     */   {
/*     */     try
/*     */     {
/* 190 */       byte[] utf8 = str.getBytes("UTF8");
/*     */ 
/* 193 */       byte[] enc = this.ecipher.doFinal(utf8);
/*     */ 
/* 196 */       return new BASE64Encoder().encode(enc);
/*     */     }
/*     */     catch (BadPaddingException e)
/*     */     {
/*     */     }
/*     */     catch (IllegalBlockSizeException e)
/*     */     {
/*     */     }
/*     */     catch (UnsupportedEncodingException e)
/*     */     {
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*     */     }
/*     */ 
/* 211 */     return null;
/*     */   }
/*     */ 
/*     */   public String decrypt(String str)
/*     */   {
/*     */     try
/*     */     {
/* 227 */       byte[] dec = new BASE64Decoder().decodeBuffer(str);
/*     */ 
/* 230 */       byte[] utf8 = this.dcipher.doFinal(dec);
/*     */ 
/* 233 */       return new String(utf8, "UTF8");
/*     */     }
/*     */     catch (BadPaddingException e)
/*     */     {
/*     */     }
/*     */     catch (IllegalBlockSizeException e)
/*     */     {
/*     */     }
/*     */     catch (UnsupportedEncodingException e)
/*     */     {
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*     */     }
/*     */ 
/* 248 */     return null;
/*     */   }
/*     */ 
/*     */   public String testUsingSecretKey()
/*     */   {
/*     */     try
/*     */     {
/* 268 */       String secretString = "simelekete";
/*     */ 
/* 273 */       SecretKey desKey = KeyGenerator.getInstance("DES").generateKey();
/*     */ 
/* 278 */       StringEncrypter desEncrypter = new StringEncrypter(desKey, desKey.getAlgorithm());
/*     */ 
/* 283 */       return desEncrypter.encrypt(secretString);
/*     */     }
/*     */     catch (NoSuchAlgorithmException e)
/*     */     {
/*     */     }
/*     */ 
/* 314 */     return "0";
/*     */   }
/*     */ 
/*     */   public String testUsingPassPhrase(String secretString)
/*     */   {
/* 333 */     String passPhrase = "semelekete";
/*     */ 
/* 336 */     StringEncrypter desEncrypter = new StringEncrypter(passPhrase);
/*     */ 
/* 339 */     String desEncrypted = desEncrypter.encrypt(secretString);
/*     */ 
/* 350 */     return desEncrypted;
/*     */   }
/*     */ }

/* Location:           C:\Users\ikhsan\Documents\ERAFONE2\WEB-INF\classes\
 * Qualified Name:     com.indonesia.maestro.StringEncrypter
 * JD-Core Version:    0.6.2
 */