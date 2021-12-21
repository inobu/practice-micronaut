package org.practice.micronaut.bookshelf.application

import com.ninja_squad.dbsetup_kotlin.dbSetup
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import java.util.*
import javax.inject.Inject
import javax.sql.DataSource
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.practice.micronaut.bookshelf.*
import org.practice.micronaut.bookshelf.application.book.BookQueryService
import org.practice.micronaut.bookshelf.toUUID

@MicronautTest
class BookQueryServiceTest {

  @Inject lateinit var bookQueryService: BookQueryService

  @Inject lateinit var dataSource: DataSource

  @BeforeEach
  fun init() {
    dbSetup(to = dataSource) {
          deleteAll()
          insertAllTest()
        }
        .launch()
  }

  @Test
  fun queryExistTest() {
    assert(bookQueryService.findBook(Ids.bookId.toUUID()).isDefined())
  }

  @Test
  fun queryNotExistTest() {
    assert(bookQueryService.findBook(UUID.randomUUID()).isEmpty())
  }
}
