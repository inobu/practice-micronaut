package org.practice.micronaut.bookshelf.domain.model

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import java.util.*
import org.junit.jupiter.api.Test
import org.practice.micronaut.bookshelf.domain.TestDataFactory.afterDate
import org.practice.micronaut.bookshelf.domain.TestDataFactory.beforeDate
import org.practice.micronaut.bookshelf.domain.TestDataFactory.currentDate
import org.practice.micronaut.bookshelf.domain.TestDataFactory.invalidLengthName
import org.practice.micronaut.bookshelf.domain.TestDataFactory.validBookName
import org.practice.micronaut.bookshelf.domain.model.book.PrePublishedBook

@MicronautTest
class PrePublishedBookTest {

  @Test
  fun validPrePublishedBookTest() {
    assert(PrePublishedBook(validBookName, afterDate, currentDate).isRight())
  }

  /** 101文字を渡してもPrePublishedBookの場合はRightに入る */
  @Test
  fun validPrePublishedBookInSpiteOfInvalidNameTest() {
    assert(PrePublishedBook(invalidLengthName, afterDate, currentDate).isRight())
  }

  @Test
  fun invalidPrePublishedBookTest() {
    assert(PrePublishedBook(validBookName, beforeDate, currentDate).isLeft())
  }
}
