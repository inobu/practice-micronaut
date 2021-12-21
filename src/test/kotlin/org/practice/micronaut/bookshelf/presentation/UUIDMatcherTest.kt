package org.practice.micronaut.bookshelf.presentation

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import java.util.*
import org.junit.jupiter.api.Test

@MicronautTest
class UUIDMatcherTest {

  @Test
  fun validUUIDTest() {
    assert(UUID.randomUUID().toString().uuidValidator().isRight())
  }

  @Test
  fun invalidUUIDTestBecauseNull() {
    val nullString: String? = null
    assert(nullString.uuidValidator().isLeft())
  }

  @Test
  fun invalidUUIDTestBecauseNotMatch() {
    val invalidString = "適当"
    assert(invalidString.uuidValidator().isLeft())
  }
}
