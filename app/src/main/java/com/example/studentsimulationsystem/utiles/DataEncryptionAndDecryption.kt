package com.example.studentsimulationsystem.utiles

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
object DataEncryptionAndDecryption {
    /** Instance From RSA Demo Class For Generation Keys*/
    private val keyPairGenerator = RSADemo()

    /** Generation Private Key For Decryption RSA Text*/

    private val privateKey: String =
        Base64.getEncoder().encodeToString(keyPairGenerator.privateKey.encoded)

    /** Generation Public Key For Encryption RSA Text*/
    private val publicKey: String =
        Base64.getEncoder().encodeToString(keyPairGenerator.publicKey.encoded)

    /** This Function For Encryption Text Using RSA AND AES Algorithms*/
    fun encryptText(plainText: String, key: String): String? {
        /** First Encrypt Text Using RSA Algorithm*/
        val rsaEncrypt = RSADemo.encryptMessage(plainText, publicKey)

        /** Second Encrypt Text Using AES Algorithm*/
        return AESDemo.encrypt(rsaEncrypt, key)
    }

    /** This Function For Decryption Text Using RSA AND AES Algorithms*/
    fun decryptText(encryptedText: String?, key: String): String {
        /** First Decrypt Text Using AES Algorithm*/
        val aesDecrypt = AESDemo.decrypt(encryptedText, key)
        /** Second Decrypt Text Using RSA Algorithm*/
        return RSADemo.decryptMessage(aesDecrypt, privateKey)

    }
}