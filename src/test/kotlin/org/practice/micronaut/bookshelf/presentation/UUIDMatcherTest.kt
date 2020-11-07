package org.practice.micronaut.bookshelf.presentation

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Test
import java.util.*

@MicronautTest
class UUIDMatcherTest {

    @Test
    fun validUUIDTest() {
        println(UUID.randomUUID().toString())
        assert(UUID.randomUUID().toString().uuidMatcher().isRight())
    }

    @Test
    fun invalidUUIDTestBecauseNull() {
        val nullString : String? = null
        assert(nullString.uuidMatcher().isLeft())
    }

    @Test
    fun invalidUUIDTestBecauseNotMatch() {
        val invalidString = "適当"
        assert(invalidString.uuidMatcher().isLeft())
    }
}