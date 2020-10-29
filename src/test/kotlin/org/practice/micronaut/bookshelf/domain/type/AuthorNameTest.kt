package org.practice.micronaut.bookshelf.domain.type

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


class AuthorNameTest {

    @Test
    fun authorNameTest() {
        assertThrows<IllegalArgumentException> {
            AuthorName("12345678901")
        }
    }

}