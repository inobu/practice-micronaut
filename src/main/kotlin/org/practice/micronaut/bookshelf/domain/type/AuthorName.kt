package org.practice.micronaut.bookshelf.domain.type

data class AuthorName(val authorName: String) {
    companion object {
        /**
         * 現状不明
         */
        private const val maxAuthorNameLength = 10
    }

    init {
        if (authorName.length > maxAuthorNameLength) {
            throw IllegalArgumentException()
        }
    }

}