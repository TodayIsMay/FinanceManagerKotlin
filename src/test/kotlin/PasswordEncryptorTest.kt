import main.PasswordEncryptor
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class PasswordEncryptorTest {
    private val encryptor = PasswordEncryptor()

    @Test
    fun testEncrypt() {
        val password = "1234567890"
        val salt = "[B@1ddf84b8"
        val encryptedPassword = encryptor.generateHash(password, salt)
        val encryptedPassword1 = encryptor.generateHash(password, salt)
        println(encryptedPassword)
        println(encryptedPassword1)
        Assertions.assertEquals(encryptedPassword, encryptedPassword1)
    }
}