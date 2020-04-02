package com.example.piaozhe.ndkdemo.utils.encrypt.keystore;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.piaozhe.ndkdemo.utils.encrypt.AesUtils;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * @Author piaozhe
 * @Date 2020/4/1 13:24
 */
public class KeyStoreUtil {

    private static final String SAMPLE_ALIAS = "KEYSTORE";
    private static EnCryptor enCryptor;


    @RequiresApi(Build.VERSION_CODES.M)
    public static EnCryptor putKey() {


        enCryptor = new EnCryptor();

        try {
            enCryptor.encryptText(SAMPLE_ALIAS, AesUtils.generateKey());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return enCryptor;
    }

    public static String getKey(EnCryptor enCryptor) {
        if (enCryptor == null){
            return null;
        }

        try {
            DeCryptor deCryptor = new DeCryptor();

            String sec = null;
            try {
                sec = deCryptor.decryptData(SAMPLE_ALIAS, enCryptor.getEncryption(), enCryptor.getIv());

            } catch (UnrecoverableEntryException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            }
            return sec;
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
