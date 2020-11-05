package org.practice.micronaut.bookshelf.domain.model

interface Entity<T: Entity<T>> {
    /**
     * TODO
     */
    val id: Id<T>
}

typealias Id<T> = EntityId<T>