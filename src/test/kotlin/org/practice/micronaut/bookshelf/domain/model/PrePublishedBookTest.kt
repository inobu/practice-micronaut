package org.practice.micronaut.bookshelf.domain.model

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Test
import org.practice.micronaut.bookshelf.domain.TestDataFactory.afterDate
import org.practice.micronaut.bookshelf.domain.TestDataFactory.beforeDate
import org.practice.micronaut.bookshelf.domain.TestDataFactory.currentDate
import org.practice.micronaut.bookshelf.domain.TestDataFactory.invalidLengthName
import org.practice.micronaut.bookshelf.domain.TestDataFactory.validBookName
import org.practice.micronaut.bookshelf.domain.model.book.PrePublishedBook
import java.util.*

@MicronautTest
class PrePublishedBookTest {

    @Test
    fun validPrePublishedBookTest() {
        assert(PrePublishedBook(validBookName, beforeDate, currentDate).isRight())
    }

    /**
     * 101文字を渡してもPrePublishedBookの場合はRightに入る
     */
    @Test
    fun validPrePublishedBookInSpiteOfInvalidNameTest() {
        assert(PrePublishedBook(invalidLengthName, beforeDate, currentDate).isRight())
    }

    @Test
    fun invalidPrePublishedBookTest() {
        val rand = UUID.randomUUID()
        val pre = "b0c4a631-469f-462e-a41f-d6a30ee6b03f"
        println(rand)
        println(UUID.fromString(pre))
        assert(PrePublishedBook(validBookName, afterDate, currentDate).isRight())
    }
}