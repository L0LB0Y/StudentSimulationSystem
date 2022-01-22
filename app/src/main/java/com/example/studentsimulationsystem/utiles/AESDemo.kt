package com.example.studentsimulationsystem.utiles

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import javax.crypto.Cipher
import java.io.UnsupportedEncodingException
import java.security.NoSuchAlgorithmException
import javax.crypto.spec.SecretKeySpec
import java.security.MessageDigest
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("GetInstance")
object AESDemo {
    private var secretKey: SecretKeySpec? = null
    private lateinit var key: ByteArray

    // set Key
    private fun setKey(myKey: String) {
        val sha: MessageDigest?
        try {
            key = myKey.toByteArray(charset("UTF-8"))
            sha = MessageDigest.getInstance("SHA-1")
            key = sha.digest(key)
            key = key.copyOf(16)
            secretKey = SecretKeySpec(key, "AES")
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
    }

    // method to encrypt the secret text using key
    fun encrypt(strToEncrypt: String, secret: String): String? {
        try {
            setKey(secret)
            val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
            cipher.init(Cipher.ENCRYPT_MODE, secretKey)
            return Base64.getEncoder().encodeToString(cipher.doFinal
                (strToEncrypt.toByteArray(charset("UTF-8"))))
        } catch (e: Exception) {

            println("Error while encrypting: $e")
        }
        return null
    }

    // method to encrypt the secret text using key

    fun decrypt(strToDecrypt: String?, secret: String): String? {
        try {
            setKey(secret)
            val cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING")
            cipher.init(Cipher.DECRYPT_MODE, secretKey)
            return String(cipher.doFinal(Base64.getDecoder().
            decode(strToDecrypt)))
        } catch (e: Exception) {
            println("Error while decrypting: $e")
        }
        return null
    }
}