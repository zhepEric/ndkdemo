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
import com.example.piaozhe.ndkdemo.utils.encrypt.keystore.DeCryptor
import com.example.piaozhe.ndkdemo.utils.encrypt.keystore.EnCryptor
import java.lang.StringBuilder
import java.security.cert.CertificateException

class JniNativeActivity : AppCompatActivity() {

    val jniNative by lazy { JniNative() }

    val SAMPLE_ALIAS = "KEYSTORE"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jni_native)

        jniNative.play("java 层")

//        getSigns()

    }

    //经过分段存储的密钥，AES加解密
    private fun aesSign() {
        val encrypt = AesUtils.encrypt("AES 加密1234")

        val decrypt = AesUtils.decrypt(encrypt)

        Log.i("JniNativeActivity","-----encrypt=$encrypt-----decrypt=$decrypt")
    }

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

    @RequiresApi(Build.VERSION_CODES.M)
    fun onKeyStoreE(view: View) {

        enCryptor = EnCryptor()

        try {

            deCryptor = DeCryptor()
        }catch (e:CertificateException){
            e.printStackTrace()
        }

        val encryptText = enCryptor.encryptText(SAMPLE_ALIAS, "12345ABCD")


        val decryptData = deCryptor.decryptData(SAMPLE_ALIAS, enCryptor.encryption, enCryptor.iv)


        Log.i("JniNativeActivity", "-----keystore结果=$decryptData")

    }
}
