package com.example.piaozhe.ndkdemo.utils.encrypt;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;

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
import javax.crypto.NoSuchPaddingException;
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
 */
public class AesUtils {
    private static final String HEX = "0123456789ABCDEF";
    private static final String SHA1PRNG = "SHA1PRNG";

    private static final String AES = "AES";
    private static final String AES_CBC_PKCS5PADDING = "AES/CBC/PKCS5Padding";
    private static final String bm = "UTF-8";


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

    private static String getSecrets(){
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
        SecretKeySpec key = new SecretKeySpec(getSecrets().getBytes(), AES);
        Cipher cipher = null;


        byte[] encryptedData;
        try {
            cipher = Cipher.getInstance(AES_CBC_PKCS5PADDING);
            IvParameterSpec zeroIv = new IvParameterSpec(new byte[cipher.getBlockSize()]);
            cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
            encryptedData = cipher.doFinal(content.getBytes(bm));

            return Base64.encode(encryptedData);
        } catch (BadPaddingException | InvalidAlgorithmParameterException | InvalidKeyException | IllegalBlockSizeException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
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
        SecretKeySpec key = new SecretKeySpec(getSecrets().getBytes(), AES);
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(AES_CBC_PKCS5PADDING);
            IvParameterSpec zeroIv = new IvParameterSpec(new byte[cipher.getBlockSize()]);

            cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
            byte[] decryptedData = cipher.doFinal(byteMi);

            return new String(decryptedData, StandardCharsets.UTF_8);
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            e.printStackTrace();
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
//    private
}
