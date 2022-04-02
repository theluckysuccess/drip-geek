package drip.geek

import com.macasaet.fernet.Key
import com.macasaet.fernet.StringValidator
import com.macasaet.fernet.Token
import com.macasaet.fernet.Validator
import java.time.Duration
import java.time.Instant
import java.time.temporal.TemporalAmount

class SecretManager(secretKey: String) {
    private val key = Key(secretKey)
    fun decrypt(token: String): String = Token.fromString(token).validateAndDecrypt(key, validator)
    fun encrypt(value: String): Token = Token.generate(key, value)
    private val validator: Validator<String> = object : StringValidator {
        override fun getTimeToLive(): TemporalAmount = Duration.ofSeconds(Instant.MAX.epochSecond)
    }
}
