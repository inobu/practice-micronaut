package org.practice.micronaut.bookshelf.domain.type

data class BookName(val bookName: String) {
    companion object {
        /**
         * 最大長さが不明なので仮
         */
        private const val maxBookNameLength = 10
    }

    init {
        if(bookName.length > maxBookNameLength) {
            throw IllegalArgumentException()
        }
    }
}