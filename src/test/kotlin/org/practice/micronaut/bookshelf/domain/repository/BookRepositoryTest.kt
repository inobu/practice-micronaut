package org.practice.micronaut.bookshelf.domain.repository

import arrow.core.Either
import com.ninja_squad.dbsetup_kotlin.dbSetup
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.practice.micronaut.bookshelf.Ids.authorId
import org.practice.micronaut.bookshelf.deleteAll
import org.practice.micronaut.bookshelf.domain.model.EntityId
import org.practice.micronaut.bookshelf.domain.model.author.Author
import org.practice.micronaut.bookshelf.domain.model.book.PublishedBook
import org.practice.micronaut.bookshelf.insertAllTest
import org.practice.micronaut.bookshelf.util.GlobalError
import java.time.LocalDate
import javax.inject.Inject
import javax.sql.DataSource

@MicronautTest
class BookRepositoryTest {

    @Inject
    lateinit var bookRepository: BookRepository

    @Inject
    lateinit var dataSource: DataSource

    @BeforeEach
    fun init() {
        dbSetup(to = dataSource) {
            deleteAll()
            insertAllTest()
        }.launch()
    }

    /**
     * FIXME
     */
    @Test
    fun insertPublishedBookTest() {
        val publicationDate = LocalDate.of(2030, 11, 1)

        val publishedBook: Either<GlobalError, PublishedBook> = PublishedBook("エリック・エヴァンスのドメイン駆動設計", publicationDate, LocalDate.now())
        val entityAuthorId = EntityId.fromString<Author>(authorId)

        when (publishedBook) {
            is Either.Left -> {
                assert(false)
            }
            is Either.Right -> {
                bookRepository.savePublishedBook(publishedBook.b,entityAuthorId)
            }
        }
    }
}