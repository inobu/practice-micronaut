package org.practice.micronaut.bookshelf.application

import com.ninja_squad.dbsetup_kotlin.DbSetupBuilder
import nu.studer.sample.tables.Authors
import nu.studer.sample.tables.BookNames
import nu.studer.sample.tables.Books
import nu.studer.sample.tables.records.AuthorsRecord
import nu.studer.sample.tables.records.BookNamesRecord
import nu.studer.sample.tables.records.BooksRecord
import org.practice.micronaut.bookshelf.application.Ids.authorId
import org.practice.micronaut.bookshelf.application.Ids.bookId
import org.practice.micronaut.bookshelf.application.Ids.bookNameId
import org.practice.micronaut.bookshelf.domain.lib.uuidToBytes
import java.time.LocalDateTime
import java.util.*


object Ids {
    const val authorId = "e4bb1e02-5599-49d5-8cba-488094cee25f"
    const val bookId = "26355bcd-0fc4-49bc-8606-b15461154181"
    const val bookNameId = "175f9011-43ec-4a91-baf6-7c4ab09aae77"
}

internal fun String.toUUID(): UUID {
    return UUID.fromString(this)
}

fun DbSetupBuilder.deleteAll() {
    deleteAllFrom(
            BookNames.BOOK_NAMES.name,
            Books.BOOKS.name,
            Authors.AUTHORS.name
    )
}

fun DbSetupBuilder.insertAllTest() {
    insertAuthors()
    insertBooks()
    insertBookNames()
}


fun DbSetupBuilder.insertAuthors() {
    val authorsTable = Authors.AUTHORS
    val id: ByteArray = authorId.toUUID().uuidToBytes()
    val authorName = "エリック・エヴァンス"
    insertInto(authorsTable.name) {
        values(AuthorsRecord(id, authorName).intoMap())
    }
}

fun DbSetupBuilder.insertBooks() {
    val booksTable = Books.BOOKS
    insertInto(booksTable.name) {
        values(
                BooksRecord(
                        UUID.fromString(bookId).uuidToBytes(),
                        LocalDateTime.of(1970, 12, 31, 23, 59),
                        authorId.toUUID().uuidToBytes()
                ).intoMap()
        )
    }
}

fun DbSetupBuilder.insertBookNames() {
    val bookNamesTable = BookNames.BOOK_NAMES
    insertInto(bookNamesTable.name) {
        values(
                BookNamesRecord(
                        UUID.fromString(bookNameId).uuidToBytes(),
                        "エリック・エヴァンスのドメイン駆動設計",
                        UUID.fromString(bookId).uuidToBytes()
                ).intoMap()
        )
    }
}