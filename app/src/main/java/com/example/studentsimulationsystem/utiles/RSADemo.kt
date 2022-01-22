package com.example.studentsimulationsystem.utiles

import android.os.Build
import androidx.annotation.RequiresApi
import java.security.spec.PKCS8EncodedKeySpec
import javax.crypto.Cipher
import java.security.spec.X509EncodedKeySpec
import java.io.IOException
import java.security.*
import java.util.*

/*
 * RSA Key Size: 4096
 * Cipher Type: RSA/ECB/OAEPWithSHA-256AndMGF1Padding
 */

@RequiresApi(Build.VERSION_CODES.O)
class RSADemo {
    var privateKey: PrivateKey
    var publicKey: PublicKey

    companion object {
        // convert String publicKey to Key object
        @Throws(GeneralSecurityException::class, IOException::class)
        fun loadPublicKey(stored: String): Key {
            val data: ByteArray = Base64.getDecoder().decode(stored.toByteArray())
            val spec = X509EncodedKeySpec(data)
            val fact = KeyFactory.getInstance("RSA")
            return fact.generatePublic(spec)
        }

        // Encrypt using publicKey
        @Throws(Exception::class)
        fun encryptMessage(plainText: String, publicKey: String): String {
            val cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding")
            cipher.init(Cipher.ENCRYPT_MODE, loadPublicKey(publicKey))
            return Base64.getEncoder().encodeToString(
                cipher.doFinal

                    (plainText.toByteArray())
            )
        }

        // Decrypt using private key
        @Throws(Exception::class)
        fun decryptMessage(encryptedText: String?, privateKey: String):
                String {
            val cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding")
            cipher.init(Cipher.DECRYPT_MODE, loadPrivateKey(privateKey))
            return String(
                cipher.doFinal(Base64.getDecoder().decode(encryptedText))
            )
        }

        // Convert String private key to privateKey object
        @Throws(GeneralSecurityException::class)
        fun loadPrivateKey(key64: String): PrivateKey {
            val clear: ByteArray = Base64.getDecoder().decode(key64.toByteArray())
            val keySpec = PKCS8EncodedKeySpec(clear)
            val fact = KeyFactory.getInstance("RSA")
            val privet = fact.generatePrivate(keySpec)
            Arrays.fill(clear, 0.toByte())
            return privet
        }

    }

    init {
        val keyGen = KeyPairGenerator.getInstance("RSA")
        keyGen.initialize(4096)
        val pair = keyGen.generateKeyPair()
        privateKey = pair.private
        publicKey = pair.public
    }
} 