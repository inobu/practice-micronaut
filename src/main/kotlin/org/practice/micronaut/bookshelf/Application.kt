package org.practice.micronaut.bookshelf

import io.micronaut.runtime.Micronaut.*

fun main(args: Array<String>) {
    build()
            .args(*args)
            .packages("org.practice.micronaut.bookshelf")
            .start()
}

