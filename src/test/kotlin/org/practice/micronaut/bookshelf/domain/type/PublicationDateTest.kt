package org.practice.micronaut.bookshelf.domain.type

import arrow.core.None
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Test
import java.time.LocalDate

@MicronautTest
class PublicationDateTest {
    companion object {
        private val afterDate = LocalDate.of(2020, 11, 1)
        private val beforeDate = LocalDate.of(2020, 10, 1)
        private val currentDate = LocalDate.of(2020, 10, 31)
    }


    @Test
    fun invalidPublicationDateTest() {
        assert(PublicationDate(afterDate, currentDate) == None)
    }

    @Test
    fun validPublicationDateTest() {
        assert(PublicationDate(beforeDate, currentDate).nonEmpty())
    }
}