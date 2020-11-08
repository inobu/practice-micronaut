package org.practice.micronaut.bookshelf.application

import com.ninja_squad.dbsetup_kotlin.dbSetup
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*
import javax.inject.Inject
import javax.sql.DataSource

@MicronautTest
class AuthorQueryServiceTest {
    @Inject
    lateinit var authorQueryService: AuthorQueryService

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
        assert(authorQueryService.findAuthor(Ids.authorId.toUUID()).isDefined())
    }

    @Test
    fun queryNotExistTest() {
        assert(authorQueryService.findAuthor(UUID.randomUUID()).isEmpty())
    }
}