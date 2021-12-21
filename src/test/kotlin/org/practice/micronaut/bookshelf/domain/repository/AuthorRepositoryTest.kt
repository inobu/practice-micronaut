package org.practice.micronaut.bookshelf.domain.repository

import com.ninja_squad.dbsetup_kotlin.dbSetup
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import java.util.*
import javax.inject.Inject
import javax.sql.DataSource
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.practice.micronaut.bookshelf.application.author.AuthorQueryService
import org.practice.micronaut.bookshelf.deleteAll
import org.practice.micronaut.bookshelf.domain.model.author.Author
import org.practice.micronaut.bookshelf.insertAuthors
import org.practice.micronaut.bookshelf.insertBooks

@MicronautTest
class AuthorRepositoryTest {

  @Inject lateinit var authorRepository: AuthorRepository

  @Inject lateinit var authorQueryService: AuthorQueryService

  @Inject lateinit var dataSource: DataSource

  @BeforeEach
  fun init() {
    dbSetup(to = dataSource) {
          deleteAll()
          insertAuthors()
          insertBooks()
        }
        .launch()
  }

  @Test
  fun insertTest() {
    val id = UUID.randomUUID().toString()
    val author = Author(id, "ジョシュア・ブロック")
    authorRepository.saveAuthor(author)

    val existAuthor = authorQueryService.findAuthor(UUID.fromString(id))
    val notExistAuthor = authorQueryService.findAuthor(UUID.randomUUID())
    assert(existAuthor.isDefined())
    assert(notExistAuthor.isEmpty())
  }
}
