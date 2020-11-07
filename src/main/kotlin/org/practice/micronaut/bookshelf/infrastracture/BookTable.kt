package org.practice.micronaut.bookshelf.infrastracture

import java.time.LocalDateTime

data class BookTable(val id: ByteArray, val bookName: String, val publihedDate: LocalDateTime, val authorName: String) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BookTable

        if (!id.contentEquals(other.id)) return false
        if (bookName != other.bookName) return false
        if (publihedDate != other.publihedDate) return false
        if (authorName != other.authorName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.contentHashCode()
        result = 31 * result + bookName.hashCode()
        result = 31 * result + publihedDate.hashCode()
        result = 31 * result + authorName.hashCode()
        return result
    }
}