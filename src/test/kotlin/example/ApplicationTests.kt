package example

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.json.JsonMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.security.jackson2.SecurityJackson2Modules
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.OAuth2Error

class ApplicationTests {
  private lateinit var objectMapper: ObjectMapper

  @BeforeEach
  internal fun setUp() {
    objectMapper = JsonMapper.builder()
      .addModules(SecurityJackson2Modules.getModules(ClassLoader.getSystemClassLoader()))
      .build()
  }

  @Test
  fun `Demonstrates issue#11893`() {
    val e = OAuth2AuthenticationException(OAuth2Error("invalid_nonce"))
    val bytes = objectMapper.writeValueAsBytes(e)

    Assertions.assertFalse(bytes.isEmpty())
  }
}
