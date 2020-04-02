package com.example.piaozhe.ndkdemo.jni

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.support.annotation.RequiresApi
import android.util.Log
import android.view.View
import com.example.piaozhe.ndkdemo.R
import com.example.piaozhe.ndkdemo.jni.sign.NativeSign
import com.example.piaozhe.ndkdemo.utils.encrypt.AesUtils
import com.example.piaozhe.ndkdemo.utils.encrypt.Base64
import com.example.piaozhe.ndkdemo.utils.encrypt.keystore.DeCryptor
import com.example.piaozhe.ndkdemo.utils.encrypt.keystore.EnCryptor
import com.example.piaozhe.ndkdemo.utils.encrypt.keystore.KeyStoreUtil
import java.lang.StringBuilder
import java.security.cert.CertificateException

class JniNativeActivity : AppCompatActivity() {

    val jniNative by lazy { JniNative() }

    val SAMPLE_ALIAS = "KEYSTORE"
    val content = "绝密内容123456789"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jni_native)

        jniNative.play("java 层")

//        getSigns()

    }

    //经过分段存储的密钥，AES加解密
    private fun aesSign() {
//        val encrypt = AesUtils.encrypt("AES 加密1234")
//
//        val decrypt = AesUtils.decrypt(encrypt)
//
//        Log.i("JniNativeActivity","-----encrypt=$encrypt-----decrypt=$decrypt")

        //
//        val encrypt1 = AesUtils.encrypt1("ABCD  LSDKFJ")
//        //进制转换
//        val s: String = AesUtils.parseByte2HexStr(encrypt1) //转化为16进制
//        val parseHexStr2Byte = AesUtils.parseHexStr2Byte(s) //转化为2进制
//
//        val decrypt1 = AesUtils.decrypt1(parseHexStr2Byte)
//
//        Log.i("JniNativeActivity", "-----encrypt=$encrypt1-----decrypt=$decrypt1")
//
//        //生成的随机密钥
//        val generateKey = AesUtils.generateKey()
//        Log.i("JniNativeActivity", "-----随机生成的generateKey=$generateKey----")

        aesSec()
    }

    private fun aesSec() {
        val generateKey = AesUtils.generateKey()//生成随机数
        val generateKey1 = AesUtils.getSecrets()//生成随机数

        //将随机生成的密钥存在KeyStore中，相对安全



        val encrypt = AesUtils.encrypt(generateKey1, content)


        val decrypt = AesUtils.decrypt(generateKey1, encrypt)
//     generateKey1:9D23FABEC002ADB5CACDC0A434C95F1D663CD202
        Log.i("JniNativeActivity", "generateKey=$generateKey1-----encode=$encrypt----decrypt=$decrypt")


    }

    //获取签名信息
    private fun getSigns() {
        val packageManager = packageManager

        val packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
//        val apkContentsSigners =

//        val signature = signatures[0]
//        Log.i("JniNativeActivity", "-----str=${signature.toChars()}")

        var signArr: Array<Signature>
        var stringBuilder = StringBuilder()
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val signingInfo = packageInfo.signingInfo

                signArr = signingInfo.apkContentsSigners

            } else {
                signArr = packageInfo.signatures
            }

            for (signIndex in signArr) {
                stringBuilder.append(signIndex)
            }
            Log.i("JniNativeActivity", "-----str=${stringBuilder.toString()}")

        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }

    fun onNativeSecret(view: View) {
        val secretStr = NativeSign.getSecretStr()
        Log.i("JniNativeActivity", "-----密钥str=$secretStr")
    }

    fun onAESEncrypt(view: View) {
        aesSign()
    }

    lateinit var enCryptor: EnCryptor
    lateinit var deCryptor: DeCryptor


    //要求API大于23
    @RequiresApi(Build.VERSION_CODES.M)
    fun onKeyStoreE(view: View) {

        enCryptor = EnCryptor()

        try {

            deCryptor = DeCryptor()
        } catch (e: CertificateException) {
            e.printStackTrace()
        }

        val encryptText = enCryptor.encryptText(SAMPLE_ALIAS, AesUtils.generateKey())


//        必须是对应的EnCryptor对象
//        val enCryptor1 = EnCryptor()

        val decryptData = deCryptor.decryptData(SAMPLE_ALIAS, enCryptor.encryption, enCryptor.iv)


//        val putKey = KeyStoreUtil.putKey()
//        val key = KeyStoreUtil.getKey(putKey)

        Log.i("JniNativeActivity", "-----keystore结果=$decryptData")

    }
}
