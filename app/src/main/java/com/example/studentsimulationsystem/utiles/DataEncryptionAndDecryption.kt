package com.example.studentsimulationsystem.utiles

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
object DataEncryptionAndDecryption {

    /** This Function For Encryption Text Using RSA AND AES Algorithms*/
    fun encryptText(plainText: String, aesKey: String, publicKey: String): String? {
        /** First Encrypt Text Using RSA Algorithm*/
        val rsaEncrypt = RSADemo.encryptMessage(plainText, publicKey)

        /** Second Encrypt Text Using AES Algorithm*/
        return AESDemo.encrypt(rsaEncrypt, aesKey)
    }

    /** This Function For Decryption Text Using RSA AND AES Algorithms*/
    fun decryptText(encryptedText: String?, aesKey: String, privateKey: String): String {
        /** First Decrypt Text Using AES Algorithm*/
        val aesDecrypt = AESDemo.decrypt(encryptedText, aesKey)
        /** Second Decrypt Text Using RSA Algorithm*/
        return RSADemo.decryptMessage(aesDecrypt, privateKey)

    }
}