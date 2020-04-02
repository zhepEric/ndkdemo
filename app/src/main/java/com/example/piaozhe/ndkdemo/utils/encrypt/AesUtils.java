package com.example.piaozhe.ndkdemo.utils.encrypt;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.example.piaozhe.ndkdemo.App;
import com.example.piaozhe.ndkdemo.R;
import com.example.piaozhe.ndkdemo.constant.Constant;
import com.example.piaozhe.ndkdemo.jni.sign.NativeSign;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @Author piaozhe
 * @Date 2020/1/16 17:38
 * AES加密,安全系数比DES加密要好
 *
 * 密钥存储方案：
 * 采用将密钥拆分成多段，存储在不同位置，如string.xml，java代码，native层，
 * 也可以采用Android KeyStore存储密钥信息
 *
 *
 *
 *
 *
 */
public class AesUtils {
    private static final String HEX = "0123456789ABCDEF";
    private static final String SHA1PRNG = "SHA1PRNG";

    private static final String AES = "AES";
    private static final String AES_CBC_PKCS5PADDING = "AES/CBC/PKCS5Padding";//AES是加密方式 CBC是工作模式 PKCS5Padding是填充模式
//    private static final String AES_CBC_PKCS5PADDING = "AES";
    private static final String bm = "UTF-8";

    static String sec ="128636EEF9ACA";


    //01234
    private static String getSc1(){
        return NativeSign.getSecretStr();
    }

    //56789
    private static String getSc2(){
        return App.getInstance().getString(R.string.rsa_sc);
    }

    //ABCDEF
    private static String getSc3(){
        return Constant.AES_KEY;
    }

    public static String getSecrets(){
        return getSc1() + getSc2() + getSc3();
    }


    //随机生成key
    public static String generateKey() {
        try {
            SecureRandom localSecureRandom = SecureRandom.getInstance(SHA1PRNG);
            byte[] bytes_key = new byte[20];
            localSecureRandom.nextBytes(bytes_key);
            String strKey = toHex(bytes_key);
            return strKey;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    //适配P
    private static SecretKeySpec deriveKeyInsecurely(String password) {
        byte[] passwordBytes = password.getBytes(StandardCharsets.US_ASCII);
        //规定是16位密钥
        return new SecretKeySpec(InsecureSHA1PRNGKeyDerivator.deriveInsecureKey(passwordBytes, 16), "AES");
    }

    //做容错处理
    public static String encrypt(String key,String content){
        if (TextUtils.isEmpty(content)){
            return content;
        }

        try{
            byte[] encrypt = encrypt(key, content.getBytes());

            return Base64.encode(encrypt);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 加密
     *
     */
    private static byte[] encrypt(String key, byte[] clear) throws Exception {

        SecretKeySpec skeySpec = deriveKeyInsecurely(key);

        Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    public static String decrypt(String key,String encryptStr){
        if (TextUtils.isEmpty(encryptStr)){
            return encryptStr;
        }

        byte[] decode = Base64.decode(encryptStr);

        try{

            byte[] decryptB = decrypt(key, decode);

            return new String(decryptB);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 解密
     *
     */
    private static byte[] decrypt(String key, byte[] encrypted) throws Exception {
//        byte[] raw = getRawKey(key.getBytes());
//        SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);

        SecretKeySpec skeySpec = deriveKeyInsecurely(key);


        Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5PADDING);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }

    /**
     * 获取手机UUid,作为AES加密中 生成秘钥的关键字
     *
     * @return
     */
    public static String getDeviceId(Context context) {
        @SuppressLint("HardwareIds") String m_szAndroidID = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return m_szAndroidID;
    }

    /**
     * 加密
     * @param content 加密内容，
     * secret获取的是设别唯一标识
     * @return
     */
    public static String encrypt(String content) {

//        SecretKeySpec key = new SecretKeySpec(getDeviceId(App.getInstance()).getBytes(), AES);
//        SecretKeySpec key = new SecretKeySpec(getSecrets().getBytes(), AES);

        Cipher cipher = null;


        byte[] encryptedData;
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128, new SecureRandom(sec.getBytes()));//利用密钥生成128位随机数

            SecretKey secretKey = keyGenerator.generateKey();

            byte[] keyEncoded = secretKey.getEncoded();

            SecretKeySpec key = new SecretKeySpec(sec.getBytes(), AES);
//            SecretKeySpec key = new SecretKeySpec(keyEncoded, AES);

            cipher = Cipher.getInstance(AES_CBC_PKCS5PADDING);
            IvParameterSpec zeroIv = new IvParameterSpec(new byte[cipher.getBlockSize()]);
            cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
            encryptedData = cipher.doFinal(content.getBytes(bm));

            return Base64.encode(encryptedData);
        } catch (BadPaddingException | InvalidAlgorithmParameterException | InvalidKeyException | IllegalBlockSizeException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            Log.i("AesUtils","----AesUtils====errot="+e.getMessage());
            return "";
        }
    }



    /**
     * 解密
     * @param content 解密内容
     * @return
     */
    public static String decrypt(String content) {

        byte[] byteMi = Base64.decode(content);
//        SecretKeySpec key = new SecretKeySpec(getDeviceId(App.getInstance()).getBytes(), AES);
//        SecretKeySpec key = new SecretKeySpec(getSecrets().getBytes(), AES);
        Cipher cipher = null;
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128, new SecureRandom(sec.getBytes()));//利用密钥生成128位随机数

            SecretKey secretKey = keyGenerator.generateKey();

            byte[] keyEncoded = secretKey.getEncoded();

            SecretKeySpec key = new SecretKeySpec(keyEncoded, AES);


            cipher = Cipher.getInstance(AES_CBC_PKCS5PADDING);
            IvParameterSpec zeroIv = new IvParameterSpec(new byte[cipher.getBlockSize()]);

            cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);


            byte[] decryptedData = cipher.doFinal(byteMi);

            return new String(decryptedData, StandardCharsets.UTF_8);
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            e.printStackTrace();
            Log.i("AesUtils","----decrypt====error="+e.getMessage());

        }
        return "";
    }


    public static byte[] encrypt1(String content){
        Cipher cipher = null;


        byte[] encryptedData;
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//            keyGenerator.init(128, new SecureRandom(sec.getBytes()));//利用密钥生成128位随机数

            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(sec.getBytes());
            keyGenerator.init(128, secureRandom);

            SecretKey secretKey = keyGenerator.generateKey();

            byte[] keyEncoded = secretKey.getEncoded();

            SecretKeySpec key = new SecretKeySpec(keyEncoded, AES);

            cipher = Cipher.getInstance(AES_CBC_PKCS5PADDING);
            IvParameterSpec zeroIv = new IvParameterSpec(new byte[cipher.getBlockSize()]);//在CBC模式下必须加IvParameterSpec
            cipher.init(Cipher.ENCRYPT_MODE, key,zeroIv);
            encryptedData = cipher.doFinal(content.getBytes(bm));

            return encryptedData;
//            InvalidAlgorithmParameterException |
        } catch ( InvalidAlgorithmParameterException |BadPaddingException | InvalidKeyException | IllegalBlockSizeException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            Log.i("AesUtils","----encrypt1====errot="+e.getMessage());
            return null;
        }
    }

    public static String decrypt1(byte[] content) {
        Cipher cipher = null;
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//            keyGenerator.init(128, new SecureRandom(sec.getBytes()));//利用密钥生成128位随机数

            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(sec.getBytes());
            keyGenerator.init(128, secureRandom);

            SecretKey secretKey = keyGenerator.generateKey();

            byte[] keyEncoded = secretKey.getEncoded();

            SecretKeySpec key = new SecretKeySpec(keyEncoded, AES);//转化为AES专用密钥


            cipher = Cipher.getInstance(AES_CBC_PKCS5PADDING);
            IvParameterSpec zeroIv = new IvParameterSpec(new byte[cipher.getBlockSize()]);

            cipher.init(Cipher.DECRYPT_MODE, key,zeroIv);//初始化为解密模式的密码器


            byte[] decryptedData = cipher.doFinal(content);

            return new String(decryptedData, StandardCharsets.UTF_8);
//            InvalidAlgorithmParameterException |
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            e.printStackTrace();
            Log.i("AesUtils","----decrypt====error="+e.getMessage());

        }
        return "";
    }


    public static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }


    //KeyStore 保存密钥字符串

    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length()/2];
        for (int i = 0;i< hexStr.length()/2; i++) {
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

}
