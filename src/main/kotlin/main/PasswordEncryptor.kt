package main

import java.security.SecureRandom
import java.util.*
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

class PasswordEncryptor {

    fun generateRandomSalt(): ByteArray {
        val random = SecureRandom()
        val salt = ByteArray(16)
        random.nextBytes(salt)

        return salt
    }

    private fun ByteArray.toHexString(): String = HexFormat.of().formatHex(this)

    fun generateHash(password: String, salt: String): String {
        val combinedSalt = "$salt$SECRET".toByteArray()

        val factory = SecretKeyFactory.getInstance(Companion.ALGORITHM)
        val spec = PBEKeySpec(password.toCharArray(), combinedSalt, ITERATIONS, KEY_LENGTH)
        val key = factory.generateSecret(spec)
        val hash = key.encoded

        return hash.toHexString()
    }

    companion object {
        private const val ALGORITHM = "PBKDF2WithHmacSHA512"
        private const val ITERATIONS = 120_000
        private const val KEY_LENGTH = 256
        private const val SECRET = "SomeRandomSecret"
    }
}