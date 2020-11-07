package org.practice.micronaut.bookshelf.application

import com.ninja_squad.dbsetup_kotlin.dbSetup
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*
import javax.inject.Inject
import javax.sql.DataSource

@MicronautTest
class BookQueryServiceTest {

    @Inject
    lateinit var bookQueryService: BookQueryService

    @Inject
    lateinit var dataSource: DataSource

    @BeforeEach
    fun init() {
        dbSetup(to = dataSource) {
            deleteAll()
            insertAuthors()
            insertBooks()
        }.launch()
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