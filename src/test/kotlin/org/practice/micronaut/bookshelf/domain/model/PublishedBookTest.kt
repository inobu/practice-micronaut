package org.practice.micronaut.bookshelf.domain.model

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Test
import org.practice.micronaut.bookshelf.domain.TestDataFactory.beforeDate
import org.practice.micronaut.bookshelf.domain.TestDataFactory.currentDate
import org.practice.micronaut.bookshelf.domain.TestDataFactory.invalidLengthName
import org.practice.micronaut.bookshelf.domain.TestDataFactory.validBookName

@MicronautTest
class PublishedBookTest {

    @Test
    fun validPublishedBookTest() {
        assert(PublishedBook(validBookName, beforeDate, currentDate).isRight())
    }

    @Test
    fun invalidPublishedBookBecauseInvalidNameTest() {
        assert(PublishedBook(invalidLengthName, beforeDate, currentDate).isLeft())
    }
}