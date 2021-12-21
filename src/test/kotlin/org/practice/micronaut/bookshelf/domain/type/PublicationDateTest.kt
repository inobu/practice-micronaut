package org.practice.micronaut.bookshelf.domain.type

import arrow.core.None
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Test
import org.practice.micronaut.bookshelf.domain.TestDataFactory.afterDate
import org.practice.micronaut.bookshelf.domain.TestDataFactory.beforeDate
import org.practice.micronaut.bookshelf.domain.TestDataFactory.currentDate

@MicronautTest
class PublicationDateTest {

  @Test
  fun invalidPublicationDateTest() {
    assert(PublicationDate(afterDate, currentDate) == None)
  }

  @Test
  fun validPublicationDateTest() {
    assert(PublicationDate(beforeDate, currentDate).nonEmpty())
  }
}
