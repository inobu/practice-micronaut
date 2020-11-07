package org.practice.micronaut.bookshelf.application

import com.ninja_squad.dbsetup_kotlin.DbSetupBuilder
import nu.studer.sample.tables.Authors
import nu.studer.sample.tables.Books
import nu.studer.sample.tables.records.AuthorsRecord
import nu.studer.sample.tables.records.BooksRecord
import org.jooq.DSLContext
import org.practice.micronaut.bookshelf.application.Ids.authorId
import org.practice.micronaut.bookshelf.application.Ids.bookId
import org.practice.micronaut.bookshelf.domain.lib.uuidToBytes
import java.time.LocalDateTime
import java.util.*


object Ids {
    const val authorId = "e4bb1e02-5599-49d5-8cba-488094cee25f"
    const val bookId = "26355bcd-0fc4-49bc-8606-b15461154181"
}

fun String.toUUID(): UUID {
    return UUID.fromString(this)
}

fun DbSetupBuilder.deleteAll() {
    deleteAllFrom(
            Books.BOOKS.name,
            Authors.AUTHORS.name
    )
}

fun DSLContext.insertAuthors() {
    insertInto(Authors.AUTHORS)
            .columns(Authors.AUTHORS.ID, Authors.AUTHORS.AUTHOR_NAME)
            .values(authorId.toUUID().uuidToBytes(), "エリック・エヴァンス").execute()
}

fun DSLContext.insertBooks() {
    insertInto(Books.BOOKS)
            .columns(
                    Books.BOOKS.ID,
                    Books.BOOKS.BOOK_NAME,
                    Books.BOOKS.PUBLIHED_DATE,
                    Books.BOOKS.AUTHOR_ID)
            .values(
                    bookId.toUUID().uuidToBytes(),
                    "エリック・エヴァンスのドメイン駆動設計",
                    LocalDateTime.of(1970, 12, 31, 23, 59),
                    authorId.toUUID().uuidToBytes())
            .execute()
}


fun DbSetupBuilder.insertAuthors() {
    val authorsTable = Authors.AUTHORS
    val id: ByteArray = Ids.authorId.toUUID().uuidToBytes()
    val authorName: String = "エリック・エヴァンス"
    insertInto("authors") {
        values(AuthorsRecord(id, authorName).intoMap())
//        mappedValues(authorsTable.ID.name to Ids.authorId.toUUID().uuidToBytes())
//        mappedValues("author_name" to "エリック・エヴァンス")
    }
}

fun DbSetupBuilder.insertBooks() {
    val booksTable = Books.BOOKS
    insertInto(booksTable.name) {
        values(
                BooksRecord(
                        UUID.fromString(Ids.bookId).uuidToBytes(),
                        "エリック・エヴァンスのドメイン駆動設計",
                        LocalDateTime.of(1970, 12, 31, 23, 59),
                        Ids.authorId.toUUID().uuidToBytes()
                ).intoMap()
        )
    }
}