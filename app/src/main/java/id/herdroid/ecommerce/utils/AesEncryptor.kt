package id.herdroid.ecommerce.utils

import android.util.Base64
import android.util.Log
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object AesEncryptor {

    private const val ALGORITHM = "AES/CBC/PKCS5Padding"
    private const val SECRET_KEY = "1234567890123456"
    private const val IV = "abcdefghijklmnop"

    fun encrypt(data: String): String {
        return try {
            val cipher = Cipher.getInstance(ALGORITHM)
            val keySpec = SecretKeySpec(SECRET_KEY.toByteArray(Charsets.UTF_8), "AES")
            val ivSpec = IvParameterSpec(IV.toByteArray(Charsets.UTF_8))
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
            val encrypted = cipher.doFinal(data.toByteArray(Charsets.UTF_8))
            Base64.encodeToString(encrypted, Base64.DEFAULT)
        } catch (e: Exception) {
            Log.e("AesEncryptor", "Encryption error", e)
            ""
        }
    }

    fun decrypt(data: String?): String {
        if (data.isNullOrBlank()) return "INVALID"
        return try {
            val cipher = Cipher.getInstance(ALGORITHM)
            val keySpec = SecretKeySpec(SECRET_KEY.toByteArray(Charsets.UTF_8), "AES")
            val ivSpec = IvParameterSpec(IV.toByteArray(Charsets.UTF_8))
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
            val decoded = Base64.decode(data, Base64.DEFAULT)
            val decrypted = cipher.doFinal(decoded)
            String(decrypted, Charsets.UTF_8)
        } catch (e: Exception) {
            Log.e("AesEncryptor", "Decryption error", e)
            "DECRYPTION_FAILED"
        }
    }

    fun generateShortOrderId(length: Int = 12): String {
        val chars = "abcdefghijklmopqrstuvwxyz0123456789"
        val random = SecureRandom()
        return (1..length)
            .map { chars[random.nextInt(chars.length)] }
            .joinToString("")
    }
}
