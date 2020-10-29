package org.practice.micronaut.bookshelf.domain.type

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BookNameTest {
    @Test
    fun bookNameTest() {
        assertThrows<IllegalArgumentException> {
            BookName("12345678901")
        }
    }
}