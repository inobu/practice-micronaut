package org.practice.micronaut.bookshelf.domain.model

import java.util.*

data class EntityId<M : Entity<M>>(val value: UUID) {
    companion object {
        fun <M : Entity<M>> fromString(id: String): EntityId<M> = EntityId(UUID.fromString(id))
    }
}